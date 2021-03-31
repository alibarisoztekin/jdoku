package persistance;

import model.Board;
import model.Cell;
import model.Difficulty;
import model.Sudoku;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

// Decoder responsible for building sudoku from .txt and .json files
public class Decoder extends DirectoryAccess {

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
                cells.put(index, cell);
                index++;
            }
        }
        scanner.close();
        return cells;
    }

    // EFFECTS: returns game id and names as a list of key value pairs respectively from json
    public List<String> getIds() throws IOException, JSONException {
        JSONObject db = readJson(JSON_STORE);
        JSONArray savedJson = db.getJSONArray("ids");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < savedJson.length(); i++) {
            result.add(String.valueOf(savedJson.get(i)));
        }

        return result;
    }

    public HashMap<String, Sudoku> loadGames() throws IOException, JSONException {
        JSONObject games = readJson(JSON_STORE).getJSONObject("savedGames");
        HashMap<String, Sudoku> parsed = new HashMap<>();

        for (Object key : games.keySet()) {
            String keyStr = (String) key;
            JSONObject gameJson = (JSONObject) games.get(keyStr);

            Difficulty difficulty = new Difficulty((String) gameJson.get("difficulty"));

            JSONArray boardJson = gameJson.getJSONArray("board");
            Board board = new Board(cellsFromJsonArray(boardJson));
            Sudoku sudoku = new Sudoku(board, difficulty);
            parsed.put(keyStr, sudoku);
        }
        return parsed;

    }

    public Hashtable<Integer, Cell> cellsFromJsonArray(JSONArray array) {
        Hashtable<Integer, Cell> result = new Hashtable<>();

        for (int i = 0; i < array.length(); i++) {
            result.put(i, new Cell(array.getJSONObject(i)));
        }

        return result;
    }

    private JSONObject readJson(String path) throws IOException {
        JSONObject object;
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
            object = new JSONObject(contentBuilder.toString());
        } catch (JSONException je) {
            je.printStackTrace();
            object = new JSONObject();
        }


        return object;
    }

}
