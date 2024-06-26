package com.example.hideandsink;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  private GameManager game;
  private Player activePlayer, player;
  private ComputerPlayer opponent;
  private MapView playerMapView;
  private Button fireButton, scopeButton, sonarButton, confirmButton, backButton, moveButton, rotateButton, winButton;
  TextView gameText, playerHealthText, opponentHealthText;
  Dialog winDialog;

  //TODO remove this
  TextView debugview;
  LinearLayout offenseSelectionLayout, confirmSelectionLayout;
  private Context ctx;
  private String offenseChoice;
  private boolean onSecondTurn, dir;
  ArrayList<String> placerArray=new ArrayList<>();
  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ctx=this;
    dir = true; //direction of placers: true = Vertical

    // Handle Back Button
    OnBackPressedCallback backMessage= new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        exitMessageSetup();
      }
    };
    getOnBackPressedDispatcher().addCallback(this, backMessage);

    // Setup Game Manager with Players and Maps
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


    // Setup Views
    offenseSelectionLayout = findViewById(R.id.gameButtonsLayout);
    confirmSelectionLayout = findViewById(R.id.gameButtonsLayout2);
    // Setup Map Views, Text and Buttons
    playerMapView = findViewById(R.id.playerMapView);
    sonarButton = findViewById(R.id.sonarButton);
    fireButton = findViewById(R.id.fireButton);
    scopeButton = findViewById(R.id.scopeButton);
    confirmButton = findViewById(R.id.confirmBtn);
    backButton = findViewById(R.id.backBtn);
    moveButton = findViewById(R.id.moveBtn);
    rotateButton = findViewById(R.id.rotateBtn);
    gameText = findViewById(R.id.gameText);
    playerHealthText = findViewById(R.id.playerHealthText);
    opponentHealthText = findViewById(R.id.oppHealthText);

    // Dialog Box
    winDialog = new Dialog(ctx);
    winDialog.setContentView(R.layout.win_dialog);
    winButton = winDialog.findViewById(R.id.winMenuBtn);
    //winDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //winDialog.setCancelable(false);
    winDialog.getWindow().getAttributes().windowAnimations = R.style.animation;


    //TODO remove this
    debugview = findViewById(R.id.debugText);


    // Listeners
    sonarButton.setOnClickListener(sonarClick);
    confirmButton.setOnClickListener(confirmClick);
    scopeButton.setOnClickListener(scopeClick);
    rotateButton.setOnClickListener(rotateClick);
    fireButton.setOnClickListener(fireClick);
    backButton.setOnClickListener(backClick);
    winButton.setOnClickListener(winClick);


    //TODO Player Ship Place Map

    //Set Boards
    playerMapView.setMap(game.getPlayer().getMap());

    //Displya Player Health
    playerHealthText.setText(String.valueOf(game.getPlayer().health));
    opponentHealthText.setText(String.valueOf(game.getOpponentPlayer().health));

    // Update Display
    updateTurnDisplay();

    // If Local Multiplayer
    //if (NetworkAdapter.hasConnection()) {
      //startReadingNetworkMessages();
    //} else {
      //    toast("No connection with opponent"); //TODO used for debugging remove before submission, or add something else to indicate not connected
    //}

    //TODO remove this
    ArrayList<int[]> temp =  game.getOpponentPlayer().subLocation;
    String t1 = Arrays.toString(temp.get(0));
    String t2 = Arrays.toString(temp.get(1));
    String t3 = Arrays.toString(temp.get(2));
    debugview.setText(t1+" "+t2+" "+t3);
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

  public void disableAllBtns(){
    moveButton.setEnabled(false);
    sonarButton.setEnabled(false);
    scopeButton.setEnabled(false);
    fireButton.setEnabled(false);
  }
  public void enableAllBtns(){
    moveButton.setEnabled(true);
    sonarButton.setEnabled(true);
    scopeButton.setEnabled(true);
    fireButton.setEnabled(true);
  }
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
  public void showOffenseButtons(){
    offenseSelectionLayout.setVisibility(View.VISIBLE);
    confirmSelectionLayout.setVisibility(View.GONE);
  }
  public void showConfirmButtons(String offChoice) {
    offenseChoice = offChoice;
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        offenseSelectionLayout.setVisibility(View.GONE);
        confirmSelectionLayout.setVisibility(View.VISIBLE);
        if (offChoice.equals("sonar")){
          moveButton.setEnabled(false);
        }
        if (offChoice.equals("scope")){
          moveButton.setEnabled(false);
          rotateButton.setVisibility((View.VISIBLE));
        }
        if(offChoice.equals("fire")){
          moveButton.setEnabled(false);
        }
      }
    });
  }
  public void updateTurnDisplay() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        // Opponents Turn
        if (game.getOpponentPlayer() == game.getActivePlayer()){
          gameText.setText("Opponents Turn Please Wait");
        }else{
          gameText.setText("Your Turn");
          //TODO change text based on turn 1 or 2.
          //Remove option if selected, remove move if not selected first.
          enableAllBtns();
        }
      }
    });
  }

  // ------------------------- ON CLICK ------------------------------------
  View.OnClickListener sonarClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      // Start Listener and Place the selector boxes
      playerMapView.setOnTouchListener(sonarMapTouch);
      // Replace Buttons with Back and Confirm
      showConfirmButtons("sonar");
    }
  };

  View.OnClickListener scopeClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      // Start Listener and Place the selector boxes
      playerMapView.setOnTouchListener(scopeMapTouch);
      // Replace Buttons with Back and Confirm
      showConfirmButtons("scope");
    }
  };

  View.OnClickListener fireClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      playerMapView.setOnTouchListener(fireMapTouch);
      showConfirmButtons("fire");
    }
  };


  // ------------------ CONFIRM CLICK -------------------------------
  View.OnClickListener confirmClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //Handle Offensive Placement and Results
      handlePlacement();

      //Check Player Won
      if (game.getOpponentPlayer().health < 1){
        winDialog.show();
        return;
      }

      //Update UI
      game.getPlayer().getMap().removeAllPlacers();
      playerMapView.invalidate();
      opponentHealthText.invalidate();

      // Change Turns
      if (onSecondTurn){
        endSecondTurn();
      }else{
        onSecondTurn = true;
        offenseChoice = null;
        showOffenseButtons();
      }
      playerMapView.invalidate();
      playerMapView.setOnTouchListener(null);
    }
  };

  View.OnClickListener rotateClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if (dir){
        dir = false;
      }else{
        dir = true;
      }
    }
  };

  View.OnClickListener backClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      showOffenseButtons();
      //remove placers
      resetPlacerArray();
      playerMapView.getMap().removeAllPlacers();
      playerMapView.setOnTouchListener(null);
      playerMapView.invalidate();
    }
  };

  View.OnClickListener winClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      finish();
    }
  };
  //------------------------- ON TOUCH --------------------------------------------------
  private View.OnTouchListener sonarMapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        //Remove Old Placers
        game.getPlayer().getMap().removeAllPlacers();
        if (placerArray.size() > 0)
          resetPlacerArray();
        //Translate Touch and Place isPlace squares
        playerMapView.invalidate();
        int xy = playerMapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy / 100;
        int y = xy % 100;
        //handle out of bounds
        if (y > 6)
          y = 6;
        if (x > 6)
          x = 6;
        game.getPlayer().getMap().cellAt(x, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x, y + 1).isPlace = true;
        game.getPlayer().getMap().cellAt(x + 1, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x + 1, y + 1).isPlace = true;

        //set Global Variable
        placerArray.add(String.valueOf(x)+","+y);
        placerArray.add(String.valueOf(x)+","+(y+1));
        placerArray.add(String.valueOf(x+1)+","+(y));
        placerArray.add(String.valueOf(x+1)+","+(y+1));
        return true;
      }
      return false;
    }
    };

  private View.OnTouchListener scopeMapTouch = new View.OnTouchListener(){
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        //Remove Old Placers
        game.getPlayer().getMap().removeAllPlacers();
        if (placerArray.size() > 0)
          resetPlacerArray();
        //Translate Touch and Place isPlace squares
        playerMapView.invalidate();
        int xy = playerMapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy / 100;
        int y = xy % 100;

        //handle out of bounds
        if (dir) {
          if (y > 6)
            y = 6;

          // Set Placers
          game.getPlayer().getMap().cellAt(x, y).isPlace = true;
          game.getPlayer().getMap().cellAt(x, y+1).isPlace = true;

          //set Placer array for confirmation
          placerArray.add((x)+","+(y));
          placerArray.add((x)+","+(y+1));

        }else {
          if (x>6)
            x=6;
          // Set Placers
          game.getPlayer().getMap().cellAt(x, y).isPlace = true;
          game.getPlayer().getMap().cellAt(x+1, y).isPlace = true;

          //set Placer array for confirmation
          placerArray.add((x)+","+(y));
          placerArray.add((x+1)+","+(y));

        }
        return true;
      }
      return false;
    }
  };

  private View.OnTouchListener fireMapTouch = new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        game.getPlayer().getMap().removeAllPlacers();
        if (placerArray.size() > 0)
          resetPlacerArray();
        playerMapView.invalidate();
        int xy = playerMapView.locatePlace(motionEvent.getX(), motionEvent.getY());
        int x = xy / 100;
        int y = xy % 100;


        game.getPlayer().getMap().cellAt(x, y).isPlace = true;
        placerArray.add((x)+","+(y));
        return true;
      }
      return false;
    }
  };

  public void resetPlacerArray(){
    placerArray.clear();
  }

  private void handlePlacement(){
    if (offenseChoice.equals("sonar")) {
      sonarButton.setEnabled(false);
      game.placeSonar(placerArray, "player");
    }
    else if (offenseChoice.equals("scope")) {
      scopeButton.setEnabled(false);
      game.placeScope(placerArray, "player");
    }
    else if (offenseChoice.equals("fire")) {
      game.placeFire(placerArray, "player");
      //update health??
      opponentHealthText.setText(String.valueOf(game.getOpponentPlayer().health));
    }
  }
  private void endSecondTurn(){
    //Update Display
    disableAllBtns();
    showOffenseButtons();
    game.changeTurn();
    updateTurnDisplay();
    playerMapView.invalidate();
    //Clear offenseChoice??
    onSecondTurn=false;
    computerTurn();
    //Win Check
    //if (game.getPlayer().health<1){
  }
  void computerTurn(){
    //game.getOpponentPlayer().getMap()
    //;sleep
    // Check if play was a move

    //place attack 2
    //Check if computer won
    //Change turn
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        game.computerAttack();

        game.changeTurn();

        updateTurnDisplay();

        //todo debug
        ArrayList<int[]> subLoc = game.getOpponentPlayer().subLocation;
        String t1 = Arrays.toString(subLoc.get(0));
        String t2 = Arrays.toString(subLoc.get(1));
        String t3 = Arrays.toString(subLoc.get(2));
        debugview.setText(t1+" "+t2+" "+t3);
      }
    });
    thread.start();

  }

  public void exitMessageSetup(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("Are you sure you want to quit?");
    alertDialogBuilder.setTitle("HideAndSink");

    alertDialogBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
      // When the user click yes button then app will close
      finish();
    });


    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
    alertDialogBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
      // If user click no then dialog box is canceled.
      dialog.cancel();
    });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }

}