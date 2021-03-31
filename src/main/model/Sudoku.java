package model;

import model.exceptions.BoardException;
import org.json.JSONObject;
import persistance.Decoder;
import persistance.Jsonable;

public class Sudoku implements Jsonable {


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Board getBoard() {
        return board;
    }



    private Difficulty difficulty;
    private Board board;
    private static final Integer SIZE = 9;


    // EFFECTS: constructs a random sudoku puzzle with given difficulty
    public Sudoku(Difficulty difficulty, Decoder decoder) {
        this.difficulty = difficulty;
        try {
            this.board = new Board(difficulty, decoder, SIZE, 3);
        } catch (BoardException e) {
            e.printStackTrace();
        }
    }

    public Sudoku(Board board, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.board = board;

    }

    public void resetBoard() {
        this.board.reset();
    }

    @Override
    public JSONObject jsoned() {
        return new JSONObject()
                .put("board",board.jsonArr())
                .put("difficulty", this.difficulty.toString());
    }

}
