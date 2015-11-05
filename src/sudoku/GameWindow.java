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

import java.awt.Color;
import java.awt.KeyboardFocusManager;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import javax.swing.JTextArea;

public class GameWindow {
	
    public static SudokuPuzzle puzzle;
    private static int keyboardfixer = 0;
    private JFrame frmSudoku;

    public void makeVisible() {
        this.frmSudoku.setVisible(true);
    }

    public GameWindow(SudokuPuzzle inputPuzzle) {
        puzzle = inputPuzzle;
        initialize();
    }

    private void initialize() {
        UIManager.put("FormattedTextField.font", new Font("Tahoma", Font.BOLD, 20));
        final long starttime= System.currentTimeMillis();

        frmSudoku = new JFrame();
        frmSudoku.setResizable(false);
        frmSudoku.getContentPane().setFocusCycleRoot(true);
        frmSudoku.setTitle("Sudoku");
        frmSudoku.setBounds(100, 100, 600, 500);
        frmSudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSudoku.getContentPane().setLayout(null);
        frmSudoku.setResizable(false);

        //Initialize each square of the sudoku puzzle as JFormattedTextFields - 81 squares
        JFormattedTextField[] array = new JFormattedTextField[81];
        for(int i=0; i<array.length; i++){
            array[i] = new JFormattedTextField();
            array[i].setBounds(40*(i%9)+51, 41*((int) i/9)+51 ,30,30);
            frmSudoku.getContentPane().add(array[i]);
        }

        //Create a map so we can look stuffs up
        final Map<Integer, JFormattedTextField> map = new HashMap<>();
        for(int i=0; i<array.length; i++)
            map.put(i, array[i]);

        //"Save Puzzle" button implementation
        JButton SaveButton = new JButton("Save Puzzle");
        SaveButton.setBounds(431, 52, 120, 50);
        frmSudoku.getContentPane().add(SaveButton);
        SaveButton.addActionListener(
            (ActionEvent e) -> {
                //The event triggered when "Save Puzzle" button is clicked
                //a window pops-up allowing the user to select a folder to save the puzzle in
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        PrintWriter output = new PrintWriter(new FileOutputStream(file.getAbsolutePath(),false));
                        for(int i = 0; i < 9 ; i++) {
                            for(int j = 0; j < 9 ; j++)
                                output.print(puzzle.grid[i][j] + " ");
                        }
                        output.close();
                    } catch (FileNotFoundException e1) {
                        // todo: This will never happen, or will it?
                    }
                } else {
                    // todo: Invalid File
                }
            }
        );

        //"Exit" button implementation
        JButton ExitButton = new JButton("Exit");
        ExitButton.setBounds(431, 383, 120, 50);
        frmSudoku.getContentPane().add(ExitButton);
        ExitButton.addActionListener(
            (ActionEvent e) -> {
                System.exit(0);
            }
        );

        //"Show Answer" button implementation
        JButton SolutionButton = new JButton("Show Answer");
        SolutionButton.setBounds(431, 174, 120, 50);
        frmSudoku.getContentPane().add(SolutionButton);
        SolutionButton.addActionListener(
            (ActionEvent e) -> {
                //The event triggered when "Show Answer" button is clicked
                //solves the puzzle by checking each square and filling in the right numbers
                for(int i = 0; i < 81 ; i++){
                    map.get(i).setText(" "+String.valueOf(puzzle.solu[(int) i/9][i%9]));
                    map.get(i).setEditable(false);
                }
            }
        );

        final JTextArea txtrCongratulationsTime = new JTextArea();
        txtrCongratulationsTime.setBackground( new Color(192,192,192, 255) );
        txtrCongratulationsTime.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtrCongratulationsTime.setText("   Congratulations!\r\n");
        txtrCongratulationsTime.setEditable(false);
        txtrCongratulationsTime.setBounds(420, 235, 147, 93);
        txtrCongratulationsTime.setVisible(false);
        frmSudoku.getContentPane().add(txtrCongratulationsTime);

        //"Check Input" button implementation
        final JButton CheckButton = new JButton("Check Input");
        CheckButton.setBounds(431, 113, 120, 50);
        frmSudoku.getContentPane().add(CheckButton);
        frmSudoku.getRootPane().setDefaultButton(CheckButton);
        CheckButton.addActionListener(
            (ActionEvent e) -> {
                for(int i = 0; i < 81; i++){
                    //if the input is correct, it sets the color of the number to green 
                    //and the user will not be able to edit that box
                    if(map.get(i).isEditable() && !map.get(i).getText().trim().isEmpty() && Character.isDigit(map.get(i).getText().trim().charAt(0)) ){
                        if(((int) map.get(i).getText().trim().charAt(0)-puzzle.solu[(int) i/9][i%9])-48 == 0){
                            puzzle.grid[(int) i/9][i%9]=map.get(i).getText().trim().charAt(0)-48;
                            map.get(i).setForeground(Color.BLACK);map.get(i).setEditable(false);
                        }
                        else
                            map.get(i).setForeground(Color.RED); //if the input is incorrect, it sets the color of the number to blue
                        map.get(i).setText(" "+map.get(i).getText().trim().charAt(0));
                    }
                }
                //Once puzzle is solved correctly, Congratulations message and time taken to complete puzzle will appear
                if(puzzle.isSolved() && !txtrCongratulationsTime.isVisible()){
                    txtrCongratulationsTime.setText("   Congratulations!"+
                            System.getProperty("line.separator")+
                            System.getProperty("line.separator")+
                            "       "+
                            TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-starttime) +
                            "  minute(s)");
                    txtrCongratulationsTime.setVisible(true);
                }
            }
        );

        //Sets the grid image in GameWindow
        ImagePanel panel = new ImagePanel(new ImageIcon(getClass().getResource("/images/grid.jpg")).getImage());
        panel.setFocusable(false);
        panel.setBounds(46, 46, 361, 370);
        frmSudoku.getContentPane().add(panel);

        //Sets a background image for GameWindow
        ImagePanel backGndPanel = new ImagePanel(new ImageIcon(getClass().getResource("/images/background.png")).getImage());
        backGndPanel.setFocusable(false);
        backGndPanel.setBounds(0, 0, 600, 500);
        frmSudoku.getContentPane().add(backGndPanel);

        //Allows user to navigate the puzzle using the keyboard arrow keys
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
            (KeyEvent e) -> {
                if (GameWindow.keyboardfixer >= 1){
                    int keyCode = e.getKeyCode();
                    switch( keyCode ) {
                    case KeyEvent.VK_UP:
                        for(int i = 9; i < 81; i++){
                            if(frmSudoku.getFocusOwner() == map.get(i) ){
                                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent(map.get(i-8));
                                break;
                            }
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        for(int i = 0; i < 72; i++){
                            if(frmSudoku.getFocusOwner() == map.get(i) ){
                                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent(map.get(i+8));
                                break;
                            }
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                        break;
                    case KeyEvent.VK_RIGHT:	
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                        break;
                    case KeyEvent.VK_C:
                        CheckButton.doClick();
                        break;
                    }
                    GameWindow.keyboardfixer--;
                }
                else{GameWindow.keyboardfixer++;}
                    return false;
            }
        );

        //Set Static Values
        for(int i = 0; i < 81; i++){
            if(puzzle.grid[(int) i/9][i%9]!=0){
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent(map.get(i+8));
                map.get(i).setText(" "+String.valueOf(puzzle.grid[(int) i/9][i%9]));
                map.get(i).setEditable(false);
            }
        }

    }
}
