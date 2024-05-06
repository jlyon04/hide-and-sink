package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
  private MapView mapView;
  Button placeSubButton;
  Button confirmSubButton;
  //private GameManager gameManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    // Get Game Manager from previous or make a new one

    // Set Board View
    mapView = findViewById(R.id.mapView);

    placeSubButton = findViewById(R.id.placeSubBtn);
    confirmSubButton = findViewById(R.id.confrimSubBtn);
    placeSubButton.setOnClickListener(placeSubListen);

  }

  View.OnClickListener placeSubListen = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
     //disable other controls, change bottom to confrim
      confirmSubButton.setVisibility(View.VISIBLE);
      placeSubButton.setVisibility(View.INVISIBLE);

     // add outline to map
      mapView.placerMode=true;
      mapView.invalidate();

     //start touch listener
      mapView.setOnTouchListener(mapTouch);

      /*
       mapView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
          view.performClick();
          if (motionEvent.getAction() == MotionEvent.ACTION_UP){
            int xy = mapView.locatePlace(motionEvent.getX(), motionEvent.getY());
            if (xy >=0){
              int x = xy/100;
              int y = xy%100;
            }

          }
          return false;
        }
      });

      mapView.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          get
        }
      }));
       */
    }
  };

  private View.OnTouchListener mapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
        int xy = mapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy/100;
        int y = xy%100;
        int test = 0;
      }
      return false;
    }
  };

}