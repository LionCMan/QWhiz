package com.example.android.qwhiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        // Call display Result on creation
        displayResult();
    }

    // Called to determine which image and text to display based on quiz score
    public void displayResult() {
        getScore();
        if (getScore() == 4){
            ImageView sheldon = (ImageView)findViewById(R.id.sheldonImage);
            sheldon.setImageResource(R.drawable.happysheldon);
            TextView perfectScore = (TextView)findViewById(R.id.resultText);
            perfectScore.setText(R.string.perfectScore);
        }
    }

    // Called when the user taps the try Again button to switch Activity
    public void tryAgain(View view){
        Intent tryAgain = new Intent(this, MainActivity.class);
        if (tryAgain.resolveActivity(getPackageManager()) != null) {
            startActivity(tryAgain);
            finish(); }
    }

    //Gets score from activity_main.xml
    public int getScore() {
        Bundle extras = getIntent().getExtras();
        return extras.getInt("id_score");
    }
}
