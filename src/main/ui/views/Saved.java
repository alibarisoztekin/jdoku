package ui.views;

import ui.SwingDriver;

import javax.swing.*;
import java.util.List;

public class Saved extends JPopupMenu {
    private final SwingDriver driver;
    private List<String> ids;


    public Saved(SwingDriver driver) {
        super("Saved Games");
        this.driver = driver;
        this.ids = this.driver.getIds();
        listSavedInMenu();

    }

    private void listSavedInMenu() {
        this.ids = driver.getIds();
        for (String id : this.ids) {
            JMenuItem item = new JMenuItem(id);
            item.addActionListener(this.driver);
            item.setActionCommand(id);
            this.add(item);
        }
    }
}
