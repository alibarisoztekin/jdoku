package ui;

import model.Cell;
import model.Sudoku;
import model.exceptions.CellException;
import persistance.Decoder;
import persistance.Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Game {

    private Sudoku sudoku;
    private Decoder decoder;
    private Encoder encoder;
    private Driver driver;

    private List<String> savedGames;

    // Chose to keep Game.State directly accessible to UI package
    protected State state;
    private Mode mode;

    //EFFECTS: Creates the game object
    public Game(Mode mode) {
        this.decoder = new Decoder();
        this.encoder = new Encoder();
        this.mode = mode;
    }

    enum Mode {
        GUI, CONSOLE;
    }

    enum State {
        PLAY, PAUSE, SELECT_SAVED, SELECT_DIFF;
    }

    public void start() throws IOException {

        switch (this.mode) {
            case CONSOLE:
                this.driver = new ConsoleDriver(this);
                break;
            case GUI:
                this.driver = new SwingDriver(this);
                break;
        }
        driver.handleRun();

    }

    public Hashtable<Integer, Cell> getCells() {
        return sudoku.getBoard().getCells();
    }

    public void changeCellValue(Character insert, Cell cell) throws CellException {
        if (cell == null || !cell.isInteractive() || !Character.isDigit(insert)) {
            throw new CellException();
        }

        cell.setValue(insert.toString());
    }

    public void reset() {
        sudoku.resetBoard();
    }

    public List<String> getSaved() {
        try {
            return decoder.getSavedGames();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public void save() {
        this.encoder.save(this);
    }

    public void load(String id) {

    }

    public void randomNewWithDifficulty(Sudoku.Difficulty difficulty) {
        this.sudoku = new Sudoku(difficulty, decoder);
    }
}
