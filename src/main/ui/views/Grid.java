package ui.views;

import model.Board;
import model.Cell;
import ui.SwingDriver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class Grid extends JPanel {


    private JTextField[][] tfCells = new JTextField[9][9];


    private Hashtable<Integer, Cell> board;
    private ArrayList<JTextField> cells;
    private SwingDriver driver;

    public Grid(SwingDriver driver) {
        super();
        this.driver = driver;
        this.cells = new ArrayList<JTextField>();
        this.board = driver.getBoard();
        layoutGrid();
    }


    private void layoutGrid() {
        for (int index : this.board.keySet()) {
            Cell cell = this.board.get(index);
            int row = cell.getRow();
            int col = cell.getColumn();
            JTextField curField = new JTextField();
            tfCells[row][col] = curField;
            if (cell.getValue() == "0") {
                curField.setText("");
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
            this.add(curField);
        }
    }

}