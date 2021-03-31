package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Cell;
import model.Difficulty;
import model.exceptions.CellException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class ConsoleDriver implements Driver {

    private int offset;

    private final Screen screen;
    private final DefaultTerminalFactory terminal;
    private Hashtable<TerminalPosition, Cell> cellCoordinates = new Hashtable<>();
    private Game game;


    public ConsoleDriver(Game game) throws IOException {
        this.game = game;
        this.terminal = new DefaultTerminalFactory();
        this.terminal.setTerminalEmulatorTitle("Jdoku");
        this.screen = this.terminal.createScreen();
        this.screen.startScreen();

    }

    public void handleRun() {
        this.game.state = Game.State.PAUSE;
        if (game.getSavedIds().isEmpty()) {
            game.state = Game.State.SELECT_DIFF;
        } else {
            game.state = Game.State.SELECT_SAVED;
        }

        try {
            tick();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tick() throws IOException {
        KeyStroke key;
        do {
            render(game.state);
            key = screen.readInput();
            handleInput(key);

        } while (key.getKeyType() != KeyType.Escape);
        screen.close();

    }

    public void handleInput(KeyStroke key) throws FileNotFoundException {
        TerminalPosition cursorPos = screen.getCursorPosition();
        if (key.getKeyType() == KeyType.Escape) {
            game.save();
        }
        if (isMoveable(key.getKeyType())) {
            move(key.getKeyType(), cursorPos);
        } else {
            if (key.getCharacter() != null) {
                processCommand(key.getCharacter());
            }
            if (game.state == Game.State.PLAY && cellCoordinates.containsKey(cursorPos)) {
                changeCell(key, cellCoordinates.get(cursorPos));
            } else if (game.state == Game.State.SELECT_DIFF && key.getKeyType() == KeyType.Enter) {
                newGameFromSelection(cursorPos.getRow() - offset);
                game.state = Game.State.PLAY;
            } else if (game.state == Game.State.SELECT_SAVED && key.getKeyType() == KeyType.Enter) {
                loadGameFromSelection(cursorPos.getRow() - offset);
                game.state = Game.State.PLAY;
            }

        }


    }

    public void render(Game.State state) throws IOException {
        screen.clear();
        graphicsForState(state);
        screen.refresh();
    }


    private void graphicsForState(Game.State state) {
        TextGraphics instructions = screen.newTextGraphics();
        instructions.putString(new TerminalPosition(0, 0), "->use arrow keys to move");
        instructions.putString(new TerminalPosition(0, 1), "->press [esc] to quit");

        switch (state) {
            case PLAY:
                this.offset = 7;
                playInstructions(instructions);
                drawBoard();
                break;
            case SELECT_SAVED:
            case SELECT_DIFF:
                this.offset = 5;
                if ((state == Game.State.SELECT_DIFF)) {
                    selectDiff(instructions);
                } else {
                    displaySaved(instructions);

                }
            default:

                instructions.putString(new TerminalPosition(0, 2), "->press [enter] to select");
                break;
        }
    }

    private void playInstructions(TextGraphics instructions) {
        instructions.putString(new TerminalPosition(0, 2), "->press [r] to reset");
        instructions.putString(new TerminalPosition(0, 3), "->press [n] for new game");
        instructions.putString(new TerminalPosition(0, 4), "->press [s] to save game");
        instructions.putString(new TerminalPosition(0, 5), "->press [l] to load games");
    }

    private void selectDiff(TextGraphics instructions) {
        instructions.putString(new TerminalPosition(offset, offset), "EASY");
        instructions.putString(new TerminalPosition(offset, offset + 1), "MEDIUM");
        instructions.putString(new TerminalPosition(offset, offset + 2), "HARD");
    }

    // EFFECTS: draws the digits on the terminal
    private void drawBoard() {
        TextGraphics tg = screen.newTextGraphics();

        Hashtable<Integer, Cell> indices = game.getCells();

        for (Integer index : indices.keySet()) {
            Cell cell = indices.get(index);
            tg.setForegroundColor(cell.isInteractive() ? TextColor.ANSI.GREEN : TextColor.ANSI.WHITE);
            TerminalPosition cellPos;
            cellPos = new TerminalPosition(cell.getRow() + offset, cell.getColumn() + offset);
            tg.putString(cellPos, cell.getValue());
            cellCoordinates.put(cellPos, cell);

        }
    }


    // not good
    // REQUIRES: int normalizedRow == cursorPos.getRow() - offset must represent corresponding difficulty
    // EFFECTS: create new Sudoku game from user input
    private void newGameFromSelection(int normalizedRow) {
        if (normalizedRow >= 0 && normalizedRow < 3) {
            this.game.newWithDifficulty(new Difficulty(normalizedRow));
        }
    }

    private void loadGameFromSelection(int normalizedRow) {
        try {
            this.game.load(this.game.getSavedIds().get(normalizedRow));
        } catch (IndexOutOfBoundsException e) {
            // silent handling for invalid user input
        }

    }

    private void processCommand(char c) {
        if (c == 'r') {
            game.reset();
        } else if (c == 'n') {
            game.state = Game.State.SELECT_DIFF;
        } else if (c == 'l') {
            game.state = Game.State.SELECT_SAVED;
        } else if (c == 's') {
            game.state = Game.State.PAUSE;
            try {
                game.save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void changeCell(KeyStroke key, Cell cell) {

        Character insert = key.getCharacter();
        try {
            game.changeCellValue(insert, cell);
        } catch (CellException e) {
            e.printStackTrace();
        }
    }

    private void displaySaved(TextGraphics instructions) {
        instructions.putString(new TerminalPosition(0, 3), "->press [n] for new game");
        List<String> savedList = game.getSavedIds();
        if (savedList.isEmpty()) {
            instructions.putString(offset, offset, "No Games Saved");
            instructions.putString(offset + 1, offset, "press [n] for new game");
        } else {
            for (int i = 0; i < savedList.size(); i++) {
                instructions.putString(offset, offset + i, savedList.get(i));
            }

        }
    }


    // EFFECTS: return true if keyType represents an arrow key
    private boolean isMoveable(KeyType keyType) {
        return keyType == KeyType.ArrowDown
                || keyType == KeyType.ArrowLeft
                || keyType == KeyType.ArrowRight
                || keyType == KeyType.ArrowUp;
    }


    // REQUIRES: isMoveable()
    // EFFECTS: move cursor for given arrow key
    private void move(KeyType keyType, TerminalPosition cursorPos) {
        if (keyType == KeyType.ArrowLeft) {
            screen.setCursorPosition(new TerminalPosition(cursorPos.getColumn() - 1, cursorPos.getRow()));
        } else if (keyType == KeyType.ArrowRight) {
            screen.setCursorPosition(new TerminalPosition(cursorPos.getColumn() + 1, cursorPos.getRow()));
        } else if (keyType == KeyType.ArrowUp) {
            screen.setCursorPosition(new TerminalPosition(cursorPos.getColumn(), cursorPos.getRow() - 1));
        } else if (keyType == KeyType.ArrowDown) {
            screen.setCursorPosition(new TerminalPosition(cursorPos.getColumn(), cursorPos.getRow() + 1));
        }
    }
}
