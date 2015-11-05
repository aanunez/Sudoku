// Author: Adam Nunez, adam.a.nunez@gmail.com
// Copyright (C) 2015  Adam Nunez
// 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
package Sudoku;

import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    
    public static void main(String[] args){
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (ClassNotFoundException | InstantiationException | 
               IllegalAccessException | UnsupportedLookAndFeelException e){}
        SudokuPuzzle puzzle = new SudokuPuzzle();
        MenuWindow window = new MenuWindow(puzzle);
        window.makeVisible();
    }
    
}