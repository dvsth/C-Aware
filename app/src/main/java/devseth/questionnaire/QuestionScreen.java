package devseth.questionnaire;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
    Button btnRevert;                                   //the revert button
    RadioButton btnFemale;                              //are you a girl?
    RadioButton btnMale;                                //are you a boy?
    RadioButton btnHindi;                               //language of survey administration
    RadioButton btnEnglish;                             //language of survey administration
    Spinner spnEducation;                               //list of educational standards
    TextView txtEducationLabel;                         //take my info bro - I'm a tag
    TextView txtLanguage;                               //I'm also a tag bro - I tell what my RadioButtons do
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
        txtEducationLabel = (TextView) findViewById(R.id.EducationLabel);
        txtLanguage = (TextView) findViewById(R.id.txtLanguage);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnFemale = (RadioButton) findViewById(R.id.rbFemale);
        btnMale = (RadioButton) findViewById(R.id.rbMale);
        btnHindi = (RadioButton) findViewById(R.id.rbHindi);
        btnEnglish = (RadioButton) findViewById(R.id.rbEnglish);
        btnGo.setOnClickListener(this);
        spnEducation = (Spinner) findViewById(R.id.spnEducation);
        background = findViewById(R.id.background);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
        btnRevert = (Button) findViewById(R.id.btnRevert);
        btnRevert.setOnClickListener(this);

        questionFeeder = new QuestionFeeder();

        btnEnglish.setVisibility(View.VISIBLE);
        btnEnglish.setEnabled(true);
        btnHindi.setEnabled(true);
        btnHindi.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapterSpnEducation = ArrayAdapter.createFromResource(this,
                R.array.education_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterSpnEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnEducation.setAdapter(adapterSpnEducation);


        responses = new String[28];
        String blank = "";
        for (int i = 0; i < responses.length; i++)
            responses[i] = blank;

        questionIndex = 3;

        isGoOver = false;

        //hide question layout at init
        txtQues.setVisibility(View.INVISIBLE);
        btnYes.setVisibility(View.INVISIBLE);
        btnNo.setVisibility(View.INVISIBLE);
        btnRevert.setVisibility(View.INVISIBLE);

        //Ignition for animation
        bgAnimator();


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

            if (questionIndex == 27)
                isLast = true;

            txtQues.setText(questionFeeder.nextQuestion());

            Log.d(null, "Reached first if");
            Log.d(null, "value of questionIndex is:" + questionIndex);
        }

        //if (!isLast && txtQues.getText().equals("Survey Complete!")) {


        //!!!TEST ONLY!!!
        //String test = "teststring for concated responses";
        //for(int c = 0; c < responses.length; c++)
        //  test = test.concat(responses[c]);
        //txtQues.setText(test);


        switch (v.getId()) {
            case R.id.rbMale:
            case R.id.rbFemale:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
            case R.id.btnNo:
                responses[questionIndex] = "1";

                Log.d(null, "Value of responses at " + questionIndex + " is: " + responses[questionIndex]);
                if (isLast) {
                    isGoOver = false;
                    disabler();
                }
                break;
            case R.id.btnYes:
                responses[questionIndex] = "2";
                Log.d(null, "Value of responses at " + questionIndex + " is: " + responses[questionIndex]);

                if (isLast) {
                    isGoOver = false;
                    disabler();
                }
                break;

            case R.id.btnGo:

                if (btnEnglish.isChecked())
                    questionFeeder.setLanguage(1);
                else
                    questionFeeder.setLanguage(0);

                String edu = "";
                switch (spnEducation.getSelectedItemPosition()) {
                    case 0:
                        edu = "Primary";
                        break;
                    case 1:
                        edu = "Secondary";
                        break;
                    case 2:
                        edu = "Bachelor's";
                        break;
                    case 3:
                        edu = "Master's";
                        break;
                    case 4:
                        edu = "No Formal Education";
                        break;


                }

                responses[2] = edu;

                String name = txtName.getText().toString();
                responses[3] = name;

                isGoOver = true;

                //Setting up the show backstage before fiddling with the lights
                //Set the question in the textview
                txtQues.setText(questionFeeder.nextQuestion());

                //lights on
                //show question layout, hide name, age input
                txtQues.setVisibility(View.VISIBLE);
                btnNo.setVisibility(View.VISIBLE);
                btnYes.setVisibility(View.VISIBLE);

                //unneeded lights off
                btnGo.setVisibility(View.INVISIBLE);
                txtAge.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.INVISIBLE);
                btnMale.setVisibility(View.INVISIBLE);
                btnFemale.setVisibility(View.INVISIBLE);
                btnHindi.setVisibility(View.INVISIBLE);
                btnEnglish.setVisibility(View.INVISIBLE);
                txtEducationLabel.setVisibility(View.INVISIBLE);
                spnEducation.setVisibility(View.INVISIBLE);
                txtLanguage.setVisibility(View.INVISIBLE);

                //add input fields into row array
                responses[0] = txtAge.getText().toString();

                if (btnMale.isChecked())
                    responses[1] = "male";
                else
                    responses[1] = "female";

                break;
            case R.id.btnRevert:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
        }

    }

    private void disabler() {

        btnYes.setEnabled(false);                           //disable buttons
        btnNo.setEnabled(false);
        btnNo.setVisibility(View.GONE);
        btnYes.setVisibility(View.GONE);
        spnEducation.setVisibility(View.GONE);

        btnRevert.setVisibility(View.VISIBLE);              //enable revert option

        dbhelper.addSurveyResponse(responses);              //add the responses to the db
        dbhelper.writeToCSV();                              //update the csv file
        Log.d(null, "wrote to CSV");

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
