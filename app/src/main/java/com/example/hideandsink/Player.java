package com.example.hideandsink;

import java.util.ArrayList;
import java.util.Random;

public class Player{
  private Map playerMap = null;
  private int health = 0;

  //todo remove this or private??
  public ArrayList<int[]> subLocation = new ArrayList<>();

  Player(){
    playerMap = new Map();
    health=2;
  }

  Player(Map map){
    playerMap=map;
    health=2;
  }

  public void setPlayerMap(Map map){playerMap=map;}

  public Map getMap(){
    return playerMap;
  }

  public void placeSubRandomly(){
    //return a random xy in the correct range
    Random rng = new Random();
    // if true then ship is horizontal
    boolean orientation = rng.nextBoolean();

    if (orientation){
      // Horizontal
      int temp1 = rng.nextInt(6);
      int temp2 = rng.nextInt(9);
      playerMap.cellAt(temp1,temp2).setSub();
      playerMap.cellAt(temp1+1,temp2).setSub();
      playerMap.cellAt(temp1+2,temp2).setSub();
      //todo remove this ??
      subLocation.add(new int[]{temp1, temp2});
      subLocation.add(new int[]{temp1+1, temp2});
      subLocation.add(new int[]{temp1+2, temp2});
    }
    else{
      // Vertical
      int temp1 = rng.nextInt(9);
      int temp2 = rng.nextInt(6);
      playerMap.cellAt(temp1,temp2).setSub();
      playerMap.cellAt(temp1,temp2+1).setSub();
      playerMap.cellAt(temp1,temp2+2).setSub();
      subLocation.add(new int[]{temp1, temp2});
      subLocation.add(new int[]{temp1, temp2+1});
      subLocation.add(new int[]{temp1, temp2+2});
    }
  }

}
