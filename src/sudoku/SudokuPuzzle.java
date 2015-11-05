package Sudoku;

import java.util.Arrays;
import java.util.Random;

public class SudokuPuzzle {

    private boolean solvedFlag = false;
    private boolean solutionFlag = false;
    private DifficultyType currentDifficulty = DifficultyType.None;
    public int[][] grid = new int[9][9];
    public int[][] solu = new int[9][9];
    private final int[][][] Seeds =
                  {{{4,2,9,3,1,6,5,7,8},
                    {8,6,7,5,2,4,1,9,3},
                    {5,1,3,8,9,7,2,4,6},
                    {9,3,1,7,8,5,6,2,4},
                    {6,8,2,9,4,1,7,3,5},
                    {7,4,5,2,6,3,9,8,1},
                    {3,5,4,6,7,2,8,1,9},
                    {1,7,8,4,5,9,3,6,2},
                    {2,9,6,1,3,8,4,5,7}},
                   {{9,6,5,4,1,8,7,3,2},
                    {1,4,3,2,6,7,9,5,8},
                    {8,2,7,9,5,3,6,1,4},
                    {5,7,9,3,8,4,1,2,6},
                    {4,1,2,6,9,5,3,8,7},
                    {6,3,8,1,7,2,4,9,5},
                    {3,5,4,7,2,1,8,6,9},
                    {7,8,6,5,3,9,2,4,1},
                    {2,9,1,8,4,6,5,7,3}},
                   {{1,2,5,8,9,7,6,3,4},
                    {6,7,4,5,1,3,9,2,8},
                    {3,9,8,4,2,6,1,5,7},
                    {4,8,2,6,5,9,7,1,3},
                    {7,6,9,2,3,1,4,8,5},
                    {5,3,1,7,8,4,2,9,6},
                    {2,4,3,9,7,5,8,6,1},
                    {9,5,6,1,4,8,3,7,2},
                    {8,1,7,3,6,2,5,4,9}}};

    //**************************************************************************
    //Constructors
    //**************************************************************************
        
    public SudokuPuzzle() {
        //Nothing to initialize
    }

    public SudokuPuzzle( int[][] inputGrid ) {
        grid = inputGrid;
        Solve();
    }
    
    public SudokuPuzzle( DifficultyType diff ) {
        makePuzzle(diff);
    }

    //**************************************************************************
    // Get, Set, and Clear
    //**************************************************************************
    
    public int getValueGrid(int i, int j) {
        return grid[i][j]; 
    }
    
    public int getValueSolution(int i, int j) {
        return solu[i][j]; 
    }
    
    public DifficultyType getCurrentDifficulty () {
        return currentDifficulty;
    }
    
    public void setValueGrid(int i, int j, int val) {
        grid[i][j] = val; 
    }
    
    public void setValueSolution(int i, int j, int val) {
        solu[i][j] = val;
    }
    
    public boolean setDifficulty(DifficultyType diff) {
        if (currentDifficulty.asInt() >= diff.asInt()) return false;
        Random r = new Random();
        int delete = 0;
        int lwBnd = 6 - diff.asInt();

        switch(diff) {
            case VeryEasy:
                delete = 20 + r.nextInt(11);
                lwBnd = 5;
                break;
            case Easy:
                delete = 20 + r.nextInt(13);
                break;
            case Normal:
                delete = 47 + r.nextInt(4);
                break;
            case Hard:
                delete = 50 + r.nextInt(4);
                break;
            case VeryHard:
                delete = 54 + r.nextInt(4);
                lwBnd = 0;
                break;
            case None: return false;
        }
        for(int i = 0; i < delete; i++)
            randomRemove(lwBnd, delete);
        currentDifficulty = diff;
        return true;
    }
    
    public void clearGrid() {
        grid = new int[9][9];
        clearSolvedFlag();
        currentDifficulty = DifficultyType.None;
    }
    
    public void clearSolution() {
        solu = new int[9][9];
        clearSolvedFlag();
    }

    public void clearValueGrid(int i, int j) {
        grid[i][j] = 0; 
    }

    public void clearValueSolution(int i, int j) {
        solu[i][j] = 0; 
        clearSolvedFlag();
    }
    
    public void setSolvedFlag() {
        solvedFlag = true;
    }
    
    public void clearSolvedFlag() {
        solvedFlag = false;
    }
    
    public void setSolutionFlag() {
        solutionFlag = true;
    }
    
    public void clearSolutionFlag() {
        solutionFlag = false;
    }
    
    //**************************************************************************
    // Core Operations
    //**************************************************************************
    
    public boolean isSolved() {
        if (solvedFlag) return true;
        int sumRow = 0;
        int sumCol = 0;
        int dupRow = 0;
        int dupCol = 0;
        for(int i = 0; i < 9 ; i++) {
            for(int j = 0; j < 9 ; j++) {
                sumRow  = sumRow + grid[i][j];
                sumCol  = sumCol + grid[j][i];
                dupRow |= 1 << (grid[i][j]-1);
                dupCol |= 1 << (grid[j][i]-1);
            }
            if( (sumRow != 45) || (sumCol != 45) || (dupRow != 511) || (dupCol != 511) )
                return false;
            sumRow = 0;
            sumCol = 0;
            dupRow = 0;
            dupCol = 0;
        }
        int sqSum = 0;
        int sqDup = 0;
        for(int k = 0; k < 3 ; k++) {
            for(int l = 0; l < 3 ; l++) {
                for(int i = 0+3*k; i < 3+3*k ; i++) {
                    for(int j = 0+3*l; j < 3+3*l ; j++) {
                        sqSum  = sqSum + grid[i][j];
                        sqDup |= 1 << (grid[i][j]-1);
                    }
                }
                if( (sqSum != 45) || (sqDup != 511) ) return false;
                sqSum = 0;
                sqDup = 0;
            }
        }
        return true;
    }
    
    public boolean isSolution() {
        if (solutionFlag) return true;
        return false;
    }

    public boolean isValid() {
        if (solvedFlag||solutionFlag) return true;
        int counter = 0;
        for(int i = 0; i < 9 ; i++) {
            for(int j = 0; j < 9 ; j++) {
                if (grid[i][j] != 0) counter++; 
                if ( (0 > grid[i][j]) | (grid[i][j] > 10) ) return false; 
            }
        }
        if (counter > 16) return true; // A sudoku with less than 16 cells is not solvable, google it.
        return false; 
    }

    public final boolean Solve() {
        if (solvedFlag||solutionFlag) return true;
        if(!isValid()) return false;
        for( int i = 0; i < 9; i++ )
            System.arraycopy(grid[i], 0, solu[i], 0, 9);
        try{
            Backtrack_solver(0,0);
        }
        catch(Exception e) {
            return true;
        }
        return false;
    }

    final public void makePuzzle( DifficultyType diff ) {
        radomizeSeed();
        for( int i = 0; i < 9; i++ )
            System.arraycopy(grid[i], 0, solu[i], 0, 9);
        setDifficulty(diff);
        setSolutionFlag();
    }
    
    public void randomizeGrid() {
        Random r = new Random();
        for(int k =0; k < 500; k++) {
            int a = r.nextInt(6);
            switch(a) {
                case 0:
                    rowSwap(r.nextInt(3),r.nextInt(3),r.nextInt(3));
                    break;
                case 1:
                    columnSwap(r.nextInt(3),r.nextInt(3),r.nextInt(3));
                    break;
                case 2:
                    triRowSwap(r.nextInt(3),r.nextInt(3));
                    break;
                case 3:
                    triColumnSwap(r.nextInt(3),r.nextInt(3));
                    break;
                case 4:
                    diagSwapLR();
                    break;
                case 5:
                    diagSwapRL();
                    break;
                default:
                    break;
            }
        }
    }
    
    //**************************************************************************
    // Private Solver Functions
    //**************************************************************************

    private void Backtrack_solver(int row, int col) throws Exception{
        if( row > 8 ) throw new Exception( "Solution found" );

        if( solu[row][col] != 0 ) next( row, col );
        else{
            for( int num = 1; num < 10; num++ ) {
                if( checkRow(row,num) && checkColumn(col,num) && checkSquare(row,col,num) ) {
                    solu[row][col] = num;
                    next( row, col );
                }
            }
            solu[row][col] = 0 ;
        }
    }

    private void next(int row, int col) throws Exception{
        if( col < 8 ) Backtrack_solver( row, col + 1 );
        else Backtrack_solver( row + 1, 0 );
    }

    private boolean checkRow(int row, int val) {
        for( int col = 0; col < 9; col++ ) {
            if( solu[row][col] == val ) return false;
        }
        return true ;
    }

    private boolean checkColumn(int col, int val) {
        for( int row = 0; row < 9; row++ ) {
             if( solu[row][col] == val ) return false;
        }
        return true;
    }

    private boolean checkSquare(int row, int col, int val) {
        row = (row / 3) * 3 ;
        col = (col / 3) * 3 ;

        for( int i = 0; i < 3; i++ ) {
            for( int j = 0; j < 3; j++ ) {
                if( solu[row+i][col+j] == val ) return false;
            }
        }
        return true ;
    }
    
    //**************************************************************************
    // Private Puzzle Generation Functions
    //**************************************************************************
    
    private void radomizeSeed() {
        Random r = new Random();
        grid = Seeds[r.nextInt(3)];
        randomizeGrid();
    }    
    
    private void randomRemove(int lwBnd, int delete) {
        Random r = new Random();
        int saveVal;
        int saveRow;
        int saveCol;
        int popRow = 0;
        int popCol = 0;

        saveRow = r.nextInt(9);
        saveCol = r.nextInt(9);
        while(grid[saveRow][saveCol]==0) {
            saveRow = r.nextInt(9);
            saveCol = r.nextInt(9);
        }
        saveVal = grid[saveRow][saveCol];
        grid[saveRow][saveCol] = 0;
        for(int j = 0; j < 9; j++) {
            if(grid[saveRow][j]!=0) popRow++;
            if(grid[j][saveCol]!=0) popCol++;
        }
        if((popRow < lwBnd) || (popCol < lwBnd)) {
            grid[saveRow][saveCol] = saveVal;
            randomRemove(lwBnd, delete);
        }
    }

    private void rowSwap(int sec,int row1, int row2) {
        if (row1==row2) return;
        int[] savRow = grid[sec*3+row1];
        grid[sec*3+row1] = grid[sec*3+row2];
        grid[sec*3+row2] = savRow;
    }

    private void columnSwap(int sec,int col1, int col2) {
        if (col1==col2) return;
        int savCol;
        for(int i = 0; i < 9; i++) {
            savCol = grid[i][sec*3+col1];
            grid[i][sec*3+col1] = grid[i][sec*3+col2];
            grid[i][sec*3+col2] = savCol;
        }
    }

    private void triRowSwap(int row1, int row2) {
        if(row1==row2) return;
        int[] savRow;
        for(int i = 0; i < 3; i++) {
            savRow = grid[3*row1+i];
            grid[3*row1+i] = grid[3*row2+i];
            grid[3*row2+i] = savRow;
        }
    }

    private void triColumnSwap(int col1, int col2) {
        if(col1==col2) return;
        int savCol;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 3; j++) {
                savCol = grid[i][3*col1+j];
                grid[i][3*col1+j] = grid[i][3*col2+j];
                grid[i][3*col2+j] = savCol;
            }
        }
    }

    private void diagSwapLR() {
        int save;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if (j>i) {
                    save = grid[j][i];
                    grid[j][i] = grid[i][j];
                    grid[i][j] = save;
                }
            }
        }
    }

    private void diagSwapRL() {
        int save;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if ((i + j) < 8) {
                    save = grid[i][j];
                    grid[i][j] = grid[8-j][8-i];
                    grid[8-j][8-i] = save;
                }
            }
        }
    }
}