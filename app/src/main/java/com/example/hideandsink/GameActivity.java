package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
  private MapView mapView;
  //private GameManager gameManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    // Get Game Manager from previous or make a new one

    // Set Board View
    mapView = (MapView) findViewById(R.id.mapView);



  }
}