package model;

import com.googlecode.lanterna.TextColor;


public class Cell {
    private final Integer index;
    private String value;
    private final Boolean interactive;
    private final Integer row;
    private final Integer column;
    private Integer box;
    private TextColor color;



    public Cell(Integer index, String value) {
        this.index = index;
        this.value = value;
        this.row = index / 9;
        this.column = index % 9;


        if (Integer.parseInt(value) == 0) {
            this.interactive = true;
            this.color = TextColor.ANSI.GREEN;
        } else {
            this.interactive = false;

            this.color = TextColor.ANSI.WHITE;
        }

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

    public TextColor getColor() {
        return color;
    }

    public Boolean isInteractive() {
        return interactive;
    }




}
