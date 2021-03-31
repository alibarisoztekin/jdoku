package ui;

import model.Cell;
import ui.views.Grid;
import ui.views.NewGame;
import ui.views.Saved;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SwingDriver extends JFrame implements Driver, ActionListener {

    private Game game;
    private Grid grid;
    private JButton saveButton;
    private JTextField indicator;
    private JButton loadButton;
    private JButton newButton;
    private JButton resetButton;
    private Saved savedPopup;
    private NewGame newGamePopup;
    private JPanel endGif;

    public SwingDriver(Game game) {
        super("Jdoku");
        this.game = game;
        setup();



    }

    private void setup() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        setupPeripherals();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    private void setupViews() {
        this.grid = new Grid(this);
        this.newGamePopup = new NewGame(this);
        this.savedPopup = new Saved(this);
        this.add(grid);
    }

    private void setupPeripherals() {

        this.saveButton = new JButton("Save");
        this.loadButton = new JButton("Load");
        this.newButton = new JButton("New Game");
        this.resetButton = new JButton("Reset");
        this.indicator = new JTextField("");
        this.add(saveButton);
        this.add(loadButton);
        this.add(indicator);

        //add action
    }

    public void handleRun() {
        game.getSavedIds();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public Hashtable<Integer, Cell> getBoard() {
        return game.getCells();
    }

    public List<String> getIds() {
        return game.getSavedIds();
    }
}
