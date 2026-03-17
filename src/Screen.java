/*
        This will be our "view" module of an MVC pattern, where we will call all methods required
        for displaying game states and input screens
 */

import java.util.Scanner;
import java.util.Random;

public class Screen {

        public enum gameState {
                OPENING, SETTING, PLAYING, GAMEOVER, WIN, QUITING

        }
        private gameState screenState;
        private Scanner scan = new Scanner(System.in);

        public Screen(){
                screenState = gameState.OPENING;
        }

        public gameState getCurrentGameState(){
                return screenState;
        }

        public void setCurrentGameState(gameState input){
            screenState = input;
        }

        // update the input screen
        public String update(Player player, Player computer){
                switch (screenState) {
                    case gameState.OPENING:
                        String playerResponse = startScreen();
                        while(true) {
                            if (playerResponse.equals("Y")) {
                                setCurrentGameState(gameState.SETTING);
                                break;
                            } else if (playerResponse.equals("N")) {
                                setCurrentGameState(gameState.QUITING);
                                break;
                            } else {
                                System.out.println("Invalid input, please use Y or N to indicate if you'd like to play a new game:");
                                playerResponse = scan.nextLine();
                            }
                        }

                    case SETTING:
                        settingScreen(player);
                        screenState = gameState.PLAYING;
                        break;

                    case PLAYING:
                        while(player.confirmBoardStates()){
                            guessingScreen(player, computer);
                            compRandomGuess(player);
                        }
                        // while loop only breaks if win condition is met
                        // check if win or loose then present win / loose screen and move
                        // back to new game screen
                        if(player.checkWin()){
                            screenState = gameState.WIN;
                            break;
                        } else {
                            screenState = gameState.GAMEOVER;
                            break;
                        }

                    case WIN:
                        winScreen();
                        screenState = gameState.QUITING;
                        break;

                    case GAMEOVER:
                        loseScreen();
                        screenState = gameState.QUITING;
                        break;

                    case QUITING:
                        quitScreen();
                        break;
                }
                return null;
        }

        // input screen for starting a new game:
        public String startScreen(){
                 String[] openingScreen =
                                {"    A B C D E F G H I J K L   ",
                                 " 1  0 0 0 0 0 0 0 0 0 0 0 0 1 ",
                                 " 2  0 0 0 0 0 0 0 0 0 0 0 0 2 ",
                                 " 3  0 0 0               0 0 3 ",
                                 " 4  0 0 0   Battleship  0 0 4 ",
                                 " 5  0 0 0               0 0 5 ",
                                 " 6  0 0 0               0 0 6 ",
                                 " 7  0 0 0   New Game?   0 0 7 ",
                                 " 8  0 0 0               0 0 8 ",
                                 " 9  0 0 0     Input     0 0 9 ",
                                 " 10 0 0 0     (Y/N)     0 0 10",
                                 " 11 0 0 0               0 0 11",
                                 " 12 0 0 0 0 0 0 0 0 0 0 0 0 12",
                                 "    A B C D E F G H I J K L   "};
                 for(String line : openingScreen){
                     System.out.println(line);
                 }
                 String input = scan.nextLine();
                 return input.toUpperCase();
        }

        public void settingScreen(Player player){
            while(player.getFirstUnsetBoatName() != null) {
                Boat boat = player.getFirstUnsentBoat();
                String boatName = boat.getName();
                String[] boatBoard = player.getBoatBoard();
                String[] guessBoard = player.getGuessBoard();
                int j = boatBoard.length;
                for (int i = 0; i < j; i++) {
                    System.out.println(boatBoard[i] + guessBoard[i]);
                }
                System.out.println("         Player Board                  Guessing Board       ");
                System.out.println();
                System.out.println("Where would you like to place your "+boatName+"?");
                System.out.println("Input coordinate:direction (eg: A5:up or G3:down or A7:left):");
                String in = scan.nextLine().toUpperCase();
                while(!in.contains(":")){
                    System.out.println("Invalid coordinate input, please input another coordinate using the A0:dir format:");
                    in = scan.nextLine().toUpperCase();
                }
                String[] input = in.split(":");
                input = checkValidInput(input, boat, player.getBoatBoard());
                player.setBoatBoard(input);
            }
        }

        public String[] checkValidInput(String[] input, Boat boat, String[] boatBoard){
            char column = input[0].charAt(0);
            int row = Integer.parseInt(input[0].substring(1));
            String direction = input[1];
            int length = boat.getSize();
            boolean valid = false;



            // nested ifs to validate:
            // is column within range?
            // is row within range?
            // is direction valid?
            // if above true, would the piece fit on the board?
            if(column >= 'A' && column <= 'L'){
                if(row >= 1 && row <= 12){
                    if( direction.equals("UP") ||
                        direction.equals("DOWN") ||
                        direction.equals("LEFT") ||
                        direction.equals("RIGHT")){
                        switch (direction){
                            case "UP":
                                if(row >= length){ valid = true; }
                                break;
                            case "DOWN":
                                if(row <= 12-length+1){ valid = true; }
                                break;
                            case "LEFT":
                                if(column >= 'A'+length-1){ valid = true; }
                                break;
                            case "RIGHT":
                                if(column <= 'L'-length+1){valid = true; }
                                break;
                            default:
                                break;
                        }

                    }
                }
            }
            // now we know the piece would fit on the board, we will validate if
            // the board has a piece already that would conflict with this piece input.
            switch (direction) {
                case "UP":
                    for (int i = row; i > row - length; i--) {
                        char[] boardValues = boatBoard[i].toCharArray();
                        if (boardValues[Board.getColIndex(column)] != '0') {
                            valid = false;
                            break;
                        }
                    }
                    break;
                case "DOWN":
                    for (int i = row; i < row + length; i++) {
                        char[] boardValues = boatBoard[i].toCharArray();
                        if (boardValues[Board.getColIndex(column)] != '0') {
                            valid = false;
                            break;
                        }
                    }
                    break;
                case "LEFT":
                    int limitl = Board.getColIndex(column) - (length * 2);
                    for (int i = Board.getColIndex(column); i > limitl; i -= 2) {
                        char[] boardValues = boatBoard[row].toCharArray();
                        if (boardValues[i] != '0') {
                            valid = false;
                            break;
                        }
                    }
                    break;
                case "RIGHT":
                    int limitr = Board.getColIndex(column) + (length * 2);
                    for (int i = Board.getColIndex(column); i < limitr; i += 2) {
                        char[] boardValues = boatBoard[row].toCharArray();
                        if (boardValues[i] != '0') {
                            valid = false;
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
            if(valid){
                return input;
            } else {
                System.out.println("Invalid coordinate input, please input another coordinate that lands on the board and doesn't conflict with other inputs:");
                String in = scan.nextLine().toUpperCase();
                while(!in.contains(":")){
                    System.out.println("Invalid coordinate input, please input another coordinate using the A0:dir format:");
                    in = scan.nextLine().toUpperCase();
                }
                String[] getter = in.split(":");
                return checkValidInput(getter, boat, boatBoard);
            }
        }


        public void guessingScreen(Player player, Player computer){
            String[] boatBoard = player.getBoatBoard();
            String[] guessBoard = player.getGuessBoard();
            int j = boatBoard.length;
            for (int i = 0; i < j; i++) {
                System.out.println(boatBoard[i] + guessBoard[i]);
            }
            System.out.println("         Player Board                  Guessing Board       ");
            System.out.println();
            System.out.println("Where would you like to attack?");
            System.out.println("Input coordinate: (eg: A5 or G3 or A7):");

            String in = scan.nextLine().toUpperCase();
            while (!validateGuessInput(in, guessBoard)){
                System.out.println("Invalid input, please guess a valid coordinate:");
                in = scan.nextLine().toUpperCase();
            }

            // check if hit and update board state
            if(computer.getBoatBoardClass().checkHit(in)){
                System.out.println("!!!! HIT !!!!");
                System.out.println("~~~~~~~~~~~~~");
                player.getGuessBoardClass().updateGuess(in, true);
            } else{
                System.out.println("===  MISS  ===");
                System.out.println("~~~~~~~~~~~~~~");
                player.getGuessBoardClass().updateGuess(in, false);
            }
        }

        private void compRandomGuess(Player player){
            Random rand = new Random();
            int row = rand.nextInt((12 - 1) + 1) + 1;
            int col = 4 + (rand.nextInt(12) * 2);
            String in = "" + Board.getColchar(col) + row;
            System.out.println("Computer guessed: "+in);
            if(player.getBoatBoardClass().checkHit(in)){
                System.out.println("!!!! HIT !!!!");
                System.out.println("~~~~~~~~~~~~~");
            } else{
                System.out.println("===  MISS  ===");
                System.out.println("~~~~~~~~~~~~~~");
            }

        }


        // return true if valid input
        private boolean validateGuessInput(String str, String[] guessBoard){
            char col = str.charAt(0);
            int row = 0;
            try {
                row = Integer.parseInt(str.substring(1).trim());
            } catch (NumberFormatException e) {
                return false;
            }

            if (str == null || str.isEmpty()) { // Check for null or empty string
                return false;
            }

            if(col >= 'A' && col <= 'L'){
                if (row >= 1 && row <= 12){
                    return true;
                }
            }
            return false;
        }

        private void winScreen(){
            String[] winScreen =
                    {       "    A B C D E F G H I J K L   ",
                            " 1                          1 ",
                            " 2                          2 ",
                            " 3             WOW          3 ",
                            " 4                          4 ",
                            " 5           YOU WON!       5 ",
                            " 6                          6 ",
                            " 7      CONGRATULATIONS     7 ",
                            " 8                          8 ",
                            " 9     ~~~~~~~~~~~~~~~~~    9 ",
                            " 10                         10",
                            " 11                         11",
                            " 12                         12",
                            "    A B C D E F G H I J K L   "};
            for(String line : winScreen){
                System.out.println(line);
            }
        }

    private void loseScreen(){
        String[] loseScreen =
                {       "    A B C D E F G H I J K L   ",
                        " 1                          1 ",
                        " 2                          2 ",
                        " 3             AWE          3 ",
                        " 4                          4 ",
                        " 5           YOU LOST!       5 ",
                        " 6                          6 ",
                        " 7             HOW?         7 ",
                        " 8                          8 ",
                        " 9     ~~~~~~~~~~~~~~~~~    9 ",
                        " 10                         10",
                        " 11                         11",
                        " 12                         12",
                        "    A B C D E F G H I J K L   "};
        for(String line : loseScreen){
            System.out.println(line);
        }
    }

        public void quitScreen(){
            String[] closingScreen =
                    {       "    A B C D E F G H I J K L   ",
                            " 1                          1 ",
                            " 2                          2 ",
                            " 3         THANK YOU        3 ",
                            " 4                          4 ",
                            " 5        FOR PLAYING!      5 ",
                            " 6                          6 ",
                            " 7           PLEASE         7 ",
                            " 8                          8 ",
                            " 9         PLAY AGAIN       9 ",
                            " 10                         10",
                            " 11           SOON!         11",
                            " 12                         12",
                            "    A B C D E F G H I J K L   "};
            for(String line : closingScreen){
                System.out.println(line);
            }
            System.exit(0);

        }
}
