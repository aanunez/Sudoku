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
