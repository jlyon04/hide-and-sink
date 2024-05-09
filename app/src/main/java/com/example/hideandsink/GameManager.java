package com.example.hideandsink;

public class GameManager {
  private Player activePlayer;
  private Player player1;
  private ComputerPlayer opponent;


  //New Game
  GameManager(){
    player1 = new Player();
    opponent = new ComputerPlayer();
    //todo randomize this??
    activePlayer=player1;
  }

  // New Game with Computer
 GameManager(Map player1Map, Map opponentMap, int playerTurn){
   player1 = new Player(player1Map);
   opponent = new ComputerPlayer(opponentMap);

   // Set Who's turn
   switch (playerTurn){
     case 0:
       activePlayer=player1;
       break;
     case 1:
       activePlayer=opponent;
       break;
   }
 }

 public Player getPlayer(){
    return player1;
 }
  public Player getOpponentPlayer(){
    return opponent;
  }
  public Player getActivePlayer(){
    return activePlayer;
  }
  public void changeTurn(){
    if(activePlayer==player1){
      activePlayer = opponent;
    }
    else{
      activePlayer=player1;
    }
  }

  String computerAttack(){
    computerAttack1();
    return "true";
  }
  String computerAttack1(){
    String moveStr = opponent.chooseAttack1(opponent.getMap());
    return moveStr;
  }

}
