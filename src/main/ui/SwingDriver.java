package ui;

import model.Cell;
import model.Difficulty;
import model.exceptions.CellException;
import ui.views.Grid;
import ui.views.NewGame;
import ui.views.Saved;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;

// Game driver using Swing
public class SwingDriver extends JFrame implements Driver, ActionListener {

    private Game game;
    private Grid grid;
    private JButton saveButton;
    private JLabel indicator;
    private JButton loadButton;
    private JButton newButton;
    private JButton resetButton;
    private Saved savedPopup;
    private NewGame newGamePopup;
    private JLabel endGif;
    private GridBagConstraints constraints;

    public SwingDriver(Game game) {
        super("Jdoku");
        this.game = game;
        this.constraints = new GridBagConstraints();

    }

    // EFFECTS: Setup views and layout
    private void setup() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setupPeripherals();
        setupGrid();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    private void setupGrid() {
        this.grid = new Grid(this);
        this.add(grid);

    }

    private void setupPeripherals() {

        setupSaveButton();

        setupLoadButton();

        setupNewButton();


        this.resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        this.add(resetButton);

        JButton cheat = new JButton("cheat");
        cheat.setActionCommand("cheat");
        cheat.addActionListener(this);
        this.add(cheat);

        this.indicator = new JLabel("     ");

        Icon gif = new ImageIcon("./data/gg.gif");
        this.endGif = new JLabel(gif);
        endGif.setBounds(50, 50, 300, 300);
    }

    private void setupNewButton() {
        this.newButton = new JButton("New Game");
        newButton.setActionCommand("new");
        newButton.addActionListener(this);
        this.add(newButton);
    }

    private void setupLoadButton() {
        this.loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        this.add(loadButton);
    }

    private void setupSaveButton() {
        this.saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        this.add(saveButton);
    }

    @Override
    public void start() {
        //load last saved
        game.loadLastSaved();
        setup();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetPeripherals();
        if (e.getActionCommand().equals("save")) {
            handleSave();
        } else if (e.getActionCommand().equals("cheat")) {
            handleGIF();
        } else if (e.getActionCommand().equals("load")) {
            handleLoad();
        } else if (e.getActionCommand().equals("new")) {
            handleNew();
        } else if (e.getActionCommand().equals("reset")) {
            game.reset();
            this.grid.setCellHashtable(game.getCells());
        } else if (e.getSource() instanceof JTextField) {
            consumeCellEvent(e);
        } else if (e.getSource() instanceof JMenuItem) {
            handleMenuEvent(e);
        }
        this.setVisible(true);
    }

    private void resetPeripherals() {
        if (endGif.isShowing()) {
            endGif.setVisible(false);
            this.remove(endGif);
        }
        if (indicator.isShowing()) {
            indicator.setVisible(false);
            this.remove(indicator);
        }
    }

    private void handleMenuEvent(ActionEvent e) {
        if (e.getActionCommand().length() > 1) {
            consumeLoadSavedEvent(e);
        } else {
            consumeNewGameEvent(e);
        }
        this.grid.setCellHashtable(game.getCells());
    }

    private void handleLoad() {
        this.savedPopup = new Saved(this);
        savedPopup.show(this.loadButton, loadButton.getX(), loadButton.getY());
    }

    private void handleNew() {
        this.newGamePopup = new NewGame(this);
        newGamePopup.show(this.newButton, newButton.getX(), newButton.getY());

    }

    private void consumeNewGameEvent(ActionEvent e) {
        if (e.getActionCommand().equals("E")) {
            game.newWithDifficulty(new Difficulty(0));
        } else if (e.getActionCommand().equals("M")) {
            game.newWithDifficulty(new Difficulty(1));
        } else if (e.getActionCommand().equals("H")) {
            game.newWithDifficulty(new Difficulty(2));
        }


    }

    private void consumeLoadSavedEvent(ActionEvent e) {
        game.load(e.getActionCommand());

    }


    private void handleGIF() {

        if (grid.isVisible() || saveButton.isVisible() || indicator.isShowing()) {
            grid.setVisible(false);
            saveButton.setVisible(false);
            indicator.setVisible(false);
            this.remove(grid);
            this.remove(saveButton);
            this.remove(indicator);
        }
        if (!endGif.isShowing()) {
            this.add(endGif);
            endGif.setVisible(true);
        }
    }

    private void handleSave() {
        try {
            game.save();
            this.indicator.setText("Saved");
            this.add(indicator);
            indicator.setVisible(true);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            this.indicator.setText("Failed to save");
        }
    }

    // MODIFIES: this, game
    // EFFECTS: process input on sudoku cell JTextFields
    private void consumeCellEvent(ActionEvent e) {
        JTextField field = (JTextField) e.getSource();
        int index = Integer.parseInt(e.getActionCommand());
        String newVal = field.getText().substring(0, 1);
        try {
            int val = Integer.parseInt(newVal);
            game.changeCellValue(newVal.charAt(0), this.grid.getCellHashtable().get(index));
            if (val == 0) {
                field.setText("");
            }
        } catch (NumberFormatException ne) {
            field.setText("");
        } catch (CellException cellException) {
            cellException.printStackTrace();
        }
    }

    public Hashtable<Integer, Cell> getCells() {
        return game.getCells();
    }

    public List<String> getIds() {
        return game.getSavedIds();
    }

}
