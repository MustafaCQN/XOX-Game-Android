package com.mustafacqn.xoxgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button[][] buttons;
    private boolean isPlayer1Turn;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView playerPointsTW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGame();
    }

    private void initGame() {

        playerPointsTW = findViewById(R.id.player_points);

        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        isPlayer1Turn = true;
        roundCount = 0;

        // getting button list and setting their onClickListeners
        buttons = new Button[3][3];
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                buttons[i][j] = findViewById(getResources().getIdentifier("button"+i+""+j, "id", getPackageName()));
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTextSize(50);
            }
        }
    }

    @Override
    public void onClick(View v) {

        // check if the tile is occupied
        if (!((Button)v).getText().toString().equals("")){
            Log.e(TAG, "onClick, Tile is already drew");
            return;
        }

        // draw the tile based on turn
        if (isPlayer1Turn) {
            ((Button)v).setText("X");
        }else {
            ((Button)v).setText("O");
        }

        // increase the round
        roundCount++;

        // check winning, draw or nextTurn situations
        if (checkForWin()){
            if (isPlayer1Turn){
                player1Wins();
            }else {
                player2Wins();
            }
        }else if (roundCount == 9) {
            draw();
        }else {
            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        // if starter was player1, then start with player2 or vice versa
        resetBoard(isPlayer1Turn ? "O" : "X");
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "O wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard("X");
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "X wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard("O");
    }

    private void resetBoard(String whoWillStart) {
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        isPlayer1Turn = whoWillStart.equals("X");
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard("X");
    }

    private void updatePointsText() {
        playerPointsTW.setText("X " + player1Points + " - " + player2Points + " O");
    }


    private boolean checkForWin() {
        String [][] fields = new String [3][3];

        // take the String values of the buttons
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        // horizontal check
        for (int i = 0; i<3; i++){
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals("")){
                return true;
            }
        }

        // vertical check
        for (int i = 0; i<3; i++){
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals("")){
                return true;
            }
        }

        // cross checks
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals("")){
            return true;
        }

        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals("")){
            return true;
        }

        return false;
    }
}
