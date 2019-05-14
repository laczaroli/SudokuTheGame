package sudoku.state;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.results.GameResult;
import sudoku.results.GameResultDao;
import util.guice.PersistenceModule;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;

public class SudokuState implements Cloneable {
    private static Logger logger = LoggerFactory.getLogger(SudokuState.class);
    public ZonedDateTime start, stop;
    public int[][] tray;
    private SudokuGen sudokuGen = new SudokuGen();

    public static void main(String[] args) {
        Injector injector = Guice.createInjector( new PersistenceModule( "game" ) );
        GameResultDao gameDao = injector.getInstance( GameResultDao.class );

        String name;


        SudokuState state = new SudokuState();
        Scanner in = new Scanner( System.in );
        System.out.println( "Type in your name!" );

        name = in.nextLine();
        logger.info("Inserteted string: {}", name);


        while (true) {
            System.out.println( "What would you want to do?" );
            state.printMenu();
            String menuItem  = in.nextLine();
            try {
                switch (menuItem) {
                    case "play":

                        state.play();

                        gameDao.persist( state.save( name ) );
                        break;
                    case "top":
                        System.out.println( state.getTops( gameDao.findBest( 10 ) ) );

                        break;
                    case "exit":
                        System.exit( 0 );
                    default:
                        logger.error("Invalid option was choosen: {}", menuItem);

                        throw new IllegalArgumentException( "Invalid option" );
                }
            } catch (Exception e) {
                System.out.println( e.getMessage() );
            }
        }


    }

    public Difficulty getDifficulty() {

        return sudokuGen.getDifficulty();
    }

    public void initBoard(Difficulty difficulty) {
        logger.info("Player selected {} difficulty",difficulty);
        sudokuGen.setDifficulty( difficulty );
        tray = sudokuGen.getDesiredGrid();


    }

    public void writeToSudokuGrid(int row, int col, int number) throws IllegalArgumentException {

    logger.info("Player wants to write to row: {}, column: {} the number: {}",row,col,number);

        if (sudokuGen.isValidMove( row, col, number ) && sudokuGen.checkConflict( row, col, number ))
            tray[row][col] = number;
        else {
            throw new IllegalArgumentException( "Invalid cell or number" );
        }


    }

    private boolean isEnd() {
        for (int[] row : tray) {
            for (int square : row) {
                if (square == -1) {
                    logger.trace( "The end!" );
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

    /**
     * Creates a {@code GameResult} instance .
     * @param name the name of the player.
     * @return Returns a {@code GameResult} with the desired name.
     */
    public GameResult save(String name) {
        logger.info( "Saving to database." );
        return GameResult.builder()
                .player( name )
                .difficulty( getDifficulty() )
                .solved( isEnd() )
                .created( ZonedDateTime.now() )
                .duration( Duration.between( start, stop ) )
                .build();
    }

    private String getTops(List<GameResult> best) {
        logger.info("Top 10 players");
        StringBuilder sb = new StringBuilder();
        sb.append( "Top 10 players \n" );
        sb.append( "Name \t Difficulty \t Duration \n" );
        if (best.isEmpty()) {
            sb.append( "Empty :(" );
        } else {
            for (var element : best) {
                //TODO formázás
                sb.append( element.getPlayer() + " \t " + element.getDifficulty() + " \t\t\t " + element.getDuration().getSeconds() + "s\n" );
            }

        }
        return sb.toString();
    }

    private void play() {
        logger.info("START GAME");
        int row = -1, col= -1, number=-1;


        System.out.println( "Select a diffculty!(0-2)" );
        System.out.println( "Easy" );
        System.out.println( "Medium" );
        System.out.println( "Hard" );
        start = ZonedDateTime.now();
        Scanner in = new Scanner( System.in );
        int menuItem = in.nextInt();
        switch (menuItem) {
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
                logger.error( "Invalid difficulty: {}",menuItem );
                throw new IllegalArgumentException( "Invalid difficulty" );


        }
       // initBoard( Difficulty.TEST );


        while (!isEnd()) {
            System.out.println( toString() );
            System.out.println( "Enter the row, column and the number you want to add or if you want to quit(q): " );

            if (in.nextLine().equals( "q" )) {
                logger.error( "Player quits the game." );
                break;
            }


             if (isValidInput( in ))
                 row = in.nextInt();
             if (isValidInput( in ))
                 col = in.nextInt();
             if (isValidInput( in ))
                 number = in.nextInt();

            try {

                writeToSudokuGrid( row - 1, col - 1, number );
            } catch (Exception e) {
                logger.error( "Cannot write to SudokuGrid" );
                System.out.println( e.getMessage() );
            }


        }
        stop = ZonedDateTime.now();


    }

        boolean isValidInput(Scanner in) throws IllegalArgumentException  {
            if (in.hasNextInt()) {
                return true;
                }
            else {
                throw new IllegalArgumentException("Invalid input!");
            }
        }
}
