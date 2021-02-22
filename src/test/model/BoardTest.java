package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private File puzzle;
    private Integer size;
    private Hashtable<Integer, Cell> cloned;




    @BeforeEach
    void setup() throws FileNotFoundException {
        this.puzzle = new File("./data/puzzles/E1.txt");
        this.size = 9;
        this.board = new Board(puzzle,size);
        this.cloned = board.getIndexHash();
    }

    @Test
    void testConstructor() {
        assertEquals(board.getIndexHash().size(), size * size);
    }

    @Test
    void testReload() throws FileNotFoundException {
        board.clearBoard();
        assertEquals(board.getIndexHash().size(), 0);
        board.reload();
        assertEquals(board.getIndexHash(),cloned);

    }

}