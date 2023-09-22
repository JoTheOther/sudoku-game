import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreatePage extends SudokuGrid {

    JMenuItem startItem;

    CreatePage() {

        // get the cell fields, but this time add a key listener to all of them
        JTextField[][] cellFields = super.getCellFields();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int finalI = i;
                int finalJ = j;
                cellFields[finalI][finalJ].addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {

                        // make sure user can only input numbers from 1-9, after each input update the
                        // board and check if it its completed and correctly solved
                        String currentText = cellFields[finalI][finalJ].getText();
                        if (currentText.matches("[1-9]")) {
                            cellFields[finalI][finalJ].setEditable(false);
                            int[][] board = getStateOfBoard();
                            if (isGridCompleted()) {
                                if (isGridValid(board)) {
                                    JOptionPane.showMessageDialog(frame, "The Sudoku is correctly solved!",
                                            "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "You made a mistake...",
                                            "Try again", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            // replace any input other than number between 1 and 9
                            cellFields[finalI][finalJ].setText(currentText.replaceAll("[^1-9]", ""));
                        }
                    }
                });
            }
        }
    }

    // configure a new menu bar that has to be pressed to start the custom game
    @Override
    public JMenuBar configureMenuBar() {
        JMenuBar menuBar = super.configureMenuBar();

        JMenu startMenu = new JMenu("Start");
        startItem = new JMenuItem("Start Sudoku");

        startItem.addActionListener(this);

        startMenu.add(startItem);
        menuBar.add(startMenu);

        return menuBar;
    }

    // call the startCustomSudoku method when start button (in menu bar) is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (e.getSource() == startItem) {
            startCustomSuoku();
            System.out.println("Start Sudoku button clicked");
        }
    }

    public void startCustomSuoku() {

        // get the current state of the board and check if it is valid and solvable
        int[][] board = getStateOfBoard();
        if (isGridValid(board)) {
            if (Sudoku.solve(board)) {
                // the board is solvable, mark user-input cells with light blue
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        if (board[i][j] != 0 && !cellFields[i][j].getText().isEmpty()) {
                            cellFields[i][j].setBackground(new Color(0xD6E6F2));
                        }
                    }
                }
            }
        } else {
            // board is not valid, error message pops up and user can edit and correct the
            // puzzle
            JOptionPane.showMessageDialog(frame, "The game can't be started.\nYour Sudoku is not valid.", "Invalid",
                    JOptionPane.ERROR_MESSAGE);
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    cellFields[i][j].setEditable(true);
                }
            }
        }
    }
}
