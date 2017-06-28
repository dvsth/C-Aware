package devseth.questionnaire;

import android.animation.ValueAnimator;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

import static android.view.animation.Animation.REVERSE;

public class QuestionScreen extends AppCompatActivity implements View.OnClickListener {

    TextView txtQues;                                   //TextView that displays questions
    View background;                                    //the background of the window
    Button btnYes;                                      //the yes button
    Button btnNo;                                       //the no button
    Button btnDisplay;
    private QuestionFeeder questionFeeder;              //provides questions
    private DBHandler dbhelper;                         //the DBHandler object which will manage db
    private String[] responses;                         //stores responses
    private int questionIndex;                          //what question are we on?


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        //Object declarations
        txtQues = (TextView) findViewById(R.id.textView1);
        background = findViewById(R.id.background);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
        btnDisplay = (Button) findViewById(R.id.recordDisplay);
        btnDisplay.setOnClickListener(this);
        questionFeeder = new QuestionFeeder();
        responses = new String[15];

        questionIndex = 1;

        //Ignition for animation
        bgAnimator();

        //Set the question in the textview
        txtQues.setText(questionFeeder.nextQuestion());

        dbhelper = new DBHandler(this);

    }

    public void onClick(View v) {

        if (questionIndex < 9)
            txtQues.setText(questionFeeder.nextQuestion());

        if (questionIndex == 9) {
            btnYes.setEnabled(false);
            btnNo.setEnabled(false);
            dbhelper.addSurveyResponse(responses);
            Log.d(null, "onClick: the response was added to database");

        } else {
            switch (v.getId()) {

                case R.id.btnNo:
                    responses[++questionIndex] = "0";
                    break;
                case R.id.btnYes:
                    responses[++questionIndex] = "1";
                    break;
                case R.id.recordDisplay:
                    txtQues.setText("ABCDEFG");
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
