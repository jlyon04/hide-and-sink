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

  public void placeSubRandomly(){
    //return a random xy in the correct range
    Random rng = new Random();
    // if true then ship is horizontal
    boolean orientation = rng.nextBoolean();

    int temp1 = rng.nextInt();
    int temp2 = rng.nextInt();

    //get board size
    //calc max values based on orientation
    playerMap.cellAt(temp1, temp2).isSub = true;
    playerMap.cellAt(temp1, temp2).isSub = true;

  }

}
