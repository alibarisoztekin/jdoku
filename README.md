# Jdoku
## Sudoku in Java

A 9x9 sudoku game implemented in Java. 

As a fan of sudoku, I wanted to explore the implementation of backtracking search to verify user inputs.
In its current form, the game is borderless for the sake of simplicity 
without losing functionality with no verifying functionality.

The roadmap involves implementing difficulty logic with common sudoku techniques required to solve the puzzle.

*Phase 1 User Stories:*
- As a user, I'd like to add values to the puzzle
- As a user, I'd like to be able to remove values I've added.
- As a user, I'd like to be able to restart a game.
- As a user, I'd like to be able to move in the game.

*Phase 2 User Stories:*
- As a user, I'd like to save a game
- As a user, I'd like to be load saved games.

-*Phase 3 User Stories:*
 - As a user, I'd like be able to play the game with keyboard and mouse 
 - As a user, I'd like to be able to save my progress and load saved game on start.

-*Phase 4:*
- Task 2: Robust design of Board class, constructor 
throws BoardException if .txt File within name specification is not found

    { additionally Driver interface provides decoupling of UI logic and instantion with start()}
    
- Task 3: 

There's semantic coupling between New Game view and Difficulty and Console driver
a better implementation of difficulty selection logic would be beneficial 

Logic between Sudoku Board and Game can be better implemented 

Unit testing of model and persistence packages is also lacking coverage and needs more tests




