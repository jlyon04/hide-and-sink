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
  boolean isSub=false;
  boolean isPlace=false;

  public void resetCell(){
    this.isPlace=false;
    this.isSub=false;
    this.isSonarHit=false;
    this.isSonarMiss=false;
    this.isScopeHit=false;
    this.isScopeMiss=false;
    this.isShotHit=false;
    this.isShotMiss=false;
  }
  public void setSub(){
    isSub = true;
  }
}
