package com.example.android.qwhiz;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.android.qwhiz.R.id.questionCard1;
import static com.example.android.qwhiz.R.id.questionCard2;
import static com.example.android.qwhiz.R.id.questionCard3;
import static com.example.android.qwhiz.R.id.questionCard4;
import static com.example.android.qwhiz.R.string.question1;

@SuppressWarnings("WrongConstant")
public class MainActivity extends AppCompatActivity {

    View mCard1, mCard2, mCard3, mCard4;
    RadioButton answer1o3, answer3o4;
    EditText question2;
    String answer2;
    CheckBox answer4o1, answer4o2, answer4o3, answer4o4;
    TextView status;
    Button grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCard1 = findViewById(questionCard1);
        mCard2 = findViewById(questionCard2);
        mCard3 = findViewById(questionCard3);
        mCard4 = findViewById(questionCard4);
        mCard1.setVisibility(View.VISIBLE);
        mCard2.setVisibility(View.GONE);
        mCard3.setVisibility(View.GONE);
        mCard4.setVisibility(View.GONE);
        status = (TextView) findViewById(R.id.questionNumber);
        grade = (Button) findViewById(R.id.submit);
        answer1o3 = (RadioButton)findViewById(R.id.q1o3);
        question2 = (EditText)findViewById(R.id.editAnswer);
        answer3o4 = (RadioButton)findViewById(R.id.q3o4);
        answer4o1 = (CheckBox)findViewById(R.id.q4o1);
        answer4o2 = (CheckBox)findViewById(R.id.q4o2);
        answer4o3 = (CheckBox)findViewById(R.id.q4o3);
        answer4o4 = (CheckBox)findViewById(R.id.q4o4);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) { 
        outState.putInt("CARDONE", mCard1.getVisibility());
        outState.putInt("CARDTWO", mCard2.getVisibility());
        outState.putInt("CARDTHREE", mCard3.getVisibility());
        outState.putInt("CARDFOUR", mCard4.getVisibility());
        outState.putCharSequence("QUESTIONNUM", status.getText());
        outState.putCharSequence("BUTTONTEXT", grade.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCard1.setVisibility(savedInstanceState.getInt("CARDONE"));
        mCard2.setVisibility(savedInstanceState.getInt("CARDTWO"));
        mCard3.setVisibility(savedInstanceState.getInt("CARDTHREE"));
        mCard4.setVisibility(savedInstanceState.getInt("CARDFOUR"));
        status.setText(savedInstanceState.getCharSequence("QUESTIONNUM"));
        grade.setText(savedInstanceState.getCharSequence("BUTTONTEXT"));
    }


    public void submitQuestion(View view){
        if (mCard1.getVisibility() == View.VISIBLE) { toQuestion2(); }
        else if (mCard2.getVisibility() == View.VISIBLE) { toQuestion3(); }
        else if (mCard3.getVisibility() == View.VISIBLE) { toQuestion4(); }
        // Calculates the quiz score, shows Toast of score when grade button is clicked, Starts new activity
        else {
            Toast.makeText(getApplicationContext(),toastMessage(),Toast.LENGTH_SHORT).show();
            completeActivity();
        }
    }

    public void lastQuestion(View view){
        if (mCard4.getVisibility() == View.VISIBLE) { toQuestion3(); }
        else if (mCard3.getVisibility() == View.VISIBLE) { toQuestion2(); }
        else if (mCard2.getVisibility() == View.VISIBLE) { toQuestion1(); }
    }

    // Switches to question card 1 & updates question number TextView
    public void toQuestion1(){
        mCard2.setVisibility(View.GONE);
        mCard1.setVisibility(View.VISIBLE);
        status.setText(R.string.questionNo1);
    }

    // Switches to question card 2 & updates question number TextView
    public void toQuestion2(){
        mCard1.setVisibility(View.GONE);
        mCard2.setVisibility(View.VISIBLE);
        mCard3.setVisibility(View.GONE);
        status.setText(R.string.questionNo2);
    }

    // Switches to question card 3 & updates Question Number TextView & grade button text
    public void toQuestion3(){
        mCard2.setVisibility(View.GONE);
        mCard3.setVisibility(View.VISIBLE);
        mCard4.setVisibility(View.GONE);
        status.setText(R.string.questionNo3);
        grade.setText(R.string.submit);
        answer2 = question2.getText().toString();
    }

    // Switches to question card 4 & updates question number TextView & submit button text
    public void toQuestion4(){
        mCard3.setVisibility(View.GONE);
        mCard4.setVisibility(View.VISIBLE);
        status.setText(R.string.questionNo4);
        grade.setText(R.string.grade);
    }

    // Method for starting new activity
    public void completeActivity(){
        Intent scoreValue = new Intent(this, CompleteActivity.class);
        scoreValue.putExtra("id_score", calculateScore());
        if (scoreValue.resolveActivity(getPackageManager()) != null) {
            startActivity(scoreValue);
            finish(); }
    }

    // Method for calculating correct score and toast message
    private String toastMessage() {
        String questionsRight = Integer.toString(calculateScore());
        return getString(R.string.youScore) + questionsRight + getString(R.string.outOf);
    }

    // Method for calculating quiz score
    public int calculateScore(){
        int score = 0;
        if (answer1o3.isChecked()){ score++; }
        if (answer2.equalsIgnoreCase("Neil Armstrong")) { score++; }
        else if (answer2.equalsIgnoreCase("Neil Armstrong ")){ score++; }
        if (answer3o4.isChecked()){ score++; }
        if (answer4o1.isChecked() && answer4o2.isChecked() &&
                answer4o3.isChecked() && answer4o4.isChecked()){ score++; }
        return score;
    }
}
