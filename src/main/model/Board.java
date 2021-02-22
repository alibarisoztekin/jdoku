package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Board {

    private Hashtable<Integer, Cell> indexHash = new Hashtable<>();
    private Integer size;
    private File file;



    //REQUIRES: dimensions of puzzle must match size
    public Board(File fromText, Integer size) throws FileNotFoundException {
        this.size = size;
        this.file = fromText;
        build();

    }

    public Hashtable<Integer, Cell> getIndexHash() {
        return indexHash;
    }

    public void reload() throws FileNotFoundException {
        build();
    }

    private void build() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        //start index count
        Integer index = 0;
        Integer indexLim = size * size;

        while (scanner.hasNextLine() && index < indexLim) {
            //remove extraneous characters
            String rowStr = scanner.nextLine().trim();
            rowStr = rowStr.replaceAll("\\s+", "");

            //place characters in row to indexHash
            for (Character c : rowStr.toCharArray()) {
                Cell cell = new Cell(index, c.toString());
                indexHash.put(index,cell);
                index++;
            }
        }
        scanner.close();
    }

    public void clearBoard() {
        indexHash.clear();
    }


}
