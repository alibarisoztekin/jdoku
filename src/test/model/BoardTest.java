package model;

import model.exceptions.BoardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Decoder;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private String puzzlePath;
    private Integer size;
    private Hashtable <Integer, Cell> clonedCells;




    @BeforeEach
    void setup() {
        this.puzzlePath = "E1.txt";
        this.size = 9;
        try {
            this.board = new Board(new Difficulty("E"), new Decoder(),size,0);
        } catch (BoardException e) {
            fail("Not supposed to fail here");
            e.printStackTrace();
        }
        try {
            this.clonedCells  = new Decoder().cellsFromTxtPath(this.puzzlePath, size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor() {
        assertEquals(board.getCells().size(), size * size);
        assertEquals(board.getCells(), this.clonedCells);


    }

    @Test
    void testInvalidConstructor() {
        try {
            Board board = new Board(new Difficulty("E"), new Decoder(),  1, 4);
        } catch (BoardException e) {
            // expected Exception
            assertNull(board.getCells());
            e.printStackTrace();
        }
    }

    @Test
    void testReload() throws FileNotFoundException {
        assertEquals(board.getCells(),clonedCells);
        Cell cell = board.getCells().get(80);
        cell.setValue("9");
        assertNotEquals(board.getCells(), clonedCells);
        board.reset();
        assertEquals(board.getCells(),clonedCells);
    }

}