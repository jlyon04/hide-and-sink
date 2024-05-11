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
import androidx.core.content.res.ResourcesCompat;

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

  public void setMap(Map map){
    this.map = map;
  }
  public Map getMap(){return  this.map;}
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
    drawCellOptions(canvas);
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

  void drawShipPlacer(Canvas canvas, int x, int y, Drawable d){
    float viewSize = maxCoord();
    float tileSize = viewSize / 8;  //8 Is how many tiles there are
    float offSet = 8;
    int offSet2 = 8;
    int viewSize2 = Math.round(maxCoord());
    int tileSize2 = Math.round( viewSize/ 8);  //8 Is how many tiles there are
    //canvas.drawRect((tileSize* x) + offSet, (tileSize*y) + offSet, ((tileSize * x)+tileSize) - offSet, (((viewSize/10) * y)+tileSize) - offSet, dotted_black);
    //Drawable d = getResources().getDrawable(R.drawable.sub_place_sq, null);
    int left = (tileSize2* x) + offSet2;
    int top =(tileSize2*y) + offSet2;
    int right =((tileSize2 * x)+tileSize2) - offSet2;
    int bottom = (((viewSize2/8) * y)+tileSize2) - offSet2;
    d.setBounds(left, top, right, bottom);
    d.draw(canvas);
  }

  private void drawShips(Canvas canvas){}
  private void drawShipHitCell(Canvas canvas){}

  public void drawSquare(Canvas canvas, int color, int x, int y){
    Paint tempPaint = new Paint();
    tempPaint.setColor(color);
    float viewSize = Math.round(maxCoord());
    float tileSize = Math.round(viewSize / 8);  //10 Is how many tiles there are
    float offSet = 8;
    canvas.drawRect((tileSize* x) + offSet, (tileSize*y) + offSet, ((tileSize * x)+tileSize) - offSet, (((viewSize/8) * y)+tileSize) - offSet, tempPaint);
  }

  private void drawCellOptions(Canvas canvas){
    if (map == null){
      //TODO Debug Option
      // Start of game there is no map, need to check on this
      return;
    }
    for(int x = 0; x < mapSize; x++){
      for(int y = 0; y < mapSize; y++){

        // Sub Location
        if(map.cellAt(x, y).getIsSub()) {

        }
        // Sonar Hit
        if(map.cellAt(x,y).getIsSonarHit()){
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.pink, null), x, y);
        }
        // Sonar Miss
        else if(map.cellAt(x,y).getIsSonarMiss()){
          //drawShipPlacer(canvas, x, y, getResources().getDrawable(R.drawable.radar, null));
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.green, null), x, y);
        }
        // Scope Hit
        if(map.cellAt(x,y).getIsScopeHit()){
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.orange, null), x, y);
        }
        // Scope Miss
        else if(map.cellAt(x,y).getIsScopeMiss()){
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.yellow, null), x, y);
        }
        // Fire Hit
        if(map.cellAt(x,y).getIsFireHit()){
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.red, null), x, y);
        }
        // Fire Miss
        else if(map.cellAt(x,y).getIsFireMiss()){
          drawSquare(canvas, ResourcesCompat.getColor(getResources(), R.color.blue, null), x, y);
        }
        // Placer
        if(map.cellAt(x, y).isPlace){
          drawShipPlacer(canvas, x, y, getResources().getDrawable(R.drawable.sub_place_sq, null));
        }
      }
    }
  }

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