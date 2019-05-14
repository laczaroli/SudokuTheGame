package sudoku.state;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

class SudokuGen {
    private static Logger logger = LoggerFactory.getLogger( SudokuGen.class );

    private Difficulty difficulty;
    private int[][] sudoku = new int[9][9];

    private Random rand = new Random();
    private ArrayList<ArrayList<Integer>> available = new ArrayList<>();


    SudokuGen() {
    }

    SudokuGen(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns  the Sudoku grid with desired difficulty.
     */
    public int[][] getDesiredGrid() {
        generateGrid();
        return removeElements();
    }

    /**
     * Creates a solved Sudoku grid.
     */
    private void generateGrid() {
        logger.info( "GENERATING SOLVED SUDOKU GRID" );

        int currentPos = 0;


        while (currentPos < 81) {
            if (currentPos == 0) {
                clearGrid();
            }

            if (available.get( currentPos ).size() != 0) {
                int i = rand.nextInt( available.get( currentPos ).size() );
                int number = available.get( currentPos ).get( i );

                if (!checkConflictBuild( currentPos, number )) {
                    int xPos = currentPos % 9;
                    int yPos = currentPos / 9;

                    sudoku[xPos][yPos] = number;

                    available.get( currentPos ).remove( i );

                    currentPos++;
                } else {
                    available.get( currentPos ).remove( i );
                }

            } else {
                for (int i = 1; i <= 9; i++) {
                    available.get( currentPos ).add( i );
                }
                currentPos--;
            }
        }


    }

    /**
     * Returns the Sudoku grid, from  which we removed the appropiate number of cells.
     */
    private int[][] removeElements() {
        int i = 0;

        while (i < difficulty.getValue()) {
            int x = rand.nextInt( 9 );
            int y = rand.nextInt( 9 );

            if (sudoku[x][y] != -1) {
                sudoku[x][y] = -1;
                i++;
            }
        }
        return sudoku;

    }

    /**
     * Clears the sudoku grid and fills the available numbers for every cell.
     */
    private void clearGrid() {
        available.clear();

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                sudoku[x][y] = 0;
            }
        }

        for (int x = 0; x < 81; x++) {
            available.add( new ArrayList<>() );
            for (int i = 1; i <= 9; i++) {
                available.get( x ).add( i );
            }
        }
    }

    /**
     * Checks the cell and the number that the player wants to add if they are valid.
     * @param row the row where the player wants to add the {@code number}.
     * @param col the column where the player wants to add the {@code number}.
     * @param number the value that the player wants to add.
     * @return returns {@code true} if the entered values are valid,{@code false} if the entered values are not valid
     */
    public boolean checkConflict(int row, int col, int number) {

        return isValidForColumn( number, row )
                || isValidForRow( number, col )
                || !isValidForBox( row, col, number );


    }

    /**
     *  Checks the 
     */
    private boolean checkConflictBuild(int currentPos, int number) {
        int row = currentPos % 9;
        int col = currentPos / 9;

        return (isValidForColumnBuild( row, col, number )
                || isValidForRowBuild( row, col, number )
                || isValidForBox( row, col, number ));

    }

    /**
     *
     */

    private boolean isValidForColumnBuild(int row, int col, int number) {
        for (int x = row - 1; x >= 0; x--) {
            if (number == sudoku[x][col]) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     */

    private boolean isValidForRowBuild(int row, int col, int number) {
        for (int y = col - 1; y >= 0; y--) {
            if (number == sudoku[row][y]) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     */
    private boolean isValidForBox(int row, final int col, final int number) {
        int xBox = row / 3;
        int yBox = col / 3;

        for (int x = xBox * 3; x < xBox * 3 + 3; x++) {
            for (int y = yBox * 3; y < yBox * 3 + 3; y++) {
                if ((x != row || y != col) && number == sudoku[x][y]) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     */

    boolean isValidForColumn(int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][row] == number)
                return false;


        }
        return true;
    }

    /**
     *
     */

    boolean isValidForRow(int number, int col) {
        for (int i = 0; i < 9; i++) {
            if (sudoku[col][i] == number)
                return false;


        }
        return true;
    }

    /**
     * Checks if the entered cell and value are in range.
     *
     * @param row    the row where the player wants to add the {@code number}.
     * @param col    the column where the player wants to add the {@code number}.
     * @param number the value that the player wants to add.
     * @return returns {@code true} if there is no conflict ,returns {@code false} if there is  conflict.
     */
    boolean isValidMove(int row, int col, int number) {
        return  isValidCell( row, col ) && isValidValue( number );
    }

    /**
     * Checks if the entered cell is in range.
     *
     * @param row the row where the player wants to add the number.
     * @param col the column where the player wants to add the number.
     * @return returns {@code true} if it is  in  range ,returns {@code false} if it is  not in range.
     */

    public boolean isValidCell(int row, int col) {
        return row > -1 && row < 9 && col > -1 && col < 9;
    }

    /**
     * Checks if the entered {@code number} is in range.
     *
     * @param number the value that the player wants to add.
     * @return returns {@code true} if it is  in  range ,returns {@code false} if it is not in range.
     */
    boolean isValidValue(int number) {
        return !(number > 9 || number < 0);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}