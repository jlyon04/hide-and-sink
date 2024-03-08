package com.example.hideandsink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private GameManager game;

  private Context ctx;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ctx=this;
    // Check for Continue Game
    Intent intent = getIntent();
    if (intent==null){
      //new game
      //??game = new GameManager();
    }

    // User Place Subs
    Intent subPlaceIntent = new Intent(this, PlacementActivity.class);
    subPlaceIntent.putExtra("placeType", "subPlacementStart");
    placementActivityLauncher.launch(subPlaceIntent);

    // If Local Multiplayer
      // Set Opponent not AI
      // startReadingNetworkMessages();
    if (NetworkAdapter.hasConnection()) {
      //if there is a multiplayer game, disable the AI difficulty change setting
      opponentSelect.setEnabled(false);
      strategyDescription.setText(getString(R.string.wifi_p2p_opponent));
      startReadingNetworkMessages();
    } else {
//            toast("No connection with opponent"); //TODO used for debugging remove before submission, or add something else to indicate not connected
    }
  }
  ActivityResultLauncher<Intent> placementActivityLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
          if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            ArrayList<String> xyList = data.getStringArrayListExtra("xyList");
            int i = 0;
            // Add sub to map
            // Wait for Opponent to Place
            // 
            // Start Game Loop
            //
          }
        }
      }
  );

  // ----------------- Bottom Display -------------------
  public void moveOrFightDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        TextView textView = new TextView(ctx);
        LinearLayout bottomLayout = findViewById(R.id.gameButtonLayout);
        //textView.setPadding();
        Button moveBtn = new Button(ctx);
        moveBtn.setText("Move");
        Button fightBtn = new Button(ctx);
        fightBtn.setText("Fight");
        textView.setText(R.string.choose_offense);
        bottomLayout.addView(textView);
        bottomLayout.addView(fightBtn);
        bottomLayout.addView(moveBtn);
      }
    });
  }
  // Offensive Option View
  public void offensiveOptionDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        TextView textView = new TextView(ctx);
        LinearLayout bottomLayout = findViewById(R.id.gameButtonLayout);
        //textView.setPadding();
        Button sonarBtn = new Button(ctx);
        Button scopeBtn = new Button(ctx);
        Button shootBtn = new Button(ctx);
        textView.setText(R.string.choose_attack);
        bottomLayout.addView(textView);
        bottomLayout.addView(sonarBtn);
        bottomLayout.addView(scopeBtn);
        bottomLayout.addView(shootBtn);
      }
    });
  }
  // Wait for opponent turn
  public void updateTurnDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        TextView textView = new TextView(ctx);
        LinearLayout bottomLayout = findViewById(R.id.gameButtonLayout);
        //textView.setPadding();
        textView.setText(R.string.opponent_turn);
        bottomLayout.addView(textView);
      }
    });
  }
}