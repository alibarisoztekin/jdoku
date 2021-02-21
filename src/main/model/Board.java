package model;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Board {

    private Hashtable<Integer, Cell> indices = new Hashtable<>();
    private Integer size;


    public Board(Integer size) {
        this.size = size;
        fillRandom();
    }

    private void fillRandom() {
        for (int i = 0; i < size * size; i++) {
            Random random = new Random();
            String val = String.valueOf(random.nextInt(size));
            indices.put(i,new Cell(i,val));
        }
    }

    public Hashtable<Integer, Cell> getIndices() {
        return indices;
    }
}
