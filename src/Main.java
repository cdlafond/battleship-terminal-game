/*
    This will act as the controller of our MVC module system, controlling the flow of the game, calling
    methods to update the game model, and calling Screen to display the game at given states
 */

void main() {

    /*
        Start the game by initializing the players and opening / new game screen
     */
    Player player1 = new Player(1);
    Player computer = new Player(0);
    Screen game = new Screen();
    game.update(player1);
    // the only two options at this point are to go into setting pieces mode, or quite
    // so to get here they haven't quite at this point & we cont to place the pieces



}
