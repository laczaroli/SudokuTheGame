package state;

public class SudokuChecker {
    public SudokuChecker() {

    }


    int[][] grid;

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    boolean isValidMove(Integer number, Integer row, Integer col){
        return  isValidForRow(number,row) && isValidForColumn(number,col) && isValidForBox( number,row,col );
    }

    boolean isValidForRow(Integer number, Integer row) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] ==  number )

                return false;


        }
        return true;
    }

    private boolean isValidForBox(final int number,final int row, final int col) {
        int xRegion = row / 3;
        int yRegion = col / 3;

        for (int x = xRegion * 3; x < xRegion * 3 + 3; x++) {
            for (int y = yRegion * 3; y < yRegion * 3 + 3; y++) {
                if ((x != row || y != col) && number == grid[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    boolean isValidForColumn(Integer number, Integer col) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] ==  number )
                return false;


        }
        return true;
    }
}
