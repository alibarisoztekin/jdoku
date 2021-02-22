package model;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;
    private Integer index;
    private String value;
    private Integer bound;

    @BeforeEach
    void setup() {
        this.bound = 9;
        this.index = new Random().nextInt(bound * bound);
        Integer val = new Random().nextInt(bound);
        this.value = val.toString();
        this.cell = new Cell(index,value);
    }

    @Test
    void testConstructor() {
        assertEquals(cell.getRow(), index / bound);
        assertEquals(cell.getColumn(), index % bound);
        assertEquals(cell.getColor(), TextColor.ANSI.WHITE);
        assertEquals(cell.getValue(), value);
    }

    @Test
    void testInteractive() {
        if (cell.getValue() == "0") {
            assertEquals(cell.isInteractive(), true);
        } else {
            assertEquals(cell.isInteractive(), false);

        }

    }

}
