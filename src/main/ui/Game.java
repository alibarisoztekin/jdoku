package ui;

import model.Cell;
import model.Difficulty;
import model.Sudoku;
import model.exceptions.CellException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Decoder;
import persistance.Encoder;
import persistance.JsoNotable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Game implements Driver, JsoNotable {

    private Sudoku sudoku;
    private Decoder decoder;
    private Encoder encoder;
    private Driver driver;
    private HashMap<String, Sudoku> saved;

    // Chose to keep Game.State directly accessible to Drivers
    protected State state;
    private Mode mode;
    private String id;

    //EFFECTS: Creates the game object
    public Game(Mode mode) {
        this.decoder = new Decoder();
        this.mode = mode;
        try {
            this.saved = decoder.loadGames();
        } catch (IOException e) {
            this.saved = new HashMap<>();
        }

    }

    @Override
    public void handleRun() {

    }

    public enum Mode {
        GUI, CONSOLE, TEST
    }

    enum State {
        PLAY, PAUSE, SELECT_SAVED, SELECT_DIFF
    }

    public void start() throws IOException {

        switch (this.mode) {
            case CONSOLE:
                this.driver = new ConsoleDriver(this);
                break;
            case GUI:
                this.driver = new SwingDriver(this);
                break;
            case TEST:
                this.driver = this;
                break;
        }
        this.encoder = new Encoder(mode);
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

    public List<String> getSavedIds() {
        try {
            return decoder.getIds();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save() throws FileNotFoundException {
        this.encoder.save(this);
    }

    public void loadLastSaved() {
        load(getSavedIds().get(0));
    }

    public void load(String id) {
        if (getSavedIds().contains(id)) {
            this.sudoku = saved.get(id);
        }
    }

    public void newWithDifficulty(Difficulty difficulty) {
        this.sudoku = new Sudoku(difficulty, decoder);
    }

    @Override
    public JSONObject json() {
        JSONArray idList = new JSONArray(saved.keySet());
        if (this.id == null) {
            this.id = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z").format(new Date());
            idList.put(0, this.id);
            this.saved.put(this.id, this.sudoku);

        }
        JSONObject games = new JSONObject();
        saved.forEach((k,v) -> games.put(k,v.json()));

        return new JSONObject()
                .put("ids", new JSONArray(saved.keySet()))
                .put("savedGames",games);
    }
}
