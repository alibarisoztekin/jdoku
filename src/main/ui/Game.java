package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import model.Board;
import model.Cell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

public class Game {

    private Integer boardOffSet = 6;
    private static final Integer SIZE = 9;


    private Screen screen;
    private DefaultTerminalFactory terminal;
    private Board board;
    private HashMap<TerminalPosition, Cell> digitCoordinates;


    //EFFECTS: Creates the game object
    public Game() throws IOException {
        this.terminal = new DefaultTerminalFactory();
        this.screen = this.terminal.createScreen();
        this.screen.startScreen();
        this.digitCoordinates = new HashMap<>();
        File puzzle = new File("./data/puzzles/E1.txt");
        this.board = new Board(puzzle, SIZE);
        render();


        screen.refresh();
        handlePlayer();


    }


    // EFFECTS: draws the digits on the terminal
    private void drawBoard() throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        Hashtable<Integer, Cell> indices = board.getIndexHash();

        for (Integer index : indices.keySet()) {
            Cell cell = indices.get(index);
            tg.setForegroundColor(cell.getColor());
            TerminalPosition cellPos;
            cellPos = new TerminalPosition(cell.getRow() + boardOffSet, cell.getColumn() + boardOffSet);
            tg.putString(cellPos, cell.getValue());
            digitCoordinates.put(cellPos, cell);

        }
    }

    private void setupInstructions() {
        TextGraphics instructions = screen.newTextGraphics();
        instructions.putString(new TerminalPosition(0, 0), "use arrow keys to move");
        instructions.putString(new TerminalPosition(0, 1), "press [esc] to quit");
        instructions.putString(new TerminalPosition(0, 2), "press [r] to reload");
    }


    //getting a checkstyle error saying method name is 34 characters??
    public void handlePlayer() throws IOException {

        while (true) {

            TerminalPosition cursorPos = screen.getCursorPosition();
            Integer column = cursorPos.getColumn();
            Integer row = cursorPos.getRow();
            KeyStroke key = screen.readInput();
            if (key != null) {

                if (key.getKeyType() == KeyType.ArrowLeft) {
                    screen.setCursorPosition(new TerminalPosition(column - 1, row));
                } else if (key.getKeyType() == KeyType.ArrowRight) {
                    screen.setCursorPosition(new TerminalPosition(column + 1, row));
                } else if (key.getKeyType() == KeyType.ArrowUp) {
                    screen.setCursorPosition(new TerminalPosition(column, row - 1));
                } else if (key.getKeyType() == KeyType.ArrowDown) {
                    screen.setCursorPosition(new TerminalPosition(column, row + 1));
                } else if (key.getKeyType() == KeyType.Escape) {
                    screen.close();
                } else if (key.getCharacter().charValue() == 'r') {
                    board.reload();
                }  else if (digitCoordinates.containsKey(cursorPos)) {
                    tryInsert(key, digitCoordinates.get(cursorPos));
                }
            }

            screen.clear();
            render();
            screen.refresh();
        }
    }


    public void render() throws IOException {
        setupInstructions();
        drawBoard();
    }

    private void tryInsert(KeyStroke key, Cell cell) {

        Character insert = key.getCharacter().charValue();

        if (cell != null && cell.isInteractive() && Character.isDigit(insert)) {
            cell.setValue(insert.toString());
        }

    }

}
