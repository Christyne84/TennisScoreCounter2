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
     * Increase the sets for Team A/Player A by 1 point.
     */
    public void addOneForTeamA (View view){
        if(gameFinished)
            return;

        pointsTeamA = pointsTeamA + 1;
        int diff = pointsTeamA - pointsTeamB;
        //TODO: explain 4 and 2 and the points-to-score logic
        if(pointsTeamA >= 4 && diff >= 2) {
            addGamesForTeamA();

            pointsTeamA = 0;
            pointsTeamB = 0;

        }

        displayScore();

    }

    /**
     * Increase the score for Team B/Player B by 1 point.
     */
    public void addOneForTeamB (View view){
        if(gameFinished)
            return;

        pointsTeamB = pointsTeamB + 1;
        int diff = pointsTeamB - pointsTeamA;
        //TODO: explain 4 and 2 and the points-to-score logic
        if(pointsTeamB >= 4 && diff >= 2) {
            addGamesForTeamB();

            pointsTeamA = 0;
            pointsTeamB = 0;

        }
        displayScore();
    }



    /**
     * Increase the games for Team A by 1 point.
     */
    public void addGamesForTeamA (){
        gamesTeamA = gamesTeamA + 1;
        int diff = gamesTeamA - gamesTeamB;
        //TODO: explain 6 and 2 and the game logic
        if (gamesTeamA >= 6 && diff >= 2) {
            addSetsForTeamA();
            gamesTeamA = 0;
            gamesTeamB = 0;
            displayGamesForTeamB(gamesTeamB);
        }
        displayGamesForTeamA(gamesTeamA);

    }

    /**
     * Increase the game for Team B by 1 point.
     */
    public void addGamesForTeamB (){
        gamesTeamB = gamesTeamB + 1;
        int diff = gamesTeamB - gamesTeamA;
        //TODO: explain 6 and 2 and the game logic
        if (gamesTeamB >= 6 && diff >=2) {
            addSetsForTeamB();
            gamesTeamA = 0;
            gamesTeamB = 0;
            displayGamesForTeamA(gamesTeamA);
        }

        displayGamesForTeamB(gamesTeamB);

    }


    /**
     * Increase the sets for Team A by 1 point.
     */
    public void addSetsForTeamA (){
        setsTeamA = setsTeamA + 1;
        displaySetsForTeamA(setsTeamA);

        //TODO: explain "2" and the game logic
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
     * Increase the sets for Team B by 1 point.
     */
    public void addSetsForTeamB (){
        setsTeamB = setsTeamB + 1;
        displaySetsForTeamB(setsTeamB);

        //TODO: explain "2" and the game logic
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
     * Reset scores.
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
     * Displays the games won for Team A/Player A.
     */
    public void displayGamesForTeamA(int games) {
        TextView gamesView = findViewById(R.id.games_player_a);
        gamesView.setText(String.valueOf(games));
    }

    /**
     * Displays the games won for Team B/Player B.
     */
    public void displayGamesForTeamB(int games) {
        TextView gamesView = findViewById(R.id.games_player_b);
        gamesView.setText(String.valueOf(games));
    }

    /**
     * Displays the sets won for Team A/Player A.
     */
    public void displaySetsForTeamA(int sets) {
        TextView setsView = findViewById(R.id.sets_player_a);
        setsView.setText(String.valueOf(sets));
    }

    /**
     * Displays the sets won for Team B/Player B.
     */
    public void displaySetsForTeamB(int sets) {
        TextView setsView = findViewById(R.id.sets_player_b);
        setsView.setText(String.valueOf(sets));
    }



    /**
     * Displays advantage(ADV) for Team A/Player A.
     */
    public void displayAdvantageScoreTeamA() {
        TextView advantageView = findViewById(R.id.points_player_a);
        advantageView.setText(R.string.advantage);
    }

    /**
     * Displays advantage(ADV) for Team B/Player B.
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

