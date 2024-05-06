package com.example.hideandsink;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private GameManager game;
  private Player activePlayer, player;
  private ComputerPlayer opponent;
  private MapView playerMapView;
  private Button fireButton, scopeButton, sonarButton;
  TextView gameText;
  private Context ctx;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ctx=this;

    // Check for Continue Game
    // Setup Game Manager with Players and Maps
    // Basic Vs Computer
    Intent intent = getIntent();
    game = new GameManager();
    if (intent==null){
      //failure??
    }else{
      ArrayList<String> xyList = intent.getStringArrayListExtra("xyList");
      if (xyList == null){
        // No Selection?? or Error??
        xyList.add("1,1");
        xyList.add("1,2");
        xyList.add("1,3");
      }
      setSubPlacement(xyList);
    }

    //Set Computer Subs
    game.getOpponentPlayer().placeSubRandomly();

    // Setup Map Views, Text and Buttons
    playerMapView = findViewById(R.id.playerMapView);
    sonarButton = findViewById(R.id.sonarButton);
    fireButton = findViewById(R.id.fireButton);
    scopeButton = findViewById(R.id.scopeButton);
    sonarButton.setOnClickListener(sonarClick);
    gameText = findViewById(R.id.gameText);

    //TODO Player Ship Place Map

    //Set Boards
    playerMapView.setMap(game.getPlayer().getMap());

    // Update Display
    updateTurnDisplay();

    // If Local Multiplayer
    //if (NetworkAdapter.hasConnection()) {
      //startReadingNetworkMessages();
    //} else {
      //    toast("No connection with opponent"); //TODO used for debugging remove before submission, or add something else to indicate not connected
    //}
  }

  private void setSubPlacement(ArrayList<String> xyList){
    for (int i =0; i<xyList.size(); i++){
      String[] res = xyList.get(i).split(",");
      int x = Integer.valueOf(res[0]);
      int y = Integer.valueOf(res[1]);
      game.getPlayer().getMap().cellAt(x,y).setSub();
    }
  }
  // ----------------- Bottom Display -------------------
  public void moveOrFightDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        TextView textView = new TextView(ctx);
        LinearLayout bottomLayout = findViewById(R.id.gameButtonsLayout);
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
        LinearLayout bottomLayout = findViewById(R.id.gameButtonsLayout);
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

  public void updateOffenseButtons() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        LinearLayout buttonsLayout = findViewById(R.id.gameButtonsLayout);
        LinearLayout buttonsLayout2 = findViewById(R.id.gameButtonsLayout2);
        buttonsLayout.setVisibility(View.GONE);
        buttonsLayout2.setVisibility(View.VISIBLE);
      }
    });
  }
  public void updateTurnDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        // Opponents Turn
        if (game.getOpponentPlayer() == game.getActivePlayer()){
          gameText.setText("Opponents Turn");
        }else{
          gameText.setText("Your Turn");
          //TODO change text based on turn 1 or 2.
          //Remove option if selected, remove move if not selected first.
        }
      }
    });
  }

  View.OnClickListener sonarClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      // Start Listener and Place the selector boxes
      playerMapView.setOnTouchListener(sonarMapTouch);
      // Replace Buttons with Back and Confirm
      updateOffenseButtons();
    }
  };


  private View.OnTouchListener sonarMapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      //Remove Old Placers
      game.getPlayer().getMap().removeAllPlacers();

      //if (xyList != null){
      //xyList.clear();
      //}
      //Refresh View
      playerMapView.invalidate();
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        int xy = playerMapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy / 100;
        int y = xy % 100;
        //Todo testing
        // -------------- SONAR PLACEMENT -------------
        //handle out of bounds
        if(y>6)
          y=6;
        if (x>6)
          x=6;
        game.getPlayer().getMap().cellAt(x, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x, y + 1).isPlace = true;
        game.getPlayer().getMap().cellAt(x+1, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x+1, y + 1).isPlace = true;
      }
      return false;
    }
  };


}