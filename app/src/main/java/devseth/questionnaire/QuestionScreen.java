package devseth.questionnaire;

import android.animation.ValueAnimator;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

import static android.view.animation.Animation.REVERSE;

public class QuestionScreen extends AppCompatActivity implements View.OnClickListener {

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

        questionIndex = 2;

        isGoOver = false;

        //hide question layout at init
        txtQues.setVisibility(View.INVISIBLE);
        btnYes.setVisibility(View.INVISIBLE);
        btnNo.setVisibility(View.INVISIBLE);

        //Ignition for animation
        bgAnimator();

        //Set the question in the textview
        txtQues.setText(questionFeeder.nextQuestion());

        dbhelper = new DBHandler(this);

    }

    public void onClick(View v) {

        if (isGoOver && questionIndex < 12){
            txtQues.setText(questionFeeder.nextQuestion());
            questionIndex++;
            Log.d(null, "Reached first if");
        }

        if (txtQues.getText().equals("Question 10")) {

            btnYes.setEnabled(false);                           //disable buttons
            btnNo.setEnabled(false);
            dbhelper.addSurveyResponse(responses);              //add the responses to the db
            dbhelper.writeToCSV();                              //update the csv file
            Log.d(null, "wrote to CSV");


        } else {
            switch (v.getId()) {

                case R.id.btnNo:
                    responses[questionIndex] = "0";
                    break;
                case R.id.btnYes:
                    responses[questionIndex] = "1";
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
                    break;


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
