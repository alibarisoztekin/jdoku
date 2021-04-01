package ui;

import model.Cell;
import model.exceptions.CellException;
import ui.views.GIF;
import ui.views.Grid;
import ui.views.NewGame;
import ui.views.Saved;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SwingDriver extends JFrame implements Driver, ActionListener, PropertyChangeListener {

    private Game game;
    private Grid grid;
    private JButton saveButton;
    private JTextField indicator;
    private JButton loadButton;
    private JButton newButton;
    private JButton resetButton;
    private Saved savedPopup;
    private NewGame newGamePopup;
    private GIF endGif;

    public SwingDriver(Game game) {
        super("Jdoku");
        this.game = game;


    }

    private void setup() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout(FlowLayout.CENTER));


        setupPeripherals();
        setupViews();
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
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        this.loadButton = new JButton("Load");
        loadButton.setActionCommand("cheat");
        loadButton.addActionListener(this);

        this.newButton = new JButton("New Game");
        this.resetButton = new JButton("Reset");
        resetButton.setActionCommand("cheat");
        this.indicator = new JTextField("");
        this.add(saveButton);
        this.add(loadButton);
        this.add(indicator);
        this.endGif = new GIF();

        //add action
    }

    public void handleRun() {
        //load last saved
        game.loadLastSaved();
        setup();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            try {
                game.save();
                this.indicator.setText("Saved");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                this.indicator.setText("Failed to save");
            }
        } else if (e.getActionCommand().equals("cheat")) {

            this.add(endGif);
            endGif.setVisible(true);
            this.setVisible(true);

        }
    }

    public Hashtable<Integer, Cell> getBoard() {
        return game.getCells();
    }

    public List<String> getIds() {
        return game.getSavedIds();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() != null) {
            int index = Integer.parseInt(evt.getPropertyName());
            if (Character.isDigit((Character) evt.getNewValue())) {
                try {
                    game.changeCellValue((Character) evt.getNewValue(),grid.getBoard().get(index));
                } catch (CellException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
