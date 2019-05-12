package sudoku.state;

import org.junit.jupiter.api.Test;
import sudoku.state.Difficulty;
import sudoku.state.SudokuState;

import static org.junit.jupiter.api.Assertions.*;
public class SudokuStateTest {
        private void assertEmptySpaces(int expectedEmptyCells,int[][] grid ){
            int counter=0;
            for(int[]row :grid){
                for (int cell:row ){
                    if (cell==-1)
                        counter++;
                }
            }
            assertEquals(expectedEmptyCells,counter);


        }

    @Test
    void testInitBoard() {
        SudokuState test = new SudokuState();
        test.initBoard( Difficulty.TEST );
        assertEmptySpaces( Difficulty.TEST.getValue(),test.tray );
        test.initBoard( Difficulty.EASY );
        assertEmptySpaces( Difficulty.EASY.getValue(),test.tray );
        test.initBoard( Difficulty.MEDIUM );
        assertEmptySpaces( Difficulty.MEDIUM.getValue(),test.tray );
        test.initBoard( Difficulty.HARD );
        assertEmptySpaces( Difficulty.HARD.getValue(),test.tray );
    }
    @Test
    void testWriteToSudokuGrid(){
        SudokuState state = new SudokuState();

        state.initBoard( Difficulty.TEST2 );
        assertThrows( IllegalArgumentException.class,()->state.writeToSudokuGrid( 10,1,10 ) );
    }
}
