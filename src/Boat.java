public class Boat {
    private int size;
    private String name;
    private boolean direction; // true = vertical, false = horizontal
    private boolean state = false; // false = not yet set, true = set on screen

    public Boat(int size, String name){
        this.size = size;
        this.name = name;
    }

    public void setName(String name){ this.name = name; }

    public String getName(){ return this.name; }

    public void setDirection(boolean direction){
        this.direction = direction;
    }

    public boolean getDirection(){ return this.direction;}

    public void flipDirection(){
        this.direction = !this.direction;
    }

    public boolean getState(){ return state;}

    public void setBoat(){
        this.state = !this.state;
    }

    public int getSize(){ return this.size;}


}
