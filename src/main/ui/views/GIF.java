package ui.views;

import javax.swing.*;
import java.awt.*;

public class GIF extends JPanel {
    Image image;

    public GIF() {
        image = Toolkit.getDefaultToolkit().createImage("./data/gg.gif");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 300, 300, this);
        }
    }
}
