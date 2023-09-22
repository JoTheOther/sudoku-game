import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * This class contains the solver algorithm, as well as a method to check if a
 * given number is valid
 */

public class Sudoku {

    public static final int GRID_SIZE = 9;

    public static void main(String[] args) {
        new LaunchPage();
    }

    // function to check if a given number for a field is valid for the given row,
    // column and sub-grid
    public static boolean isFieldValid(int[][] board, int row, int col, int num) {

        // check if row is valid
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        // check if column is valid
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        // check if 3x3 grid is valid
        int subGridTopRow = row - row % 3;
        int subGridLeftCol = col - col % 3;
        for (int i = subGridTopRow; i < subGridTopRow + 3; i++) {
            for (int j = subGridLeftCol; j < subGridLeftCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // function to automatically solve the Sudoku puzzle using backtracking and MRV
    // heuristics
    public static boolean solve(int[][] board) {

        // a list is used to store the coordinates of the empty cells and the amount of
        // valid numbers they can take
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    int count = 0;
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isFieldValid(board, row, col, num)) {
                            count++;
                        }
                    }
                    emptyCells.add(new int[] { row, col, count });
                }
            }
        }
        // sort the empty cells by the number of valid numbers
        emptyCells.sort(Comparator.comparingInt(a -> a[2]));

        // backtrack from the cell with the least number of valid numbers
        for (int[] cell : emptyCells) {
            int row = cell[0];
            int col = cell[1];
            if (board[row][col] == 0) {
                for (int num = 1; num <= GRID_SIZE; num++) {
                    if (isFieldValid(board, row, col, num)) {
                        board[row][col] = num;
                        if (solve(board)) {
                            return true;
                        } else {
                            board[row][col] = 0;
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }
}

/**
 * This class generates a 'random' Sudoku puzzle by using the backtracking
 * algorithm from the Sudoku class.
 * It first creates an empty Sudoku board, solves it using the Sudoku.solve()
 * method and then randomly removes numbers from
 * the solved board while ensuring that the board is still solvable.
 * Note: The structure of the generated puzzle is always the same due to the
 * backtracking algorithm.
 */

class SudokuGenerator {

    static final int GRID_SIZE = 9;

    // method that generates and returns a random 9x9 Sudoku puzzle
    public static int[][] generateSudoku() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        Sudoku.solve(board);
        removeNumbers(board);
        return board;
    }

    // this method removes a certain number of cells from the solved Sudoku and
    // constantly verifies if it is still solvable
    private static void removeNumbers(int[][] board) {
        Random random = new Random();
        int numRemoved = 0;
        int numToBeRemoved = random.nextInt(6) + 47; // removing 47 to 52 numbers to get different complexities
        while (numRemoved < numToBeRemoved) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (board[row][col] != 0) {
                int backup = board[row][col];
                board[row][col] = 0;
                int boardCopy[][] = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    System.arraycopy(board[i], 0, boardCopy[i], 0, GRID_SIZE);
                }
                if (!Sudoku.solve(boardCopy)) {
                    board[row][col] = backup;
                } else {
                    numRemoved++;
                }
            }
        }
    }
}
