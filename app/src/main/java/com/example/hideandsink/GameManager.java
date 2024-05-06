package com.example.hideandsink;

public class GameManager {
  private Player activePlayer;
  private Player player1, player2;


  //New Game
  GameManager(){
    player1 = new Player();
    player2 = new ComputerPlayer();
    //randomize this??
    activePlayer=player1;
  }

  // New Game with Computer
 GameManager(Map player1Map, Map player2Map, int playerTurn){
   player1 = new Player(player1Map);
   player2 = new ComputerPlayer(player2Map);

   // Set Who's turn
   switch (playerTurn){
     case 0:
       activePlayer=player1;
       break;
     case 1:
       activePlayer=player2;
       break;
   }
 }


}
