package sudoku.state;


import java.util.ArrayList;
import java.util.Random;

class SudokuGen {
    private Difficulty difficulty;
    private int[][] sudoku = new int[9][9];
    private Random rand = new Random();
    private ArrayList<ArrayList<Integer>> available = new ArrayList<>();


    SudokuGen() { }

    SudokuGen(Difficulty difficulty){
        this.difficulty = difficulty;
    }



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

    public boolean checkConflict(int row,int col,  int number) {
        return isValidForColumn( number,row )
                || isValidForRow(  number,col )
                || !isValidForBox( row, col, number );




    }

    private boolean checkConflictBuild(int currentPos,  int number) {
        int row = currentPos % 9;
        int col = currentPos / 9;

        return  (isValidForColumnBuild( row, col, number )
                || isValidForRowBuild( row, col, number )
                || isValidForBox( row, col, number ));

    }

    private boolean isValidForColumnBuild( int row,  int col,  int number) {
        for (int x = row - 1; x >= 0; x--) {
            if (number == sudoku[x][col]) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidForRowBuild( int row,  int col,  int number) {
        for (int y = col - 1; y >= 0; y--) {
            if (number == sudoku[row][y]) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidForBox( int row, final int col, final int number) {
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

    boolean isValidMove(int row,int col,int number){
        return isValidCell(row,col  ) && isValidValue(  number);
    }

    public boolean isValidCell(int row, int col) {
        return row >-1 && row <9 && col > -1 && col < 9;
    }
    boolean isValidValue(int number){
        return !(number>9 || number<0);
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