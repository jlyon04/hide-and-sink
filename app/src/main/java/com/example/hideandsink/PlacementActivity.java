package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PlacementActivity extends AppCompatActivity {

  private MapView mapView;
  private Map map;
  private boolean dir; //true = vertical

  private String placeType;

  private ArrayList<String> xyList= new ArrayList<String>();
  Button confirmBtn, rotateBtn, backBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_placement);

    // Initialize Board and Placers
    dir=true;
    map = new Map();

    Intent mapdata = this.getIntent();
    String mapString = mapdata.getStringExtra("map");
    placeType = mapdata.getStringExtra("placeType");

    mapView = findViewById(R.id.placementMapView);
    mapView.setMap(map);
    mapView.setOnTouchListener(mapTouch);

    confirmBtn = findViewById(R.id.confirmSubBtn);
    confirmBtn.setOnClickListener(confirmClick);
    rotateBtn = findViewById(R.id.rotateSubBtn);
    rotateBtn.setOnClickListener(rotateClick);

    // Set Starting Placers
    map.cellAt(0, 0).isPlace = true;
    map.cellAt(0,  1).isPlace = true;
    map.cellAt(0,  2).isPlace = true;
    xyList.add(("0") + "," + ("0"));
    xyList.add(("0") + "," + ("1"));
    xyList.add(("0") + "," + ("2"));
  }

  //-------------------------- Listeners ---------------------------------

  // Map Touch Listener - Place Sub
  private View.OnTouchListener mapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      //Remove Old Placers
      map.removeAllPlacers();
      if (xyList != null){
        xyList.clear();
      }
      mapView.invalidate();
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        int xy = mapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy / 100;
        int y = xy % 100;
        // ---------- SUB PLACEMENT --------------
        if (placeType.equals("subPlacement") || placeType.equals("subPlacementStart")) {
          //handle out of bounds
          if (dir) {
            if (y >= 6) {
              y = 5;
            }
            map.cellAt(x, y).isPlace = true;
            map.cellAt(x, y + 1).isPlace = true;
            map.cellAt(x, y + 2).isPlace = true;
            xyList.add(String.valueOf(x) + "," + String.valueOf(y));
            xyList.add((x) + "," + (y + 1));
            xyList.add((x) + "," + (y + 2));
          } else {
            if (x >= 6) {
              x = 5;
            }
            map.cellAt(x, y).isPlace = true;
            map.cellAt(x + 1, y).isPlace = true;
            map.cellAt(x + 2, y).isPlace = true;
            xyList.add(String.valueOf(x) + "," + String.valueOf(y));
            xyList.add((x + 1) + "," + y);
            xyList.add((x + 2) + "," + y);
          }
          mapView.invalidate();
        }
        // -------------- SONAR PLACEMENT -------------
        else if(placeType.equals("sonarPlacement")) {
          //handle out of bounds
          if(y>6)
            y=6;
          if (x>6)
            x=6;
          map.cellAt(x, y).isPlace = true;
          map.cellAt(x, y + 1).isPlace = true;
          map.cellAt(x+1, y).isPlace = true;
          map.cellAt(x+1, y + 1).isPlace = true;
          xyList.add(x + "," + y);
          xyList.add((x) + "," + (y + 1));
          xyList.add((x+1) + "," + (y));
          xyList.add((x+1) + "," + (y + 1));
        }
        // -------------- SCOPE PLACEMENT -------------
        else if(placeType.equals("scopePlacement")) {
          if (dir) {
            //handle out of bounds
            if (y > 6)
              y = 6;
            map.cellAt(x, y).isPlace = true;
            map.cellAt(x, y + 1).isPlace = true;
            xyList.add(x + "," + y);
            xyList.add((x) + "," + (y + 1));
          }
          else{
            if (x > 6)
              x=6;
            map.cellAt(x, y).isPlace = true;
            map.cellAt(x+1, y).isPlace = true;
            xyList.add(x + "," + y);
            xyList.add((x+1) + "," + y);
          }
        }
        // -------------- SHOT PLACEMENT -------------
        else if(placeType.equals("shotPlacement")) {
          map.cellAt(x, y).isPlace = true;
          xyList.add(x + "," + y);
        }
      }
      return false;
    }
  };
  //Confirm Button
  private View.OnClickListener confirmClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //Capture X,Y as STRINGS for all three placements
      if (xyList.isEmpty()){
        // FAIL - Alert User, Must have a selection
        return;
      }
      Intent mainIntent = new Intent(view.getContext(), MainActivity.class);
      mainIntent.putStringArrayListExtra("xyList", xyList);
      startActivity(mainIntent);
      finish();
    }
  };
  private View.OnClickListener rotateClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(dir)
        dir=false;
      else
        dir=true;
      mapView.invalidate();
    }
  };
}
