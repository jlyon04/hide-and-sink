package com.example.hideandsink;

import java.util.Random;

public class StrategyBasic implements StategyInterface{
  static final String strategyName="Basic";
  private Random rng = new Random();

  @Override
  public String getStrategyName() {
    return strategyName;
  }

  @Override
  public String chooseAttack1(Map map) {
    //read board
    for(int y=0;y<map.size();y++){
      for(int x=0;x<map.size();x++){
        if(map.cellAt(x,y).getIsFireHit()){
          //check for
        }
        // If Scope and No Fire Hit - Fire
        if (map.cellAt(x,y).getIsScopeHit() && !map.cellAt(x,y).getIsFireHit()){
          return ("fire,"+x+y);
          //fire
        }
      }
    }
    return null;
  }

  @Override
  public String chooseAttack2(Map map) {
    return null;
  }

  }
