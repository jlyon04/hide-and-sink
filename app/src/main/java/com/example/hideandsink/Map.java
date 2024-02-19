package com.example.hideandsink;

public class Map {
  private final int size;
  private Cell[][] map = null;


  /* Constructors */
  public Map() {this.size=8;}
  public Map(int size){
    this.size=size;
    map = new Cell[size()][size()];
    createMap(map);
  }

  /* Functions */
  int size(){return this.size;}

  // Create the Map - Place all Cells in a 2D array
  private void createMap(Cell[][] map){
    for(int y=0;y<map.length;y++){
      for(int x=0;x<map[0].length;x++){
        map[y][x]=new Cell(x,y);
      }
    }
  }

  Cell cellAt(int x, int y){
    return map[y][x];
  }

  //End Game Check
  //placeShip
  //removeShip
  //health??
}
