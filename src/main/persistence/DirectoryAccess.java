package persistence;


// Abstract class providing necessary path information to subclasses
public abstract class DirectoryAccess {

    protected static String DIR_PATH = "./data/";
    protected static String PUZZLES_PATH = DIR_PATH + "puzzles/";
    protected static String JSON_STORE = DIR_PATH + "db.json";
    protected static String TEST = DIR_PATH + "test.json";

    protected String currentPath;

    // EFFECTS: instantiate abstract class with path to data directory
    public DirectoryAccess() {
        this.currentPath = DIR_PATH;
    }

}
