package state;

import java.util.Scanner;

public class SudokuState implements Cloneable {
    private int[][] tray;
    private SudokuGen sudokuGen = new SudokuGen();
    private SudokuChecker sudokuChecker = new SudokuChecker();

    private void initBoard(Difficulty difficulty) {
        sudokuGen.setDifficulty( difficulty );
        tray = sudokuGen.getDesiredGrid();


    }

    public void writeToSudokuGrid(Integer row, Integer col, Integer number) {
        sudokuChecker.setGrid( tray );


        if (sudokuChecker.isValidMove( number, row, col )) {
            tray[row][col] = number;
        } else {
            System.out.println( "Invalid move" );
        }

    }


    private boolean isEnd() {
        for (int[] row : tray) {
            for (int square : row) {
                if (square == -1) {
                    return false;
                }
            }
        }
        return true;

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "  - - - - - - - - - - - - -\n" );
        for (int i = 0; i < tray.length; i++) {
            for (int j = 0; j < tray[0].length; j++) {

                sb.append( j == 0 ? i + 1 : "" ).append( j == 0 ? " | " : " " ).append( tray[i][j] == -1 ? "." : (tray[i][j]) ).append( (j + 1) % 3 == 0 ? " |" : "" );
            }
            sb.append( "\n" ).append( (i + 1) % 3 == 0 ? "  - - - - - - - - - - - - -\n" : "" );
        }
        sb.append( "\t" );
        for (int i = 1; i < 10; i++) {
            sb.append( i ).append( i % 3 == 0 ? "\t" : " " );
        }
        sb.append( "\n" );

        return sb.toString();
    }

    private void printMenu() {
        System.out.println( "Play: play" );
        System.out.println( "Tops: top" );
        System.out.println( "Exit: exit" );
    }

    public static void main(String[] args) {

        SudokuState state = new SudokuState();
        Scanner in = new Scanner( System.in );


        while (true) {
            System.out.println( "What would you want to do?" );
            state.printMenu();
            try {
                switch (in.nextLine()) {
                    case "play":
                        state.play();
                        break;
                    case "top":
                        // state.printTops();
                        break;
                    case "exit":
                        System.exit( 0 );
                    default:
                        throw new IllegalArgumentException( "Invalid option" );
                }
            } catch (Exception e) {
                System.out.println( e.getMessage() );
            }
        }


    }

    private void play() {
        Integer row, col, number;


        System.out.println( "Select a diffculty!(0-2)" );
        System.out.println( "Easy" );
        System.out.println( "Medium" );
        System.out.println( "Hard" );
        Scanner in = new Scanner( System.in );
        switch (in.nextInt()) {
            case 0:
                initBoard( Difficulty.EASY );
                break;
            case 1:
                initBoard( Difficulty.MEDIUM );
                break;
            case 2:
                initBoard( Difficulty.HARD );
                break;
            default:
                throw new IllegalArgumentException( "Invalid difficulty" );


        }


        while (!isEnd()) {
            System.out.println( toString() );
            System.out.println( "Enter the row, column and the number you want to add: " );
            row = in.nextInt();
            col = in.nextInt();
            number = in.nextInt();
            try {
                writeToSudokuGrid( row - 1, col - 1, number );
            } catch (Exception e) {
                System.out.println( e.getMessage() );
            }


        }
    }


}
