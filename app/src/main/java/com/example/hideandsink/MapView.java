package com.example.hideandsink;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
  private final Paint mapLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  {
    mapLinePaint.setColor(mapLineColor);
  }
  private final Paint mapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  {
    mapPaint.setColor(mapBackgroundColor);
  }


  //Map Touch Listener
    //interface??
    //List<BoardTOuchListener> listeners
  //@Override onTouchEven
  //@OVerride onDraw(Canvas canvas)


/*              DRAW FUNCTION             */
  @Override
  protected void onDraw(Canvas canvas){
    super.onDraw(canvas);
    drawGrid(canvas);
  }
  private void drawGrid(Canvas canvas) {
    float maxCoord = maxCoord();
    float cellSize = lineGap();
    //Draw Container Square
    canvas.drawRect(0, 0, maxCoord, maxCoord, mapPaint);
    for(int i=0; i<numOfLines();i++){
      float xy = i* cellSize;
      canvas.drawLine(0, xy, maxCoord, xy, mapLinePaint);
      canvas.drawLine(xy,0,xy, maxCoord, mapLinePaint);
    }

  }
  private void drawShips(Canvas canvas){}
  private void drawShipHitCell(Canvas canvas){}

  //locateCell(x,y)
  //public void addBoardTouchListener(BoardTouchListener listener) {
  //public void removeBoardTouchListener(BoardTouchListener listener) {
  //private void notifyBoardTouch(int x, int y) {

  /* Draw Helpers */
  /** Calculate the gap between two horizontal/vertical lines. */
  protected float lineGap() {
    return Math.min(getMeasuredWidth(), getMeasuredHeight()) / (float) mapSize;
  }

  /** Calculate the number of horizontal/vertical lines. */
  private int numOfLines() {
    return mapSize + 1;
  }

  /** Calculate the maximum screen coordinate. */
  protected float maxCoord() {
    return lineGap() * (numOfLines() - 1);
  }
}