package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// Difficulty of sudoku game {not an ideal implementation, enum wasn't suitable}
public class Difficulty {

    private String description;
    private List<String> list = setup();

    public Difficulty(String description) {

        this.description = description;
    }

    public Difficulty(int val) {

        this.description = list.get(val);
    }


    private static  ArrayList<String> setup() {
        ArrayList<String> list = new ArrayList<>();
        list.add("EASY");
        list.add("MEDIUM");
        list.add("HARD");
        return list;
    }

    @Override
    public String toString() {
        return description.substring(0,1);
    }


    public String name() {
        return description;
    }


}
