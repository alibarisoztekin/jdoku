package persistance;

import ui.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Encoder extends DirectoryAccess {

    private static final int TAB = 4;


    public Encoder(Game.Mode mode) {
        if (mode == Game.Mode.TEST) {
            currentPath = TEST;
        } else {
            currentPath = JSON_STORE;
        }
    }

    public void save(Game game) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(currentPath));
        writer.print(game.json().toString(TAB));
        writer.close();
    }
}
