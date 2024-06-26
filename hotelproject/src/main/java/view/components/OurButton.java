package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OurButton extends JButton {

    private int arcWidth=30;
    private int arcHeight=30;

    private boolean bgFilled=false;
    private Image icon;

    /**
     * (For content buttons)
     * Will create a button with the given text and set the default font to Arial, bold, 14pt.
     * It provides a default transparent background and black text color and round border
     * @param text String
     *             The text to be displayed on the button
     * @author Mouloudj
     * */
    public OurButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14)); // Set default font
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    // These 2 methods are used to paint the button with a round border and armed color when clicked
    @Override
    protected void paintComponent(Graphics g) {
        if (!bgFilled){
            super.paintComponent(g);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Anti-aliasing, make the border smoother
        // Set the color of the button based on the state (armed or not)
        if (getModel().isArmed()) {
            // Workaround for getArmedBorderColor():
            Color armedColor = UIManager.getColor("Button.select"); // Use system-defined color
            if (armedColor == null) {
                armedColor = super.getBackground().darker(); // Fallback if not available
            }
            g2d.setColor(armedColor);
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        super.paintComponent(g);
    }

    public void setButtonText(String text) {
        super.setText(text);
    }

    public void setButtonBgColor(Color bgColor ) {
        setBackground(bgColor);
        bgFilled = true;
        repaint();
    }
    public void setButtonTxtColor(Color txtColor) {
        setForeground(txtColor);
    }
    public void removeRoundBorder() {
        setContentAreaFilled(true);
        repaint();
    }

    public void setButtonSize(Dimension size) {
        setPreferredSize(size);
        repaint();
    }

    public void setFontButton(Font font) {
        setFont(font);
    }

    /**
     * Set the icon to the button
     * @param icon String
     *             The path of the icon
     * @param gap int
     *            The space between the text and the icon
     * @param horizontalTextPosition int
     *                               The position of the text in relation to the icon
     *                               LEFT=2, RIGHT=4, CENTER=0
     * */
    public void setIconToButton(ImageIcon icon,int gap, int horizontalTextPosition) {
        if (icon != null) {
            this.icon = icon.getImage();
            setIcon(icon);
            setIconTextGap(gap);
            setHorizontalTextPosition(horizontalTextPosition);
            // Adjust button size if icon is larger

            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            setPreferredSize(new Dimension(Math.max(getPreferredSize().width, iconWidth),
                    Math.max(getPreferredSize().height, iconHeight)));
        }
    }
    public void addActionListener(ActionListener listener) {
        super.addActionListener(listener);
    }
}