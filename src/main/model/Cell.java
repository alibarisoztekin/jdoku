package model;

import com.googlecode.lanterna.TextColor;

public class Cell {
    private Integer index;
    private String value;
    private Boolean interactive;
    private Integer posX;
    private Integer posY;
    private Integer box;
    private TextColor color;


    public Cell(Integer index, String value) {
        this.index = index;
        this.value = value;
        this.posX = index / 9;
        this.posY = index % 9;

    }

    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Integer getBox() {
        return box;
    }

    public String getValue() {
        return value;
    }
}
