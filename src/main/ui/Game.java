package ui;

import model.Cell;
import model.Difficulty;
import model.Sudoku;
import model.exceptions.CellException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Decoder;
import persistence.Encoder;
import persistence.JsoNotable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//
public class Game implements Driver, JsoNotable {

    private Sudoku sudoku;
    private Decoder decoder;
    private Encoder encoder;
    private Driver driver;
    private HashMap<String, Sudoku> saved;


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


    // headless driver for testing
    @Override
    public void start() {

    }

    public enum Mode {
        GUI, CONSOLE, TEST
    }


    public void handleRun() throws IOException {

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
        driver.start();

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
            this.id = id;
        }
    }

    public void newWithDifficulty(Difficulty difficulty) {
        this.sudoku = new Sudoku(difficulty, decoder);
        this.id = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z").format(new Date());


    }

    @Override
    public JSONObject json() {
        JSONArray idList = new JSONArray(saved.keySet());
        idList.put(0, this.id);
        this.saved.put(this.id, this.sudoku);


        JSONObject games = new JSONObject();
        saved.forEach((k, v) -> games.put(k, v.json()));

        return new JSONObject()
                .put("ids", idList)
                .put("savedGames", games);
    }
}
