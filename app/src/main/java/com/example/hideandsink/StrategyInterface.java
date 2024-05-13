package com.example.hideandsink;

import java.util.ArrayList;

interface StategyInterface {
  int mapSize=8;
  ArrayList<String> atkStr=new ArrayList<>();
  String getStrategyName();
  ArrayList<String> chooseAttack1(Map map, int health);
  ArrayList<String> chooseAttack2(Map map, String lastAtk);

}
