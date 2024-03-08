package com.example.hideandsink;

public class GameManager {
  private Player activePlayer;
  private Player player1, player2;

 GameManager(Map player1Map, boolean is1Computer, Map player2Map, boolean is2Computer, int playerTurn){
   player1 = new Player(player1Map, is1Computer);
   player2 = new Player(player2Map, is2Computer);

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
