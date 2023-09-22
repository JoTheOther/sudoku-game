import javax.swing.*;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class PlayPage extends SudokuGrid {

    PlayPage() {

        // generate a random puzzle
        int[][] board = SudokuGenerator.generateSudoku();

        // get the cell fields of the grid, loop through it and fill it with the
        // generated puzzle
        JTextField[][] cellFields = super.getCellFields();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] != 0) {
                    cellFields[i][j].setText(Integer.toString(board[i][j]));
                    cellFields[i][j].setBackground(new Color(0xD6E6F2));
                    cellFields[i][j].setEditable(false);
                } else {
                    final int finalI = i;
                    final int finalJ = j;
                    // add key listener to the empty cells so the user can type into fields
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
    }
}
