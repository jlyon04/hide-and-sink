package com.example.hideandsink;

import java.util.ArrayList;

interface StategyInterface {
  int mapSize=8;
  String getStrategyName();
  ArrayList<String> chooseAttack1(Map map);
  ArrayList<String> chooseAttack2(Map map, String lastAtk);

}
