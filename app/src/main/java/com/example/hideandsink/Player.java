package com.example.hideandsink;

public class Player{
  private Map playerMap = null;
  //Testing vvv
  private int health = 0;
  public boolean isComputer;


  Player(){
    playerMap = new Map();
    health=2;
    isComputer=true;
  }

  Player(Map map){
    playerMap=map;
    health=2;
    isComputer=true;
  }
  Player(Map map, boolean isComputerv){
    playerMap=map;
    health=2;
    isComputer=isComputerv;
  }

  public void setPlayerMap(Map map){playerMap=map;}
}
