package com.example.hideandsink;

public class Cell {
  int x = 0;
  int y = 0;

  /* Constructors */
  public Cell(int x, int y){
    this.x = x;
    this.y = y;
  }

  private boolean isSonarHit;
  private boolean isSonarMiss;
  private boolean isScopeHit;
  private boolean isScopeMiss;
  private boolean isFireHit;
  private boolean isFireMiss;
  private boolean isSub;
  public boolean isPlace;

  // Getter and setter for isSonarHit
  public boolean getIsSonarHit() {
    return isSonarHit;
  }

  public void setSonarHit() {
    isSonarHit = true;
  }

  // Getter and setter for isSonarMiss
  public boolean getIsSonarMiss() {
    return isSonarMiss;
  }

  public void setSonarMiss() {
    isSonarMiss = true;
  }

  // Getter and setter for isScopeHit
  public boolean getIsScopeHit() {
    return isScopeHit;
  }

  public void setScopeHit() {
    isScopeHit = true;
  }

  // Getter and setter for isScopeMiss
  public boolean getIsScopeMiss() {
    return isScopeMiss;
  }

  public void setScopeMiss() {
    isScopeMiss = true;
  }

  // Getter and setter for isShotHit
  public boolean getIsFireHit() {
    return isFireHit;
  }

  public void setFireHit() {
    isFireHit = true;
  }

  // Getter and setter for isShotMiss
  public boolean getIsFireMiss() {
    return isFireMiss;
  }

  public void setFireMiss() {
    isFireMiss = true;
  }

  // Getter and Setter for isSub
  public boolean getIsSub() {
    return isSub;
  }
  public void setSub(){
    isSub = true;
  }

  public void setSub(boolean isSubV){
    isSub=isSubV;
  }
  public void resetCell(){
    this.isPlace=false;
    this.isSub=false;
    this.isSonarHit=false;
    this.isSonarMiss=false;
    this.isScopeHit=false;
    this.isScopeMiss=false;
    this.isFireHit=false;
    this.isFireMiss=false;
  }

}
