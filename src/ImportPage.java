import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImportPage extends SudokuGrid {

    ImportPage() {

        // get the puzzle from text file
        int[][] board = getValuesFromTextFile();

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

    // method to select a text file containing 9x9 numbers and store them
    public static int[][] getValuesFromTextFile() {

        // create file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        // get the selected file, use buffered reader to read from each line and add the
        // character at the current position to the board array
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int[][] board = new int[GRID_SIZE][GRID_SIZE];
                String line;
                int row = 0;
                while ((line = reader.readLine()) != null && row < GRID_SIZE) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        char c = line.charAt(col);
                        if (Character.isDigit(c)) {
                            board[row][col] = Character.getNumericValue(c);
                        }
                    }
                    row++;
                }
                return board;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // if user cancels or closes file chooser, hide Sudoku grid window
            frame.setVisible(false);
        }
        return null;
    }
}
