package ui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Board;
import model.Cell;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Game {

    private Integer boardOffSet = 2;
    private static final Integer SIZE = 9;

    private Integer boardLength;
    private Integer boardHeight;
    private Integer botBarHeight;



    private Screen screen;
    private Board board;
    private Integer difficulty;


    //EFFECTS: Creates the game object
    public Game() throws IOException {
        this.screen = new DefaultTerminalFactory().createScreen();
        this.screen.startScreen();
        this.board = new Board(SIZE);
        drawBoard();


    }


    // EFFECTS: draws the digits on the terminal
    private void drawBoard() throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        Hashtable<Integer, Cell> indices = board.getIndices();

        for (Integer index : indices.keySet()) {
            Cell cell = indices.get(index);
            tg.setForegroundColor(ANSI.RED);
            tg.putString(cell.getPosX() + boardOffSet,cell.getPosY() + boardOffSet,cell.getValue());
        }
        screen.refresh();
    }




}
