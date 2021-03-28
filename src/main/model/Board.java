package model;

import model.exceptions.BoardException;
import persistance.Decoder;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

// Represents
public class Board {

    private Hashtable<Integer, Cell> cells;
    private final int size;


    // REQUIRES: dimensions of puzzle must match size in txt file
    // EFFECTS: instantiates Board with given cells and size
    public Board(Hashtable<Integer, Cell> cells, int size) {
        this.size = size;
        this.cells = cells;
    }

    public Board(int size) {
        this(new Hashtable<>(size * size), size);
    }

    // REQUIRES: randUpperBound is 0 indexed, must match number of predetermined games in file;
    // EFFECTS: instantiates a random board with given difficulty from the existing files in ./data/puzzles
    // 0 randUpperBound is for test validity
    public Board(Sudoku.Difficulty difficulty, Decoder decoder, int size, int randUpperBound) throws BoardException {
        this(size);
        int rand;
        try {
            rand = new Random().nextInt(randUpperBound) + 1;
        } catch (IllegalArgumentException e) {
            rand  = 1;
        }
        String filePath = difficulty.toString() + rand + ".txt";
        try {
            this.cells = decoder.cellsFromTxtPath(filePath, size);
        } catch (FileNotFoundException e) {
            throw new BoardException(e.getMessage());
        }

    }

    public Hashtable<Integer, Cell> getCells() {
        return cells;
    }

    // MODIFIES: this.cells
    // EFFECTS: returns the values of the board to their original configuration
    public void reset() {
        for (Cell cell : cells.values()) {
            if (cell.isInteractive()) {
                cell.setValue("0");
            }
        }
    }

}
