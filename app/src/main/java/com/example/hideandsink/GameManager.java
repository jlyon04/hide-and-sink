package com.example.hideandsink;

import static android.os.SystemClock.sleep;

import java.nio.file.WatchKey;
import java.util.ArrayList;

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


  public boolean computerAttack(){
    ArrayList<String> atkArray = opponent.chooseAttack1(opponent.getMap(), opponent.health);
    String offenseType=atkArray.get(0);
    atkArray.remove(0);
    switch (offenseType) {
      case "move":
        opponent.moveSub(atkArray);
        //update debug list
        setDebugLoc(atkArray);
        return false;
      case ("sonar"):
        placeSonar(atkArray, "opponent");
        break;
      case "scope":
        placeScope(atkArray, "opponent");
        break;
      case "fire":
        placeFire(atkArray, "opponent");
        break;
      default:
        //error
        break;
    }

    sleep(850);
    ArrayList<String> atkArray2 = opponent.chooseAttack2(opponent.getMap(), offenseType);
    String offenseType2=atkArray2.get(0);
    atkArray2.remove(0);
    switch (offenseType2) {
      case ("sonar"):
        placeSonar(atkArray2, "opponent");
        break;
      case "scope":
        placeScope(atkArray2, "opponent");
        break;
      case "fire":
        placeFire(atkArray2, "opponent");
        break;
      default:
        //error
        break;
    }
    return false;
  }


  //locArray should be in format ["1,2"],["3,4"]
  public void placeSonar(ArrayList<String> locArray, String attacker){
    for(int i =0; i< locArray.size();i++){
      String[] res = locArray.get(i).split(",");
      int x = Integer.valueOf(res[0]);
      int y = Integer.valueOf(res[1]);

      if(attacker.equals("player")){
        if (opponent.getMap().cellAt(x, y).getIsSub()) {
          paintSonarHit(locArray, player1.getMap());
          break;
        } else {
          //This should only hit if it is the last iteration and no sub has been found
          if (i == locArray.size() - 1) {
            //No Sub Hit Color all: Miss
            paintSonarMiss(locArray, player1.getMap());
          }
        }
      }
      // Opponents Turn
      else{
        if (player1.getMap().cellAt(x, y).getIsSub()) {
          paintSonarHit(locArray, opponent.getMap());
          break;
        } else {
          //This should only hit if it is the last iteration and no sub has been found
          if (i == locArray.size() - 1) {
            //No Sub Hit Color all: Miss
            paintSonarMiss(locArray, opponent.getMap());
          }
        }
        }
      }
    }
  public void placeScope(ArrayList<String> locArray, String attacker){
      for(int i =0; i< locArray.size();i++){
        String[] res = locArray.get(i).split(",");
        int x = Integer.valueOf(res[0]);
        int y = Integer.valueOf(res[1]);
        // Player
        if(attacker.equals("player")){
          if(opponent.getMap().cellAt(x,y).getIsSub()){
            player1.getMap().cellAt(x,y).setScopeHit();
          }
          else{
            player1.getMap().cellAt(x,y).setScopeMiss();
          }
        }
        // Opponent
        else{
          if(player1.getMap().cellAt(x,y).getIsSub()){
            opponent.getMap().cellAt(x,y).setScopeHit();
          }
          else{
            opponent.getMap().cellAt(x,y).setScopeMiss();
          }
        }
      }
    }

  public void placeFire(ArrayList<String> locArray, String attacker){
    String[] res = locArray.get(0).split(",");
    int x = Integer.valueOf(res[0]);
    int y = Integer.valueOf(res[1]);
    if(attacker.equals("player")){
      if(opponent.getMap().cellAt(x,y).getIsSub()){
        player1.getMap().cellAt(x,y).setFireHit();
        opponent.health -= 1;
      }
      else{
        player1.getMap().cellAt(x,y).setFireMiss();
      }
    }
    else {
      if(player1.getMap().cellAt(x,y).getIsSub()){
        opponent.getMap().cellAt(x,y).setFireHit();
        player1.health -= 1;
      }
      else{
        opponent.getMap().cellAt(x,y).setFireMiss();
      }
    }
  }

  private void setDebugLoc(ArrayList<String> locArray){
    if (opponent.subLocation.size()>0)
      opponent.subLocation.clear();
    for(int i=0;i<locArray.size();i++){
      String[] res = locArray.get(i).split(",");
      opponent.subLocation.add(new int[]{Integer.valueOf(res[0]),Integer.valueOf(res[1])});
    }
  }

  private void paintSonarHit(ArrayList<String> locArray, Map map) {
      for (int j = 0; j < locArray.size(); j++) {
        String[] res = locArray.get(j).split(",");
        int x = Integer.valueOf(res[0]);
        int y = Integer.valueOf(res[1]);
        map.cellAt(x, y).setSonarHit();
      }
    }
  private void paintSonarMiss(ArrayList<String> locArray, Map map) {
      for (int j = 0; j < locArray.size(); j++) {
        String[] res = locArray.get(j).split(",");
        int x = Integer.valueOf(res[0]);
        int y = Integer.valueOf(res[1]);
        map.cellAt(x, y).setSonarMiss();
      }
    }

  }
