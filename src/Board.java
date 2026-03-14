/*
    The board class will hold all the information in regard to playing fields status'.
    The intent is to have 4 boards: the players board, the players guess board, the computers board
    & the computers guess board.
    Only the players boards will need to be displayed in the view boards, but designed as such in case of future
    two player systems can display multiple boards with minimal adjustments to design

    board design in design_doc
 */
public class Board {

    private String[] boardState =
            {"    A B C D E F G H I J K L   ",
             " 1  0 0 0 0 0 0 0 0 0 0 0 0 1 ",
             " 2  0 0 0 0 0 0 0 0 0 0 0 0 2 ",
             " 3  0 0 0 0 0 0 0 0 0 0 0 0 3 ",
             " 4  0 0 0 0 0 0 0 0 0 0 0 0 4 ",
             " 5  0 0 0 0 0 0 0 0 0 0 0 0 5 ",
             " 6  0 0 0 0 0 0 0 0 0 0 0 0 6 ",
             " 7  0 0 0 0 0 0 0 0 0 0 0 0 7 ",
             " 8  0 0 0 0 0 0 0 0 0 0 0 0 8 ",
             " 9  0 0 0 0 0 0 0 0 0 0 0 0 9 ",
             " 10 0 0 0 0 0 0 0 0 0 0 0 0 10",
             " 11 0 0 0 0 0 0 0 0 0 0 0 0 11",
             " 12 0 0 0 0 0 0 0 0 0 0 0 0 12",
             "    A B C D E F G H I J K L   "};
    // index: 0123456789012345678901234567890

    public String[] getBoard(){
        return this.boardState;
    }

    // the input strings have been validated to be within range before this function is called
    public void setBoatOnBoard(String[] input, Boat boat){
        char column = input[0].charAt(0);
        int colIndex = getColIndex(column);
        int row = Integer.parseInt(input[0].substring(1));
        String direction = input[1];
        int length = boat.getSize();


        switch (direction){
            case "UP":
                for(int i = row;i > row-length; i--){
                    char[] mod = boardState[i].toCharArray();
                    mod[colIndex] = '|';
                    boardState[i] = new String(mod);
                }
                boat.setBoat();
                break;
            case "DOWN":
                for(int i = row;i < row+length; i++){
                    char[] mod = boardState[i].toCharArray();
                    mod[colIndex] = '|';
                    boardState[i] = new String(mod);
                }
                boat.setBoat();
                break;
            case "LEFT":
                int limitl = getColIndex(column) - (length*2);
                for(int i = colIndex; i > limitl; i-= 2){
                    char[] mod = boardState[row].toCharArray();
                    mod[i] = '-';
                    boardState[row] = new String(mod);
                }
                boat.setBoat();
                break;
            case "RIGHT":
                int limitr = getColIndex(column) + (length*2);
                for(int i = colIndex; i < limitr; i+= 2){
                    char[] mod = boardState[row].toCharArray();
                    mod[i] = '-';
                    boardState[row] = new String(mod);
                }
                boat.setBoat();
                break;
        }
    }

    public static int getColIndex(char in){
        return switch (in) {
            case 'A' -> 4;
            case 'B' -> 6;
            case 'C' -> 8;
            case 'D' -> 10;
            case 'E' -> 12;
            case 'F' -> 14;
            case 'G' -> 16;
            case 'H' -> 18;
            case 'I' -> 20;
            case 'J' -> 22;
            case 'K' -> 24;
            case 'L' -> 26;
            default -> 0;
        };
    }

}
