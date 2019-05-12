package sudoku.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuGenTest {
    SudokuGen sudokuGen = new SudokuGen( Difficulty.TEST );
    int[][] testArray = sudokuGen.getDesiredGrid();


    int asserEmptyRow(int[][] testSudoku) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudoku[i][j] == -1)
                    return i;
            }
        }
        return -1;
    }
    int asserEmptyCol(int[][] testSudoku) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudoku[i][j] == -1)
                    return j;
            }
        }
    return -1;
    }
    int asserEmptyNumber(int[][] testSudoku) {
    sudokuGen.setSudoku( testSudoku );
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudoku[i][j] == -1){
                    for(int k = 1;k<10;k++){
                        if(sudokuGen.isValidMove( i,j,k ))
                            return k;

                    }
                }

            }
        }
        return -1;
    }


    @Test
    void testGenerateGrid() {
    }

    @Test
    void testRemoveElements() {
    }

    @Test
    void testCheckConflict() {


    }

    @Test
    void testIsValidForColumn() {
        int row = asserEmptyRow( testArray );
        int col = asserEmptyCol( testArray );
        int number = asserEmptyNumber( testArray );
        //assertTrue( sudokuGen.isValidForColumn(number,row  ));
    }

    @Test
    void testIsValidForRow() {
       int row = asserEmptyRow( testArray );
        int col = asserEmptyCol( testArray );
        int number = asserEmptyNumber( testArray );
        assertTrue( sudokuGen.isValidForRow(number,col  ));

    }

    @Test
    void testIsValidValue() {
        for (int i = 1; i < 10; i++) {
            assertTrue( sudokuGen.isValidValue( i ) );
        }

        assertFalse( sudokuGen.isValidValue( 10 ) );
        assertFalse( sudokuGen.isValidValue( -1 ) );

    }


    @Test
    void testIsValidCell() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue( sudokuGen.isValidCell( i, j ) );


            }
        }

        assertFalse( sudokuGen.isValidCell( 10, -1 ) );
    }
}