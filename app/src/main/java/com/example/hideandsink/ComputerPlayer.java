package com.example.hideandsink;
import java.util.Random;

public class ComputerPlayer extends Player{
  private StategyInterface strategyInterface;
  private Map playerMap = null;
  private int health = 2;
  //private Strategy strategy;
  ComputerPlayer(){
    super();
    placeSubRandomly();
  }
  ComputerPlayer(Map map){
    super();
    super.setPlayerMap(map);
  }

  /**Returns a place for the computer to shoot*/
  String chooseAttack1(Map opponentMap){
    return strategyInterface.chooseAttack1(opponentMap);
  }
}
