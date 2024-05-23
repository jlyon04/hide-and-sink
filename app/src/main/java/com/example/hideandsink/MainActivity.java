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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  private GameManager game;
  private Player activePlayer, player;
  private ComputerPlayer opponent;
  private MapView playerMapView;
  private Button confirmButton, backButton, rotateButton, winButton, clearMapButton, toggleSubButton;
  private ImageButton sonarButton, fireButton, scopeButton, moveButton;
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
    confirmButton = findViewById(R.id.confirmButton);
    backButton = findViewById(R.id.backButton);
    moveButton = findViewById(R.id.moveButton);
    rotateButton = findViewById(R.id.rotateButton);
    clearMapButton = findViewById(R.id.clearMapButton);
    toggleSubButton = findViewById(R.id.toggleSubButton);
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
    moveButton.setOnClickListener(moveClick);
    clearMapButton.setOnClickListener(clearMapClick);


    //Set Boards
    playerMapView.setMap(game.getPlayer().getMap());

    //Display Player Health
    playerHealthText.setText(String.valueOf(game.getPlayer().health));
    opponentHealthText.setText(String.valueOf(game.getOpponentPlayer().health));

    // Update Display
    updateTurnDisplay("Your Turn");

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
    moveButtonDisable();
    sonarButtonDisable();
    scopeButtonDisable();
    fireButtonDisable();
  }
  public void enableAllBtns(){
    moveButton.setClickable(true);
    moveButton.setBackgroundColor(getResources().getColor(R.color.water_blue));
    sonarButton.setClickable(true);
    sonarButton.setBackgroundColor(getResources().getColor(R.color.water_blue));
    scopeButton.setClickable(true);
    scopeButton.setBackgroundColor(getResources().getColor(R.color.water_blue));
    fireButton.setClickable(true);
    fireButton.setBackgroundColor(getResources().getColor(R.color.water_blue));
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
        if (offChoice.equals("scope") || offChoice.equals("move")){
          rotateButton.setVisibility((View.VISIBLE));
        }
      }
    });
  }
  public void updateTurnDisplay(String dispString) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        gameText.setText(dispString);
      }
    });
  }
  private void moveButtonDisable(){
    moveButton.setClickable(false);
    moveButton.setBackgroundColor(Color.GRAY);
  }
  private void sonarButtonDisable(){
    sonarButton.setClickable(false);
    sonarButton.setBackgroundColor(Color.GRAY);
  }
  private void scopeButtonDisable(){
    scopeButton.setClickable(false);
    scopeButton.setBackgroundColor(Color.GRAY);
  }
  private void fireButtonDisable(){
    fireButton.setClickable(false);
    fireButton.setBackgroundColor(Color.GRAY);
  }

  // ------------------------- ON CLICK ------------------------------------
  View.OnClickListener sonarClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      // Start Listener and Place the selector boxes
      playerMapView.setOnTouchListener(sonarMapTouch);
      // Replace Buttons with Back and Confirm
      showConfirmButtons("sonar");
      updateTurnDisplay("Your Turn: Sonar");
    }
  };

  View.OnClickListener scopeClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      // Start Listener and Place the selector boxes
      playerMapView.setOnTouchListener(scopeMapTouch);
      // Replace Buttons with Back and Confirm
      showConfirmButtons("scope");
      updateTurnDisplay("Your Turn: Scope");
    }
  };

  View.OnClickListener fireClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      playerMapView.setOnTouchListener(fireMapTouch);
      showConfirmButtons("fire");
      updateTurnDisplay("Your Turn: Fire");
    }
  };

  View.OnClickListener moveClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      playerMapView.setOnTouchListener(moveMapTouch);
      showConfirmButtons("move");
      updateTurnDisplay("Your Turn: Move Sub");
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
      if (onSecondTurn || offenseChoice.equals("move")){
        offenseChoice = null;
        onSecondTurn=false;
        endSecondTurn();
      }else{
        onSecondTurn = true;
        offenseChoice = null;
        //moveButton.setEnabled(false);
        moveButtonDisable();
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
      resetPlacerArray();
      rotateButton.setVisibility(View.GONE);
      playerMapView.getMap().removeAllPlacers();
      playerMapView.setOnTouchListener(null);
      playerMapView.invalidate();
      updateTurnDisplay(getString(R.string.your_turn_choose));
    }
  };

  View.OnClickListener winClick = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      finish();
    }
  };

  View.OnClickListener clearMapClick = new View.OnClickListener(){
    @Override
    public void onClick(View view) {
      game.getPlayer().getMap().clearMap();
      playerMapView.invalidate();
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

  private View.OnTouchListener moveMapTouch = new View.OnTouchListener(){
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
        if (y > 5)
          y = 5;

        // Set Placers
        game.getPlayer().getMap().cellAt(x, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x, y+1).isPlace = true;
        game.getPlayer().getMap().cellAt(x, y+2).isPlace = true;

        //set Placer array for confirmation
        placerArray.add((x)+","+(y));
        placerArray.add((x)+","+(y+1));
        placerArray.add((x)+","+(y+2));

      }else {
        if (x>5)
          x=5;
        // Set Placers
        game.getPlayer().getMap().cellAt(x, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x+1, y).isPlace = true;
        game.getPlayer().getMap().cellAt(x+2, y).isPlace = true;

        //set Placer array for confirmation
        placerArray.add((x)+","+(y));
        placerArray.add((x+1)+","+(y));
        placerArray.add((x+2)+","+(y));

      }
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
      //sonarButton.setEnabled(false);
      sonarButtonDisable();
      game.placeSonar(placerArray, "player");
    }
    else if (offenseChoice.equals("scope")) {
      //scopeButton.setEnabled(false);
      scopeButtonDisable();
      game.placeScope(placerArray, "player");
    }
    else if (offenseChoice.equals("fire")) {
      game.placeFire(placerArray, "player");
      //update health??
      opponentHealthText.setText(String.valueOf(game.getOpponentPlayer().health));
    } else if (offenseChoice.equals("move")) {
      game.getPlayer().moveSub(placerArray);
    }
  }
  private void endSecondTurn(){
    //Update Display
    disableAllBtns();
    showOffenseButtons();
    game.changeTurn();
    updateTurnDisplay("Opponent Turn, Please Wait");
    //Clear offenseChoice??
    computerTurn();
    //playerMapView.invalidate();
  }
  void computerTurn(){
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        game.computerAttack();

        //check win
        if (game.getPlayer().health<1){
          TextView winText = winDialog.findViewById(R.id.winTextview);
          winText.setText("You Have Lost");
          winDialog.show();
        }

        game.changeTurn();

        updateTurnDisplay("Your Turn");
        // Enable Buttons
        enableAllBtns();

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