package sudoku.state;


import java.util.ArrayList;
import java.util.Random;

class SudokuGen {
    SudokuGen() {
    }



    public Difficulty getDifficulty() {
        return difficulty;
    }

    private Difficulty difficulty;


    private int[][] sudoku = new int[9][9];

    private Random rand = new Random();

    private ArrayList<ArrayList<Integer>> available = new ArrayList<>();

    int[][] getDesiredGrid() {
        generateGrid();
        return removeElements( sudoku );
    }


    void generateGrid() {


        int currentPos = 0;


        while (currentPos < 81) {
            if (currentPos == 0) {
                clearGrid( sudoku );
            }

            if (available.get( currentPos ).size() != 0) {
                int i = rand.nextInt( available.get( currentPos ).size() );
                int number = available.get( currentPos ).get( i );

                if (!checkConflict( currentPos, number )) {
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

    public int[][] removeElements(int[][] Sudoku) {
        int i = 0;

        while (i < difficulty.getValue()) {
            int x = rand.nextInt( 9 );
            int y = rand.nextInt( 9 );

            if (Sudoku[x][y] != -1) {
                Sudoku[x][y] = -1;
                i++;
            }
        }
        return Sudoku;

    }

    private void clearGrid(int[][] sudoku) {
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
    public boolean checkConflict(int xPos,int yPos, final int number) {
        if (isValidForColumn( number,xPos )
                || isValidForRow(  number,yPos )
                || !isValidForBox( xPos, yPos, number )) {
            return true;
        }

        return false;
    }
    private boolean checkConflict(int currentPos, final int number) {
        int xPos = currentPos % 9;
        int yPos = currentPos / 9;

        if (checkHorizontalConflict( xPos, yPos, number )
                || checkVerticalConflict( xPos, yPos, number )
                || isValidForBox( xPos, yPos, number )) {
            return true;
        }

        return false;
    }

    private boolean checkHorizontalConflict(final int xPos, final int yPos, final int number) {
        for (int x = xPos - 1; x >= 0; x--) {
            if (number == sudoku[x][yPos]) {
                return true;
            }
        }

        return false;
    }

    private boolean checkVerticalConflict(final int xPos, final int yPos, final int number) {
        for (int y = yPos - 1; y >= 0; y--) {
            if (number == sudoku[xPos][y]) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidForBox( int xPos, final int yPos, final int number) {
        int xRegion = xPos / 3;
        int yRegion = yPos / 3;

        for (int x = xRegion * 3; x < xRegion * 3 + 3; x++) {
            for (int y = yRegion * 3; y < yRegion * 3 + 3; y++) {
                if ((x != xPos || y != yPos) && number == sudoku[x][y]) {
                    return true;
                }
            }
        }

        return false;
    }
    boolean isValidForColumn(int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][row] ==  number )
                return false;


        }
        return true;
    }
    boolean isValidForRow(int number, int col) {
        for (int i = 0; i < 9; i++) {
            if (sudoku[i][col] ==  number )
                return false;


        }
        return true;
    }
        boolean isValidValue(int number){
        return !(number>9 || number<0);
        }


    void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    public boolean isValidCell(int row, int col) {
        return row >-1 && row <9 && col > -1 && col < 9;
    }
}