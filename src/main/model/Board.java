package model;

import model.exceptions.BoardException;
import org.json.JSONArray;
import persistance.Decoder;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Random;

// Represents
public class Board {

    private Hashtable<Integer, Cell> cells;
    private final int size;


    // REQUIRES: dimensions of puzzle must match size in txt file
    // EFFECTS: instantiates Board with given cells and size
    public Board(Hashtable<Integer, Cell> cells) {
        this.size = 9; //
        this.cells = cells;
    }


    // EFFECTS: instantiates a random board with given difficulty from the existing files in ./data/puzzles
    // 0 randUpperBound is for test validity
    // throws BoardException if File with given upper bound is not found
    public Board(Difficulty difficulty, Decoder decoder, int size, int randUpperBound) throws BoardException {
        this.size = size;
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


    public JSONArray jsonArr() {
        JSONArray result = new JSONArray();
        this.cells.forEach((k,v) -> result.put(k,v.json()));
        return result;
    }
}
