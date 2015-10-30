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