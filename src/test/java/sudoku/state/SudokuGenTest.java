package sudoku.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuGenTest {
    SudokuGen sudokuGen = new SudokuGen( );
    int[][] testArray = {
            {3, 7, 8, 2, 5, 1, 4, 6, 9},
            {9, 2, 4, 7, 6, 8, 3, 5, 1},
            {6 ,1 ,5, 4, 3, 9, 2, 7, 8},
            {2 ,3 ,6, 9 ,4, 7, 1, 8, 5},
            {1 ,5 ,7 ,8 ,2 ,6 ,9 ,4 ,3},
            {4 ,8 ,9, 3, 1 ,5, 7, 2, 6},
            {5 ,6 ,3 ,1 ,7 ,2 ,8 ,9 ,4},
            {7 ,9 ,1, 6, 8, 4 ,5 ,3 ,2},
            {8 ,4 ,2 ,5, 9, -1 ,6 ,1 ,7}
            };
    int testRow =8;
    int testCol = 5;
    int testValue = 3;





    @Test
    void testCheckConflict() {
        sudokuGen.setSudoku( testArray );

        assertTrue( sudokuGen.checkConflict( testRow,testCol,testValue ) );
        assertFalse( sudokuGen.checkConflict( testRow,testCol,testValue-1 ) );


    }

    @Test
    void testIsValidForColumn() {
        sudokuGen.setSudoku( testArray );

        assertTrue( sudokuGen.isValidForColumn(testValue,testCol  ));
        assertFalse(  sudokuGen.isValidForColumn(testValue-1,testCol  ));
    }

    @Test
    void testIsValidForRow() {
        sudokuGen.setSudoku( testArray );

        assertTrue( sudokuGen.isValidForRow(testValue,testRow  ));
        assertFalse(  sudokuGen.isValidForRow(testValue-1,testRow  ));

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