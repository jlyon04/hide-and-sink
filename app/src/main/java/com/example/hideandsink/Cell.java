package com.example.hideandsink;

public class Cell {
  int x = 0;
  int y = 0;

  /* Constructors */
  public Cell(int x, int y){
    this.x = x;
    this.y = y;
  }

  boolean isSonarHit=false;
  boolean isSonarMiss=false;
  boolean isScopeHit=false;
  boolean isScopeMiss=false;
  boolean isShotHit=false;
  boolean isShotMiss=false;
  boolean isShip=false;

}
