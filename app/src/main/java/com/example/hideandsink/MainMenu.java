package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);

    Button newGameButton = findViewById(R.id.newGameBtn);
    newGameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Start Placement Activity
        Intent intent = new Intent(view.getContext(), PlacementActivity.class);
        intent.putExtra("placeType", "subPlacementStart");
        //Should I change this to a launcher?
        view.getContext().startActivity(intent);
      }
    });
  }
}