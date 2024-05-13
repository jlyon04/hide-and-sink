package com.example.hideandsink;

import java.util.ArrayList;
import java.util.Random;

public class StrategyRandom implements StategyInterface{
  Random rng = new Random();
  @Override
  public String getStrategyName() {
    return "Randy Jo-honson";
  }

  @Override
  public ArrayList<String> chooseAttack1(Map map, int health){
    if(atkStr.size()>0){
      atkStr.clear();
    }
    int attack = rng.nextInt(4);
    String xyStr = null;
    switch (attack){
      case 0:
        atkStr.add("move");
        placeSub();
        break;
      case 1:
        atkStr.add("sonar");
        placeSonar();
        break;
      case 2:
        atkStr.add("scope");
        //Vertical only
        placeScope();
        break;
      case 3:
        atkStr.add("fire");
        int x = rng.nextInt(mapSize);
        int y = rng.nextInt(mapSize);
        atkStr.add(x+","+y);
        break;
    }
    return atkStr;
  }

  @Override
  public ArrayList<String> chooseAttack2(Map map, String lastAtk) {
    if(atkStr.size()>0){
      atkStr.clear();
    }
    int attack = rng.nextInt(2);
    if (lastAtk.equals("sonar")){
      if(attack==0) {
        atkStr.add("scope");
        placeScope();
      }
      else {
        atkStr.add("fire");
        placeFire();
      }
    }
    else if (lastAtk.equals("scope")){
      if(attack==0) {
        atkStr.add("sonar");
        placeSonar();
      }
      else {
        atkStr.add("fire");
        placeFire();
      }
    }
    else if (lastAtk.equals("fire")){
      if(attack==0) {
        atkStr.add("sonar");
        placeSonar();
      }else {
        atkStr.add("scope");
        placeScope();
      }
    }
    return atkStr;
  }

  private void placeSub() {
    // if true then ship is horizontal
    boolean orientation = rng.nextBoolean();
    String locString;

    if (orientation) {
      // Horizontal
      int temp1 = rng.nextInt((mapSize-2));
      int temp2 = rng.nextInt(mapSize+1);
      atkStr.add(temp1+","+temp2);
      atkStr.add((temp1+1)+","+temp2);
      atkStr.add((temp1+2)+","+temp2);
    } else {
      // Vertical
      int temp1 = rng.nextInt(9);
      int temp2 = rng.nextInt(6);
      atkStr.add(temp1+","+temp2);
      atkStr.add((temp1)+","+(temp2+1));
      atkStr.add((temp1)+","+(temp2+2));
    }
  }

  private void placeSonar(){
    int x = rng.nextInt(mapSize-1);
    int y = rng.nextInt(mapSize-1);
    atkStr.add((x)+","+(y));
    atkStr.add((x)+","+(y+1));
    atkStr.add((x+1)+","+(y));
    atkStr.add((x+1)+","+(y+1));
  }

  private void placeScope(){
   //vertical only
    int x = rng.nextInt(mapSize);
    int y = rng.nextInt(mapSize-1);
    atkStr.add((x)+","+(y));
    atkStr.add((x)+","+(y+1));
  }

  private void placeFire(){
    int x = rng.nextInt(mapSize);
    int y = rng.nextInt(mapSize);
    atkStr.add(x+","+y);
  }

}
