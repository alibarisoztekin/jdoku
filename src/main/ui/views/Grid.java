package ui.views;

import model.Cell;
import ui.SwingDriver;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class Grid extends JPanel {


    private JTextField[][] cellFields;


    public Hashtable<Integer, Cell> getBoard() {
        return board;
    }

    private Hashtable<Integer, Cell> board;
    private ArrayList<JTextField> cells;
    private SwingDriver driver;

    public Grid(SwingDriver driver) {
        super();
        this.driver = driver;
        this.cells = new ArrayList<>();
        this.board = driver.getBoard();
        this.cellFields = new JTextField[9][9];
        layoutGrid();
        layoutInBorder();
    }

    // EFFECTS: maps Cells to JTextFields
    private void layoutGrid() {

        for (int index : this.board.keySet()) {
            Cell cell = this.board.get(index);
            int row = cell.getRow();
            int col = cell.getColumn();
            JTextField curField = new JTextField();
            cellFields[row][col] = curField;
            setupCellField(cell, curField);

        }
    }

    private void setupCellField(Cell cell, JTextField curField) {
        if (cell.getValue().equals("0")) {
            curField.setText(" ");
        } else {
            curField.setText(cell.getValue());
        }
        if (cell.isInteractive()) {
            curField.setEditable(true);
            curField.setBackground(Color.GREEN);
        } else {
            curField.setEditable(false);
            curField.setBackground(Color.WHITE);
            curField.setForeground(Color.LIGHT_GRAY);
        }
        curField.setHorizontalAlignment(JTextField.CENTER);
        curField.setFont(new Font("Helvetica",Font.BOLD, 20));
        curField.setActionCommand(cell.getIndex());
        curField.addActionListener(this.driver);

    }

    // inspired by https://stackoverflow.com/users/1031312/ozzy
    private void layoutInBorder() {
        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                constraints.weightx = 1.0;
                constraints.weighty = 1.0;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridx = i;
                constraints.gridy = j;
                borderPanel.add(cellFields[i][j], constraints);
            }
        }
        this.add(borderPanel);
    }
}
