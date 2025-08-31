package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TagLabel extends JLabel {
    public TagLabel(String text) {
        super(text);
        setFont(getFont().deriveFont(Font.PLAIN, 12f));
        setBorder(new EmptyBorder(2, 10, 2, 10));   // padding interior
        setOpaque(false);                           // pintamos nosotros el fondo
        setForeground(new Color(60,60,60));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int arc = 16;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // fondo “pill”
        g2.setColor(new Color(230, 230, 230));      // fondo claro (cámbialo según tu L&F)
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        // borde sutil
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        g2.dispose();
        super.paintComponent(g);                    // texto
    }
}
