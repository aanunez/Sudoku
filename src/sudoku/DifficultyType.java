/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sudoku;

/**
 *
 * @author adam
 */
public enum DifficultyType {
    None(0),
    VeryEasy(1),
    Easy(2),
    Normal(3),
    Hard(4),
    VeryHard(5);
    
    private final int value;
    
    private DifficultyType( int value ) {
        this.value = value;
    }
    
    public int asInt() {
        return value;
    }
    
    public static DifficultyType fromInt(int x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return VeryEasy;
            case 2:
                return Easy;
            case 3:
                return Normal;
            case 4:
                return Hard;
            case 5:
                return VeryHard;
            default:
                return null;
        }
    }
}
