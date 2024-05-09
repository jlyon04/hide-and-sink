package com.example.hideandsink;

import java.util.Random;

public class StrategyRandom implements StategyInterface{
  String atkStr;
  @Override
  public String getStrategyName() {
    return "Randy Jo-honson";
  }

  @Override
  public String chooseAttack1(Map map) {
    Random rng = new Random();
    int attack = rng.nextInt(4);
    int x = rng.nextInt(mapSize);
    int y = rng.nextInt(mapSize);
    String xyStr=""+x+","+y;
    switch (attack){
      case 0:
        atkStr="move";
        break;
      case 1:
        atkStr="sonar";
        break;
      case 2:
        atkStr="scope";
        break;
      case 3:
        atkStr="fire";
        break;
    }
    return atkStr+","+xyStr;
  }

  @Override
  public String chooseAttack2(Map map) {
    return chooseAttack1(map);
  }
}
