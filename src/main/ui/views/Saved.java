package ui.views;

import ui.SwingDriver;

import javax.swing.*;
import java.util.List;

public class Saved extends JPopupMenu {
    private final SwingDriver driver;
    private List<String> ids;

    private JButton ok;
    private JButton cancel;

    public Saved(SwingDriver driver) {
        this.driver = driver;
        this.ids = this.driver.getIds();
        this.ok = new JButton();

    }
}
