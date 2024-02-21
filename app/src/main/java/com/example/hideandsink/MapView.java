package com.example.hideandsink;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

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
  private int mapBackgroundColor = Color.parseColor("#00B7C3");
  private int mapLineColor = Color.WHITE;
  private final Paint mapLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  {
    mapLinePaint.setColor(mapLineColor);
  }
  private final Paint mapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  {
    mapPaint.setColor(mapBackgroundColor);
  }

  // TEST PUBLICS
  boolean placerMode=false;


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
    if(placerMode){
      drawShipPlacer(canvas, 1, 0);
      drawShipPlacer(canvas, 1, 1);
      drawShipPlacer(canvas, 1, 2);
    }
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

  void drawShipPlacer(Canvas canvas, int x, int y){
    Paint dotted_black = new Paint();
    dotted_black.setStyle(Paint.Style.STROKE);
    dotted_black.setColor(Color.BLACK);
    dotted_black.setStrokeWidth(3);
    float viewSize = maxCoord();
    float tileSize = viewSize / 8;  //8 Is how many tiles there are
    float offSet = 8;
    int offSet2 = 8;
    int viewSize2 = Math.round(maxCoord());
    int tileSize2 = Math.round( viewSize/ 8);  //8 Is how many tiles there are
    //canvas.drawRect((tileSize* x) + offSet, (tileSize*y) + offSet, ((tileSize * x)+tileSize) - offSet, (((viewSize/10) * y)+tileSize) - offSet, dotted_black);
    Drawable d = getResources().getDrawable(R.drawable.sub_place_sq, null);
    int left = (tileSize2* x) + offSet2;
    int top =(tileSize2*y) + offSet2;
    int right =((tileSize2 * x)+tileSize2) - offSet2;
    int bottom = (((viewSize2/8) * y)+tileSize2) - offSet2;
    d.setBounds(left, top, right, bottom);
    d.draw(canvas);
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

  public int locatePlace(float x, float y) {
    if (x <= maxCoord() && y <= maxCoord()) {
      final float placeSize = lineGap();
      int ix = (int) (x / placeSize);
      int iy = (int) (y / placeSize);
      //80?
      return ix * 100 + iy;
    }
    return -1;
  }

}