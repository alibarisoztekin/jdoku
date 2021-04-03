package ui;

import java.io.IOException;

import static ui.Game.Mode.*;

public class Jdoku {

    public static void main(String[] args) throws IOException {
        Game game = new Game(GUI);
        game.handleRun();

    }
}