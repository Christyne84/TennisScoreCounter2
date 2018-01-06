package com.example.android.tennisscorecounter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Default names of the players it they don't write their names and surnames
    static final String defaultNameA = "Player";
    static final String defaultSurnameA = "1";
    static final String defaultNameB = "Player";
    static final String defaultSurnameB = "2";
    //State of the scores
    static final String stateScoreTeamA = "GlobalScoreTeamA";
    static final String stateScoreTeamB = "GlobalScoreTeamB";
    static final String stateGamesTeamA = "GlobalGamesTeamA";
    static final String stateGamesTeamB = "GlobalGamesTeamB";
    static final String stateSetsTeamA = "GlobalSetsTeamA";
    static final String stateSetsTeamB = "GlobalSetsTeamB";
    static final String statePointsTeamA = "GlobalPointsTeamA";
    static final String statePointsTeamB = "GlobalPointsTeamB";
    //Tracks the score from Team A/Player A and Team B/Player B
    int pointsToScore[] = {0, 15, 30, 40};
    int pointsTeamA = 0;
    int pointsTeamB = 0;
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int gamesTeamA = 0;
    int gamesTeamB = 0;
    int setsTeamA = 0;
    int setsTeamB = 0;
    boolean gameFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore state members from saved instance
            scoreTeamA = savedInstanceState.getInt(stateScoreTeamA);
            scoreTeamB = savedInstanceState.getInt(stateScoreTeamB);
            gamesTeamA = savedInstanceState.getInt(stateGamesTeamA);
            gamesTeamB = savedInstanceState.getInt(stateGamesTeamB);
            setsTeamA = savedInstanceState.getInt(stateSetsTeamA);
            setsTeamB = savedInstanceState.getInt(stateSetsTeamB);
            pointsTeamA = savedInstanceState.getInt(statePointsTeamA);
            pointsTeamB = savedInstanceState.getInt(statePointsTeamB);

        }

        displayScoreForTeamA(scoreTeamA);
        displayScoreForTeamB(scoreTeamB);
        displayGamesForTeamA(gamesTeamA);
        displayGamesForTeamB(gamesTeamB);
        displaySetsForTeamA(setsTeamA);
        displaySetsForTeamB(setsTeamB);
        displayScore();

        //Sets the default names "Player A" and "Player B" in the EditTexts
        EditText namePlayerA = findViewById(R.id.editNamePlayerA);
        namePlayerA.setText(defaultNameA);
        EditText surnamePlayerA = findViewById(R.id.editSurnamePlayerA);
        surnamePlayerA.setText(defaultSurnameA);
        EditText namePlayerB = findViewById(R.id.editNamePlayerB);
        namePlayerB.setText(defaultNameB);
        EditText surnamePlayerB = findViewById(R.id.editSurnamePlayerB);
        surnamePlayerB.setText(defaultSurnameB);
    }

    /**
     * Checks the scores against the length of the array pointsToScore[] = {0, 15, 30, 40}
     * to display the score, to display deuce (40-40) or to display advantage(ADV) in the POINTS TextView
     */
    private void displayScore() {
        //pointsToScore.length = 4
        if(pointsTeamA < pointsToScore.length)
            displayScoreForTeamA(pointsToScore[pointsTeamA]);
        else if (pointsTeamA > pointsTeamB)
            displayAdvantageScoreTeamA();
        else
            displayScoreForTeamA(40);


        if(pointsTeamB < pointsToScore.length)
            displayScoreForTeamB(pointsToScore[pointsTeamB]);
        else if (pointsTeamB > pointsTeamA)
            displayAdvantageScoreTeamB();
        else
            displayScoreForTeamB(40);
    }

    /**
     * Save the user's current game state (scores, games, sets, points)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putInt(stateScoreTeamA, scoreTeamA);
        savedInstanceState.putInt(stateScoreTeamB, scoreTeamB);
        savedInstanceState.putInt(stateGamesTeamA, gamesTeamA);
        savedInstanceState.putInt(stateGamesTeamB, gamesTeamB);
        savedInstanceState.putInt(stateSetsTeamA, setsTeamA);
        savedInstanceState.putInt(stateSetsTeamB, setsTeamB);
        savedInstanceState.putInt(statePointsTeamA, pointsTeamA);
        savedInstanceState.putInt(statePointsTeamB, pointsTeamB);

        displayScoreForTeamA(scoreTeamA);
        displayScoreForTeamB(scoreTeamB);
        displayGamesForTeamA(gamesTeamA);
        displayGamesForTeamB(gamesTeamB);
        displaySetsForTeamA(setsTeamA);
        displaySetsForTeamB(setsTeamB);
    }

    /**
     * Increase the score for Team A/Player A by 1 point when the + button is clicked.
     * Checks if game is still on or it is finished, if it's still on, it increases the points
     * of Team A/Player A by 1. Calculates the score difference between players. If the score of
     * Team A/Player A is greater than 40 and the score of the Team B/Player B is at least 2 points
     * smaller (max 30), the player A wins the game, and adds 1 game to the Games textView.
     * Clears the number of points for both players to initial values 0 then displays the score.
     */
    public void addOneForTeamA (View view){
        if(gameFinished)
            return;

        pointsTeamA = pointsTeamA + 1;
        int diff = pointsTeamA - pointsTeamB;

        if(pointsTeamA >= 4 && diff >= 2) {
            addGamesForTeamA();

            pointsTeamA = 0;
            pointsTeamB = 0;

        }

        displayScore();

    }

    /**
     * Increase the score for Team B/Player B by 1 point when the + button is clicked.
     * Checks if game is still on or it is finished, if it's still on, it increases the points
     * of Team B/Player B by 1. Calculates the score difference between players. If the score of
     * Team B/Player B is greater than 40 and the score of the Team A/Player A is at least 2 points
     * smaller (max 30), the player B wins the game, and adds 1 game to the Games textView.
     * Sets the number of points for both players to initial values 0 then displays the score.
     */
    public void addOneForTeamB (View view){
        if(gameFinished)
            return;

        pointsTeamB = pointsTeamB + 1;
        int diff = pointsTeamB - pointsTeamA;

        if(pointsTeamB >= 4 && diff >= 2) {
            addGamesForTeamB();

            pointsTeamA = 0;
            pointsTeamB = 0;

        }
        displayScore();
    }



    /**
     * Increase the number of games for Team A/Player A by 1.
     * Calculates the difference between the number of games won by Team A/Player A and Team B/Player B.
     * At 6 games won for Team A/Player A and at least 2 games difference from Team B/Player B, the
     * Team A/Player A wins the a set and adds up 1 set to Team A/Player A.
     * Sets the number of games for both players to initial values 0 then displays it.
     */
    public void addGamesForTeamA (){
        gamesTeamA = gamesTeamA + 1;
        int diff = gamesTeamA - gamesTeamB;

        if (gamesTeamA >= 6 && diff >= 2) {
            addSetsForTeamA();
            gamesTeamA = 0;
            gamesTeamB = 0;
            displayGamesForTeamB(gamesTeamB);
        }
        displayGamesForTeamA(gamesTeamA);

    }

    /**
     * Increase the number of games for Team B/Player B by 1.
     * Calculates the difference between the number of games won by Team B/Player B and Team A/Player A.
     * At 6 games won for Team B/Player B and at least 2 games difference from Team A/Player A, the
     * Team B/Player B wins the a set and adds up 1 set to Team B/Player B.
     * Sets the number of games for both players to initial values 0 then displays it.
     */
    public void addGamesForTeamB (){
        gamesTeamB = gamesTeamB + 1;
        int diff = gamesTeamB - gamesTeamA;

        if (gamesTeamB >= 6 && diff >=2) {
            addSetsForTeamB();
            gamesTeamA = 0;
            gamesTeamB = 0;
            displayGamesForTeamA(gamesTeamA);
        }

        displayGamesForTeamB(gamesTeamB);

    }


    /**
     * Increase the number of sets for Team A/Player A by 1.
     * If either Team A/Player A or Team B/Player B wins 2 sets, the match is over.
     * Displays the winner in the Winner TextView.
     * Displays a toast message in the center of the screen: "The game is over! Press Reset to start a new match"
     */
    public void addSetsForTeamA (){
        setsTeamA = setsTeamA + 1;
        displaySetsForTeamA(setsTeamA);

        if ( setsTeamA == 2 || setsTeamB == 2){
            gameFinished = true;

            //the winner is: ""
            displayWinner();

            Toast toastText = Toast.makeText(this, "The game is over!\nPress Reset to start a new match", Toast.LENGTH_LONG);
            toastText.setGravity(Gravity.CENTER, 0, 0);
            toastText.show();
        }
    }

    /**
     * Increase the number of sets for Team B/Player B by 1.
     * If either Team A/Player A or Team B/Player B wins 2 sets, the match is over.
     * Displays the winner in the Winner TextView.
     * Displays a toast message in the center of the screen: "The game is over! Press Reset to start a new match"
     */
    public void addSetsForTeamB (){
        setsTeamB = setsTeamB + 1;
        displaySetsForTeamB(setsTeamB);

        if ( setsTeamA == 2 || setsTeamB == 2){
            gameFinished = true;

            //the winner is: ""
            displayWinner();

            Toast toastText = Toast.makeText(this, "The game is over!\nPress Reset to start a new match", Toast.LENGTH_LONG);
            toastText.setGravity(Gravity.CENTER, 0, 0);
            toastText.show();

        }
    }

    /**
     * Reset scores, games, sets, points and winner's name.
     */
    public void resetScore (View view){
        scoreTeamA = 0;
        scoreTeamB = 0;
        gamesTeamA = 0;
        gamesTeamB = 0;
        setsTeamA = 0;
        setsTeamB = 0;
        pointsTeamA = 0;
        pointsTeamB = 0;
        gameFinished = false;

        displayScoreForTeamA(scoreTeamA);
        displayScoreForTeamB(scoreTeamB);
        displayGamesForTeamA(gamesTeamA);
        displayGamesForTeamB(gamesTeamB);
        displaySetsForTeamA(setsTeamA);
        displaySetsForTeamB(setsTeamB);
        ((TextView) findViewById(R.id.winner)).setText("");

    }


    /**
     * Displays the score for Team A/Player A.
     */
    public void displayScoreForTeamA(int score) {
        TextView scoreView = findViewById(R.id.points_player_a);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the score for Team B/Player B.
     */
    public void displayScoreForTeamB(int score) {
        TextView scoreView = findViewById(R.id.points_player_b);
        scoreView.setText(String.valueOf(score));
    }


    /**
     * Displays the number of games won for Team A/Player A.
     */
    public void displayGamesForTeamA(int games) {
        TextView gamesView = findViewById(R.id.games_player_a);
        gamesView.setText(String.valueOf(games));
    }

    /**
     * Displays the number of games won for Team B/Player B.
     */
    public void displayGamesForTeamB(int games) {
        TextView gamesView = findViewById(R.id.games_player_b);
        gamesView.setText(String.valueOf(games));
    }

    /**
     * Displays the number of sets won for Team A/Player A.
     */
    public void displaySetsForTeamA(int sets) {
        TextView setsView = findViewById(R.id.sets_player_a);
        setsView.setText(String.valueOf(sets));
    }

    /**
     * Displays the number of sets won for Team B/Player B.
     */
    public void displaySetsForTeamB(int sets) {
        TextView setsView = findViewById(R.id.sets_player_b);
        setsView.setText(String.valueOf(sets));
    }



    /**
     * Displays advantage(ADV) point for Team A/Player A.
     */
    public void displayAdvantageScoreTeamA() {
        TextView advantageView = findViewById(R.id.points_player_a);
        advantageView.setText(R.string.advantage);
    }

    /**
     * Displays advantage(ADV) point for Team B/Player B.
     */
    public void displayAdvantageScoreTeamB() {
        TextView advantageView = findViewById(R.id.points_player_b);
        advantageView.setText(R.string.advantage);
    }

    /**
     * Displays the name of the winner at the end of the match.
     */
    public void displayWinner() {

        //Sets the default names "Player A" and "Player B" in the EditTexts
        EditText namePlayerA = findViewById(R.id.editNamePlayerA);
        String name_a = namePlayerA.getText().toString();
        EditText surnamePlayerA = findViewById(R.id.editSurnamePlayerA);
        String surname_a = surnamePlayerA.getText().toString();
        EditText namePlayerB = findViewById(R.id.editNamePlayerB);
        String name_b = namePlayerB.getText().toString();
        EditText surnamePlayerB = findViewById(R.id.editSurnamePlayerB);
        String surname_b = surnamePlayerB.getText().toString();

        //Sets the winner's name (the default names or the names typed by the user)
        if(setsTeamA > setsTeamB) {
            ((TextView) findViewById(R.id.winner)).setText(name_a + " " + surname_a + " won the match!");
        } else {
            ((TextView) findViewById(R.id.winner)).setText(name_b + " " + surname_b + " won the match!");
        }
    }
}

