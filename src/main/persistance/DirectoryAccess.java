package persistance;

import java.util.Scanner;

// Abstract class providing necessary path information to Encoder and Decoder classes
public abstract class DirectoryAccess {

    protected static String DIR_PATH = "./data/";
    protected static String PUZZLES_PATH = DIR_PATH + "puzzles/";
    protected static String JSON_STORE = DIR_PATH + "db.json";

    protected String currentPath;

    // EFFECTS: instantiate abstract class with path to data directory
    public DirectoryAccess() {
        this.currentPath = DIR_PATH;
    }

}
