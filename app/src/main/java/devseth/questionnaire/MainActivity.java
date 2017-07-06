package devseth.questionnaire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

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
                Intent i = new Intent(getApplicationContext(), QuestionScreen.class);
                startActivity(i);

            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareViaEmail("C-Aware", "C-AwareDatabase.csv");
            }
        });


    }

    private void ShareViaEmail(String folder_name, String file_name) {
     /*   try {
            File Root = Environment.getExternalStorageDirectory();
            String filelocation = Root.getAbsolutePath() + folder_name + "/" + file_name;
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            String message = "File to be shared is " + file_name + ".";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://" + filelocation));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setData(Uri.parse("mailto:dfirespark@gmail.com"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } catch (Exception e) {
            System.out.println("" + e + "occurred while sending mail.");
        }*/

        File filelocation = new File(Environment.getExternalStorageDirectory() + "/" + "C-Aware", "C-AwareDatabase.csv");
        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"devdotseth@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "C-Aware Database Export");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


}
