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



}
