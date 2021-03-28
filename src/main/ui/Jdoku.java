package ui;

import java.io.IOException;

import static ui.Game.Mode.*;

public class Jdoku {

    public static void main(String[] args) throws IOException {
        Game game = new Game(CONSOLE);
        game.start();
//        Thread t = new Thread() {
//            public void run() {
//                try {
//                    game.start(Game.Mode.CONSOLE);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        t.start();
    }
}