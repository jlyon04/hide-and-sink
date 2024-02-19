package com.example.hideandsink;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MapView extends View {
  public MapView(Context context) {
    super(context);
  }
  public MapView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  private Map map;
  private int mapSize = 8;
  private int mapBackgroundColor = Color.BLUE;
  private int mapLineColor = Color.WHITE;

  //Map Touch Listener
    //interface??
    //List<BoardTOuchListener> listeners
  //@Override onTouchEven
  //@OVerride onDraw(Canvas canvas)

  private void drawShips(Canvas canvas){}
  private void drawShipHitCell(Canvas canvas){}

  //locateCell(x,y)
  //public void addBoardTouchListener(BoardTouchListener listener) {
  //public void removeBoardTouchListener(BoardTouchListener listener) {
  //private void notifyBoardTouch(int x, int y) {
}