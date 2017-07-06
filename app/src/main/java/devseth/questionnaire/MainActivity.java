package devseth.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnExport = (Button) findViewById(R.id.btnExport);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QuestionScreen.class);
                startActivity(i);

            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler handler = new DBHandler(getApplicationContext());
                handler.writeToCSV();
            }
        });


    }

}
