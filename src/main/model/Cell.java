package model;

import org.json.JSONException;
import org.json.JSONObject;
import persistence.JsoNotable;

import java.util.Objects;

// Class that represents a single cell in the board
public class Cell implements JsoNotable {
    public String getIndex() {
        return String.valueOf(index);
    }

    private final int index;
    private String value;
    private final boolean interactive;
    private int row;
    private int column;


    // EFFECTS: instantiate cell with index on board and the value with the info from txt file
    public Cell(Integer index, String value) {
        this.index = index;
        this.value = value;
        this.interactive = (Integer.parseInt(value) == 0);
        computeCoords();
    }

    // EFFECTS: instantiate cell from JSON representation
    public Cell(JSONObject json) throws JSONException {
        this.index = (int) json.get("index");
        this.value = (String) json.get("value");
        this.interactive = (boolean) json.get("interactive");
        computeCoords();
    }

    // MODIFIES: this
    // determine row and column location of cell on the board from index
    private void computeCoords() {
        this.row = this.index / 9;
        this.column = this.index % 9;
    }


    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isInteractive() {
        return interactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return index == cell.index
                && interactive == cell.interactive
                && value.equals(cell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value, interactive);
    }

    @Override
    public JSONObject json() {
        JSONObject json = new JSONObject();
        json.put("index", this.index);
        json.put("value", this.value);
        json.put("interactive", this.interactive);
        return json;
    }
}
