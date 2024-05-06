package com.example.hideandsink;

public class Player{
  private Map playerMap = null;
  private int health = 0;


  Player(){
    playerMap = new Map();
    health=2;
  }

  Player(Map map){
    playerMap=map;
    health=2;
  }

  public void setPlayerMap(Map map){playerMap=map;}
}
