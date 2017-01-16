SuDoku
==============
Sudoku game and solver written in Java.

Files
-----

* SudokuPuzzle.java - Models, solves, and checks a sudoku puzzle.
* MenuWindow.java - GUI and controller code.
* GameWindow.java - GUI for sudoku game.
* ImagePanel.java - Background handler for Menu and Game.
* DifficultyType - Enum defining sudoku difficulty level.

Features
--------

* Generate sudoku puzzles of various difficulties
* Save/Load puzzles to a human readable file
* Verify a puzzle is solvable
* Auto-Solve puzzles

Usage
-----

If you simply wish to use the program as is you need only to run dist/Sudoku.jar. If you want to use the SudokuPuzzle class then you will need SudokuPuzzle.java and DifficultyType.java. The basic operation of the class is as follows...

Make a puzzle ...

    SudokuPuzzle puzzle = new SudokuPuzzle(); // Create a puzzle with default settings.(see below)
    puzzle.makePuzzle(Normal); // Making a puzzle this way also finds the puzzle's solution
    //Or
    SudokuPuzzle puzzle = new SudokuPuzzle(Normal)

Loading a puzzle...

    SudokuPuzzle puzzle = new SudokuPuzzle( int[][] inputGrid )

Inspecting and Setting values in the puzzle...

    puzzle.getValueGrid(i,j)
    puzzle.setValueGrid(i,j,value)

Loading and saving from a flat file is handled by looping over the grids and writting to a file in GameWindow.

Issues
-------
* When checking if a puzzle is solvable ...Currently the check is wring 16 issue
* Loading an unsolvable puzzle does not produce an error.
* Loading a file that throws FileNotFoundException does not produce an error.

Contact
-------
If you have questions, concerns, or comments please feel free to contact me via 

    adam.a.nunez@gmail.com
License
-------
Copyright (C) 2014  Adam Nunez
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See theGNU General Public License for more details.
