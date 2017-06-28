package devseth.questionnaire;

import android.animation.ValueAnimator;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.view.animation.Animation.REVERSE;

public class QuestionScreen extends AppCompatActivity {

    TextView txtQues;                                   //TextView that displays questions
    View background;                                    //the background of the window
    private QuestionFeeder questionFeeder;              //provides questions
    private SQLiteOpenHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        //Object declarations
        txtQues = (TextView) findViewById(R.id.textView1);
        background = findViewById(R.id.background);
        questionFeeder = new QuestionFeeder();

        //Ignition for animation
        bgAnimator();
        txtQues.setText(questionFeeder.nextQuestion());


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
