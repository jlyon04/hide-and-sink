package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class PlacementActivity extends AppCompatActivity {

  private MapView mapView;
  private Map map;
  private boolean dir; //true = vertical


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_placement);

    // Initialize Board and Placers
    map = new Map();
    //map.placePlacer("sub", 0,0,true);
    // Placer Mode for Drawing
    // mapView.invalidate();

    mapView = findViewById(R.id.placementMapView);
    mapView.setOnTouchListener(mapTouch);
    mapView.setMap(map);
  }
  private View.OnTouchListener mapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      //Remove Old Placers
      map.clearMap();
      mapView.invalidate();
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
        int xy = mapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy/100;
        int y = xy%100;
        //handle out of bounds
        if (dir) {
          if (y >= 6) {
            y = 5;
          }
          map.cellAt(x,y).isPlace=true;
          map.cellAt(x,y+1).isPlace=true;
          map.cellAt(x,y+2).isPlace=true;
        }
        else{
         if (x >= 6) {
           x=5;
         }
          map.cellAt(x,y).isPlace=true;
          map.cellAt(x+1,y).isPlace=true;
          map.cellAt(x+2,y).isPlace=true;
        }
        //set ship x and y?
        //invalidate?
        mapView.invalidate();
      }
      return false;
    }
  };

}