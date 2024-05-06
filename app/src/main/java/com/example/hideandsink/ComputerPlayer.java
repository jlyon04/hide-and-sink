package com.example.hideandsink;
import java.util.Random;

public class ComputerPlayer extends Player{
  private Map playerMap = null;
  private int health = 0;
  //private Strategy strategy;
  ComputerPlayer(){
    super();
    placeSubRandomly();
  }
  ComputerPlayer(Map map){
    super();
    super.setPlayerMap(map);
  }


}
