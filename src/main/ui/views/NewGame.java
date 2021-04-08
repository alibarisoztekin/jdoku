package ui.views;

import model.Difficulty;
import ui.Driver;
import ui.SwingDriver;

import javax.swing.*;

public class NewGame extends JPopupMenu {
    private SwingDriver driver;

    //Semantic coupling with Difficulty, fix this!
    public NewGame(SwingDriver driver) {
        this.driver = driver;
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(this.driver);
        easy.setActionCommand("E");
        this.add(easy);
        JMenuItem medium = new JMenuItem("Medium");
        medium.addActionListener(this.driver);
        medium.setActionCommand("M");
        this.add(medium);
        JMenuItem hard = new JMenuItem("Hard");
        hard.addActionListener(this.driver);
        hard.setActionCommand("H");
        this.add(hard);
    }

}
