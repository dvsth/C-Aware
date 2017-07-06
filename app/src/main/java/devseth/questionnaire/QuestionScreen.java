package devseth.questionnaire;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuestionScreen extends AppCompatActivity implements View.OnClickListener {

    //permissions
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    TextView txtQues;                                   //TextView that displays questions
    EditText txtName;                                   //input for name
    EditText txtAge;                                    //input for age
    View background;                                    //the background of the window
    Button btnYes;                                      //the yes button
    Button btnNo;                                       //the no button
    Button btnGo;                                       //the go button
    RadioButton btnFemale;                              //are you a girl?
    RadioButton btnMale;                                //are you a boy?
    private QuestionFeeder questionFeeder;              //provides questions
    private DBHandler dbhelper;                         //the DBHandler object which will manage db
    private String[] responses;                         //stores responses
    private int questionIndex;                          //what question are we on?
    private boolean isGoOver;                           //are we done with basic details?

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        //Object declarations
        txtQues = (TextView) findViewById(R.id.textView1);
        txtName = (EditText) findViewById(R.id.txtName);
        txtAge = (EditText) findViewById(R.id.txtAge);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnFemale = (RadioButton) findViewById(R.id.rbFemale);
        btnMale = (RadioButton) findViewById(R.id.rbMale);
        btnGo.setOnClickListener(this);
        background = findViewById(R.id.background);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
        questionFeeder = new QuestionFeeder();

        responses = new String[12];
        String blank = "";
        for (int i = 0; i < responses.length; i++)
            responses[i] = blank;

        questionIndex = 1;

        isGoOver = false;

        //hide question layout at init
        txtQues.setVisibility(View.INVISIBLE);
        btnYes.setVisibility(View.INVISIBLE);
        btnNo.setVisibility(View.INVISIBLE);

        //Ignition for animation
        bgAnimator();

        //Set the question in the textview
        txtQues.setText(questionFeeder.nextQuestion());

        dbhelper = new DBHandler(getApplicationContext());

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }

    }

    public void onClick(View v) {

        boolean isLast = false;

        if (isGoOver) {
            questionIndex++;

            if (questionIndex == 11)
                isLast = true;

            txtQues.setText(questionFeeder.nextQuestion());

            Log.d(null, "Reached first if");
            Log.d(null, "value of questionIndex is:" + questionIndex);
        }

        if (!isLast && txtQues.getText().equals("Survey Complete!")) {

            btnYes.setEnabled(false);                           //disable buttons
            btnNo.setEnabled(false);
            dbhelper.addSurveyResponse(responses);              //add the responses to the db
            dbhelper.writeToCSV();                              //update the csv file
            Log.d(null, "wrote to CSV");

            //!!!TEST ONLY!!!
            //String test = "teststring for concated responses";
            //for(int c = 0; c < responses.length; c++)
            //  test = test.concat(responses[c]);
            //txtQues.setText(test);

        } else {
            switch (v.getId()) {

                case R.id.btnNo:
                    responses[questionIndex] = "0";

                    Log.d(null, "Value of responses at " + questionIndex + " is: " + responses[questionIndex]);
                    if (isLast) {
                        isGoOver = false;

                    }
                    break;
                case R.id.btnYes:
                    responses[questionIndex] = "1";
                    Log.d(null, "Value of responses at " + questionIndex + " is: " + responses[questionIndex]);

                    if (isLast) {
                        isGoOver = false;

                    }
                    break;

                case R.id.btnGo:

                    isGoOver = true;

                    //show question layout, hide name, age input
                    txtQues.setVisibility(View.VISIBLE);
                    btnNo.setVisibility(View.VISIBLE);
                    btnYes.setVisibility(View.VISIBLE);
                    btnGo.setVisibility(View.INVISIBLE);
                    txtAge.setVisibility(View.INVISIBLE);
                    txtName.setVisibility(View.INVISIBLE);
                    btnMale.setVisibility(View.INVISIBLE);
                    btnFemale.setVisibility(View.INVISIBLE);

                    //add input fields into row array
                    responses[0] = txtAge.getText().toString();

                    if (btnMale.isChecked())
                        responses[1] = "male";
                    else
                        responses[1] = "female";


            }
        }

    }

    private void bgAnimator() {

        //Colors the animation cycles through
        //PaleVioletRed, Light Green, Soft Blue, Faded Yellow, PaleVioletRed
        ValueAnimator anim = ValueAnimator.ofArgb(Color.rgb(219, 112, 147), Color.rgb(169, 210, 106),
                Color.rgb(186, 228, 240), Color.rgb(255, 255, 213), Color.rgb(219, 112, 147));

        //create the animator
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                background.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });

        //animator settings
        anim.setRepeatMode(ValueAnimator.RESTART);      //restart anim sequence once it ends
        anim.setRepeatCount(ValueAnimator.INFINITE);    //keep playing it... to infinity and beyond!
        anim.setDuration(25000);                        //play duration 25 seconds
        anim.start();                                   //let the animations begin!
    }

}
