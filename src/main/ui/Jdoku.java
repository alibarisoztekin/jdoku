package ui;

import java.io.IOException;

import static ui.Game.Mode.*;

// Runnable class of application
public class Jdoku {

    public static void main(String[] args) throws IOException {
        Game game = new Game(CONSOLE);
        game.handleRun();

    }
}