package com.example.hideandsink;

import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class StrategyBasic implements StategyInterface{
  static final String strategyName="Basic";
  private Random rng = new Random();
  int myHealth = 2;

  @Override
  public String getStrategyName() {
    return strategyName;
  }

  @Override
  public ArrayList<String> chooseAttack1(Map map, int health) {
    if(health!=myHealth){
      //Ive been hit - move
      myHealth=health;
    }
    //Was I hit

    //read board
    for(int y=0;y<map.size();y++){
      for(int x=0;x<map.size();x++){
        if(map.cellAt(x,y).getIsFireHit()){
          //check for
        }
        // If Scope and No Fire Hit/Miss -> Fire
        if (map.cellAt(x,y).getIsScopeHit() && !map.cellAt(x,y).getIsFireHit() && !map.cellAt(x,y).getIsFireMiss()){
          //fire
          atkStr.add("fire");
          atkStr.add(x+","+y);
        }
        // Positive Sonar - No scope
        if (isPosSonarAndNoScope(map.cellAt(x,y))){
          atkStr.add("scope");
          atkStr.add(x+","+y);
          //now what - you are at top left corner check other directions
          if(isPosSonarAndNoScope(map.cellAt(x+1,y)))
            atkStr.add((x+1)+","+y);
          else if(isPosSonarAndNoScope(map.cellAt(x,y+1)))
            atkStr.add(x+","+(y+1));
          else if(isPosSonarAndNoScope(map.cellAt(x+1,y+1)))
            atkStr.add((x+1)+","+(y+1));
          else
            //temp - out of bouncs check??
            atkStr.add((x+1)+","+y);
        }
      }
    }
    return null;
  }

  @Override
  public ArrayList<String> chooseAttack2(Map map, String lastAtk) {
    return null;
  }

  private boolean isPosSonarAndNoScope(Cell cell){
    //what if check goes out of bounds??
    return cell.getIsSonarHit() && !cell.getIsScopeHit() && !cell.getIsScopeMiss();
  }

}
