package com.example.hideandsink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button newGameButton = findViewById(R.id.newGameBtn);
    newGameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Start New Activity
        Intent intent = new Intent(view.getContext(), GameActivity.class);
        view.getContext().startActivity(intent);
      }
    });

  }
}