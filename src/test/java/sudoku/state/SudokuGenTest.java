package sudoku.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGenTest {
    SudokuGen sudokuGen = new SudokuGen();




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
    }

    @Test
    void testIsValidForRow() {

    }

    @Test
    void testIsValidValue() {
        for(int i = 1; i <10 ; i++){
       assertTrue(   sudokuGen.isValidValue(  i));
        }

        assertFalse(sudokuGen.isValidValue( 10 ) );
        assertFalse(sudokuGen.isValidValue( -1 ) );

    }



    @Test
    void testIsValidCell() {

        for(int i = 0; i <9 ; i++){
            for (int j = 0; j<9;j++){
                assertTrue(sudokuGen.isValidCell( i,j ) );


            }
        }

        assertFalse(sudokuGen.isValidCell( 10,-1 ) );
    }
}