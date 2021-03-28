package persistance;

import model.Cell;
import model.exceptions.BoardException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

// Decoder responsible for building sudoku from .txt and .json files
public class Decoder extends DirectoryAccess {


    // EFFECTS: instantiates a decoder
    public Decoder() {

    }

    // EFFECTS: returns Hashtable containing the cells produced from a txt file
    // throws FileNotFoundException if board is not on given path
    public Hashtable<Integer, Cell> cellsFromTxtPath(String path, int size) throws FileNotFoundException {

        File file = new File(PUZZLES_PATH + path);
        Scanner scanner = new Scanner(file);
        //start index count
        Integer index = 0;
        Integer indexLim = size * size;
        Hashtable<Integer, Cell> cells = new Hashtable<>(indexLim);

        while (scanner.hasNextLine() && index < indexLim) {
            //remove extraneous characters
            String rowStr = scanner.nextLine().trim();
            rowStr = rowStr.replaceAll("\\s+", "");

            //place characters in row to indexHash
            for (Character c : rowStr.toCharArray()) {
                Cell cell = new Cell(index, c.toString());
                cells.put(index,cell);
                index++;
            }
        }
        scanner.close();
        return cells;
    }

    // EFFECTS: returns game id and names as a list of key value pairs respectively from json
    public List<String> getSavedGames() throws IOException {
        JSONObject db = readJson(JSON_STORE);
        JSONArray savedJson = db.getJSONArray("savedGames");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < savedJson.length(); i++) {
            result.add(String.valueOf(savedJson.get(i)));
        }

        return result;
    }



    public Hashtable<Integer,Cell> cellsFromJsonArray(JSONArray array) {
        Hashtable<Integer,Cell> result = new Hashtable<>();

        for (int i = 0; i < array.length(); i++) {
            result.put(i, new Cell(array.getJSONObject(i)));
        }

        return result;
    }

    private JSONObject readJson(String path) throws IOException {
        String data;
        try {
            data = Files.readAllBytes(Paths.get(path)).toString();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            data = "";
        }

        return new JSONObject(data);
    }

}
