package persistence;

import model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Game;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EncoderTest {
    private Game game;

    @BeforeEach
    public void setup() {
        this.game = new Game(Game.Mode.TEST);
        try {
            this.game.handleRun();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game.newWithDifficulty(new Difficulty(0));
    }

    @Test
    public void testSave() {
        try {
            this.game.save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


