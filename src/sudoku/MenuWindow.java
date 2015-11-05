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
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent; 

public class MenuWindow {

    public static SudokuPuzzle puzzle;
    private GameWindow window;
    private JFrame frmSudoku;
    
    public void makeVisible() {
        this.frmSudoku.setVisible(true);
    }

    public MenuWindow(SudokuPuzzle inputPuzzle) {
        puzzle = inputPuzzle;
        initialize();
    }

    private void initialize() {
        frmSudoku = new JFrame();
        frmSudoku.setTitle("Sudoku");
        frmSudoku.setBounds(100, 100, 450, 300);
        frmSudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSudoku.getContentPane().setLayout(null);
        frmSudoku.setResizable(false);
        
        final ImagePanel MainMenuPanel = new ImagePanel(new ImageIcon(getClass().getResource("/images/MenuBackground.png")).getImage());
        //final ImagePanel MainMenuPanel = new ImagePanel(new ImageIcon("/images/MenuBackground.png").getImage());
        MainMenuPanel.setFocusable(false);
        MainMenuPanel.setBounds(0, 0, 450, 300);
        frmSudoku.getContentPane().add(MainMenuPanel);
        MainMenuPanel.setLayout(null);

        JButton LoadButton = new JButton("Load Puzzle");
        LoadButton.setBounds(44, 104, 120, 50);
        MainMenuPanel.add(LoadButton);
        LoadButton.addActionListener(
            (ActionEvent e) -> {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        java.util.Scanner input = new java.util.Scanner(new java.io.FileInputStream(file.getAbsolutePath()));
                        for(int i = 0; i < 9 ; i++) {
                            for(int j = 0; j < 9 ; j++)
                                puzzle.setValueGrid(i, j, Integer.parseInt(input.next()));
                        }
                        input.close();
                    } catch (java.io.FileNotFoundException e1) {
                        // todo: Display an error to the user
                    }
                    if (puzzle.Solve()) {
                        window = new GameWindow(puzzle);
                        window.makeVisible();
                        frmSudoku.setVisible(false);
                    } else {
                        //todo: Show error to user, loaded puzzle is not solvable
                    }
                    
                } else {
                    // todo: Invalid File
                }
            }
        );

        JButton ExitButton = new JButton("Exit");
        ExitButton.setBounds(44, 165, 120, 50);
        MainMenuPanel.add(ExitButton);
        ExitButton.addActionListener(
            (ActionEvent e) -> {
                System.exit(0);
            }
        );

        final ImagePanel DiffPanel = new ImagePanel(new ImageIcon(getClass().getResource("/images/MenuBackground.png")).getImage());
        DiffPanel.setFocusable(false);
        DiffPanel.setBounds(0, 0, 450, 300);
        frmSudoku.getContentPane().add(DiffPanel);
        DiffPanel.setLayout(null);

        //Create a slider that will be used to set the difficulty of the game
        final JSlider slider = new JSlider();
        slider.setValue(3);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setMinimum(1);
        slider.setMaximum(5);
        slider.setOpaque(false);
        slider.setBounds(46, 210, 200, 26);
        DiffPanel.add(slider);

        //Creates a label for the difficulty slider
        final JLabel diffLabel = new JLabel();
        diffLabel.setText("Typical");
        diffLabel.setFont(new Font("Metropolis 1920",Font.PLAIN,34));
        diffLabel.setForeground(Color.WHITE);
        diffLabel.setBounds(50, 150, 220, 50);
        DiffPanel.add(diffLabel);

        //Prints the difficulty selected for each tick mark on the slider
        slider.addChangeListener(
            (ChangeEvent e) -> {
                switch(slider.getValue()){
                case 1:
                    diffLabel.setText("Very Easy");
                    break;
                case 2:
                    diffLabel.setText("Easy");
                    break;
                case 3:
                    diffLabel.setText("Typical");
                    break;
                case 4:
                    diffLabel.setText("Difficult");
                    break;
                case 5:
                    diffLabel.setText("Very Difficult");
                    break;
                default:
                    //Todo: Do something here
                }
            }
        );

        final JButton StartGame = new JButton("Start");
        StartGame.setBounds(304, 200, 120, 50);
        StartGame.setVisible(false);
        DiffPanel.add(StartGame);
        StartGame.addActionListener(
            (ActionEvent e) -> {
                puzzle.makePuzzle(DifficultyType.fromInt(slider.getValue()));
                window = new GameWindow(puzzle);
                window.makeVisible();
                frmSudoku.setVisible(false);
            }
        );
        
        final JButton BackToMain = new JButton("Back");
        BackToMain.setBounds(10, 11, 89, 23);
        BackToMain.setVisible(false);
        DiffPanel.add(BackToMain);
        BackToMain.addActionListener(
            (ActionEvent e) -> {
                MainMenuPanel.setVisible(true);
                DiffPanel.setVisible(false);
                StartGame.setVisible(false);
                BackToMain.setVisible(false);
            }
        );

        JButton NewButton = new JButton("New Puzzle");
        NewButton.setBounds(44, 43, 120, 50);
        MainMenuPanel.add(NewButton);
        NewButton.addActionListener(
            (ActionEvent e) -> {
                MainMenuPanel.setVisible(false);
                DiffPanel.setVisible(true);
                StartGame.setVisible(true);
                BackToMain.setVisible(true);
            }
        );
    }
}
