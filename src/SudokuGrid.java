import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SudokuGrid extends JPanel implements ActionListener {

    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;

    public static JFrame frame;
    public JTextField[][] cellFields;
    public JMenuItem solveItem;
    public JMenuItem helpItem;

    SudokuGrid() {

        // configure graphical user interface
        frame = new JFrame();
        frame.setTitle("Sudoku");
        frame.setResizable(false);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE, 0, 0));
        frame.setJMenuBar(configureMenuBar());

        // configure cell fields
        cellFields = new JTextField[GRID_SIZE][GRID_SIZE];
        JPanel[][] subGrids = new JPanel[SUBGRID_SIZE][SUBGRID_SIZE];

        // create 9x9 grid of 3x3 sub-grids using JPanel, each sub-grid is seperated by
        // a border
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                JPanel subGrid = new JPanel(
                        new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE, 5, 5));
                subGrid.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                subGrids[i][j] = subGrid;
                frame.add(subGrid);
            }
        }

        // create JTextFields and add them to their respective sub-grids
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JTextField cellField = new JTextField();
                cellField.setHorizontalAlignment(JTextField.CENTER);
                cellField.setFont(new Font("Comic Sans", Font.BOLD, 20));
                cellFields[i][j] = cellField;

                int subGridRow = i / SUBGRID_SIZE;
                int subGridColumn = j / SUBGRID_SIZE;
                subGrids[subGridRow][subGridColumn].add(cellField);
            }
        }
        frame.setVisible(true);
    }

    // configure the menu bar
    public JMenuBar configureMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu solveMenu = new JMenu("Solution");
        JMenu helpMenu = new JMenu("Help");

        solveItem = new JMenuItem("Show Solution");
        helpItem = new JMenuItem("About");

        solveItem.addActionListener(this);
        helpItem.addActionListener(this);

        solveMenu.add(solveItem);
        helpMenu.add(helpItem);
        menuBar.add(solveMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    // getter method for sudoku grid
    public JTextField[][] getCellFields() {
        return cellFields;
    }

    // stores current state of board in a 2D array
    public int[][] getStateOfBoard() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                try {
                    int value = Integer.parseInt(cellFields[i][j].getText());
                    board[i][j] = value;
                } catch (NumberFormatException ex) {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    // checks wether the Sudoku grid is completed or not
    public boolean isGridCompleted() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (cellFields[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // checks the 9x9 Sudoku Grid if it is valid or not by using a variable 'seen'
    // to check for duplicates
    public boolean isGridValid(int[][] grid) {

        // check rows
        for (int i = 0; i < GRID_SIZE; i++) {
            boolean[] seen = new boolean[GRID_SIZE + 1];
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = grid[i][j];
                if (value != 0) {
                    if (seen[value]) {
                        return false;
                    } else {
                        seen[value] = true;
                    }
                }
            }
        }
        // check columns
        for (int j = 0; j < GRID_SIZE; j++) {
            boolean[] seen = new boolean[GRID_SIZE + 1];
            for (int i = 0; i < GRID_SIZE; i++) {
                int value = grid[i][j];
                if (value != 0) {
                    if (seen[value]) {
                        return false;
                    } else {
                        seen[value] = true;
                    }
                }
            }
        }
        // check sub-grids
        for (int i0 = 0; i0 < SUBGRID_SIZE; i0++) {
            for (int j0 = 0; j0 < SUBGRID_SIZE; j0++) {
                boolean[] seen = new boolean[GRID_SIZE + 1];
                for (int i = i0 * SUBGRID_SIZE; i < (i0 + 1) * SUBGRID_SIZE; i++) {
                    for (int j = j0 * SUBGRID_SIZE; j < (j0 + 1) * SUBGRID_SIZE; j++) {
                        int value = grid[i][j];
                        if (value != 0) {
                            if (seen[value]) {
                                return false;
                            } else {
                                seen[value] = true;
                            }
                        }
                    }
                }
            }
        }
        // all checks passed -> grid is valid
        return true;
    }

    // configure the actions that should be performed when a specififc button is
    // pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        // try to solve the current state of the Sudoku by using the backtracking
        // algorithm
        if (e.getSource() == solveItem) {
            int[][] board = getStateOfBoard();
            if (!isGridValid(board)) {
                JOptionPane.showMessageDialog(
                        frame,
                        "The Sudoku grid is not valid.\nYou made a mistake.",
                        "Invalid Grid",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Sudoku.solve(board);
            // update the text fields with the solved values
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    cellFields[i][j].setText(Integer.toString(board[i][j]));
                    cellFields[i][j].setEditable(false);
                }
            }
        }
        // show an information message about the rules and tips
        if (e.getSource() == helpItem) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Sudoku is a logic-based puzzle game played on a 9x9 grid, divided into nine 3x3 sub-grids.\n" +
                            "The goal is to fill each empty cell with a number from 1 to 9, such that each row, " +
                            "column,\nand sub-grid contains all the digits 1 to 9 without any repetition.\nThe puzzle is "
                            +
                            "solved when all the cells are filled correctly.\n\nIf you are stuck you can reveal the " +
                            "solution by pressing 'Solution'.",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
