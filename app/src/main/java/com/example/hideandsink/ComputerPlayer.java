package com.example.hideandsink;
import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player{
  private StategyInterface strategyInterface;
  private Map playerMap = null;
  ComputerPlayer(){
    super();
    placeSubRandomly();
    strategyInterface= new StrategyRandom();
  }
  ComputerPlayer(Map map){
    super();
    super.setPlayerMap(map);
    new StrategyRandom();
  }

  /**Returns a place for the computer to shoot*/
  ArrayList<String> chooseAttack1(Map opponentMap){
    return strategyInterface.chooseAttack1(opponentMap);
  }
  ArrayList<String> chooseAttack2(Map opponentMap, String lastAtk){
    return strategyInterface.chooseAttack2(opponentMap,lastAtk);
  }
}
