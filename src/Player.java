/*
    int player will indicate the player status: 0 = computer, 1+ = player (for possible 2 player future)
    Each player will have 5 pieces: length
        Carrier:	5
        Battleship:	4
        Cruiser:   	3
        Submarine:	3
        Destroyer:	2

     Each piece is a Boat object, with individual status'
 */

import java.util.Random;

public class Player {

    private int player;
    private Boat[] boats = {
            new Boat(5, "Carrier"),
            new Boat(4, "Battleship"),
            new Boat(3, "Cruiser"),
            new Boat(3, "Submarine"),
            new Boat(2, "Destroyer")
    };
    private Board boatBoard = new Board();
    private Board guessBoard = new Board();

    public Player(int player){
        this.player = player;
    }

    public String[] getBoatBoard(){
        return boatBoard.getBoard();
    }

    //input has been validated to be a valid array representing a char num coordinate and a direction:
    // {"A10","DOWN"}
    //input has also been validated to fit in board
    public void setBoatBoard(String[] input){
        boatBoard.setBoatOnBoard(input, this.getFirstUnsentBoat());
    }
    public String[] getGuessBoard(){
        return guessBoard.getBoard();
    }

    public Boat getFirstUnsentBoat(){
        for(Boat boat : boats){
            if(!boat.getState()){ return boat;}
        }
        return null;
    }

    public String getFirstUnsetBoatName(){
        for(Boat boat : boats){
            if(!boat.getState()){ return boat.getName();}
        }
            return null;
    }

    public void setRandBoard(){
        for(Boat boat : this.boats){
            while(!boat.getState()) {
                int maxIndex = 12 - boat.getSize() + 1;
                Random rand = new Random();
                int row;
                int col;
                int dir = rand.nextInt((2 - 1) + 1) + 1;
                String direction;
                switch (dir){
                    case 1:
                        direction = "DOWN";
                        row = rand.nextInt((maxIndex - 1) + 1) + 1;
                        col = 4 + ((rand.nextInt((12 - 1) + 1) + 1) * 2);
                        break;
                    case 2:
                        direction = "RIGHT";
                        row = rand.nextInt((12 - 1) + 1) + 1;
                        col = 4 + ((rand.nextInt((maxIndex - 1) + 1) + 1) * 2);
                        break;
                    default:
                        direction = "";
                        row = 0;
                        col = 0;
                        break;
                }
                // check to see that there isn't already a piece in the way
                boolean valid = true;
                switch (direction) {
                    case "DOWN":
                        for (int i = row; i < row + boat.getSize(); i++) {
                            char[] boardValues = boatBoard.getBoard()[i].toCharArray();
                            if (boardValues[col] != '0') {
                                valid = false;
                                break;
                            }
                        }
                        break;
                    case "RIGHT":
                        for (int i = col; i < col + boat.getSize(); i += 2) {
                            char[] boardValues = boatBoard.getBoard()[row].toCharArray();
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
                    char column = this.boatBoard.getBoard()[0].charAt(col);
                    String[] input = {""+column+row,direction };
                    this.boatBoard.setBoatOnBoard(input, boat);
                }
            }
        }
    }



}
