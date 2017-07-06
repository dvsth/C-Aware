package devseth.questionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Dev Seth on 6/28/2017.
 */

public class DBHandler extends SQLiteOpenHelper {


    //database and table info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SurveyRecords";
    private static final String TABLE_RESPONSES = "responses";
    //identifier - primary key
    private static final String KEY_ID = "id";
    //demographics
    private static final String DEM_AGE = "age";
    private static final String DEM_SEX = "sex";

    //responses - each column stores a value for one response - 1 for yes, 0 for no
    private static final String QUES_1 = "QUES_1";
    private static final String QUES_2 = "QUES_2";
    private static final String QUES_3 = "QUES_3";
    private static final String QUES_4 = "QUES_4";
    private static final String QUES_5 = "QUES_5";
    private static final String QUES_6 = "QUES_6";
    private static final String QUES_7 = "QUES_7";
    private static final String QUES_8 = "QUES_8";
    private static final String QUES_9 = "QUES_9";
    private static final String QUES_10 = "QUES_10";
    //create a reference to the database we are handling
    private static SQLiteDatabase database;
    private static int counter;
    private static String[] QUESTION_ARRAY;
    //quelle heure est-il? wie viel uhr is es?
    private String timestamp;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        //set reference to database we are handling
        database = db;

        //starting the primary key field from 0;
        counter = 0;

        //create the table with relevant columns
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_RESPONSES + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEM_AGE + " INTEGER,"
                        + DEM_SEX + " TEXT, " + QUES_1 + " TEXT, " + QUES_2 + " TEXT, " +
                        QUES_3 + " TEXT, " + QUES_4 + " TEXT, " + QUES_5 + " TEXT, " + QUES_6 +
                        " TEXT, " + QUES_7 + " TEXT, " + QUES_8 + " TEXT, " + QUES_9 + " TEXT, "
                        + QUES_10 + " TEXT"
                        + ")";
        database.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);

        // Create tables again
        onCreate(db);
    }

    public void addSurveyResponse(String[] inputResponses) {

        //initialize array containing all questions
        QUESTION_ARRAY = new String[]{DEM_AGE, DEM_SEX, QUES_1, QUES_2, QUES_3,
                QUES_4, QUES_5, QUES_6, QUES_7, QUES_8, QUES_9, QUES_10};

        ContentValues values = new ContentValues();
        for (int i = 0; i < 12; i++) {
            values.put(QUESTION_ARRAY[i], inputResponses[i]);


        }

        getWritableDatabase().insert(TABLE_RESPONSES, null, values);

        getWritableDatabase().close();

    }

    public void writeToCSV() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        timestamp = dateFormat.format(new Date());

        File exportFolder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "SurveyDatabase");


        if (!exportFolder.exists()) {
            exportFolder.mkdir();


        }

        File exportCSV;
        PrintWriter writer;


        try {

            exportCSV = new File(exportFolder, "export" + timestamp + ".txt");


            //if (!exportCSV.exists()) {
            exportCSV.createNewFile();
            //}

            writer = new PrintWriter(new FileWriter(exportCSV));

            SQLiteDatabase db = getReadableDatabase();                           //open the database for reading

            Cursor curCSV = db.rawQuery("Select * from responses", null);      //feed the table to cursor
            Log.d(null, "row count is: " + curCSV.getCount());

            //write header of CSV
            writer.println("key, age, sex, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10");


            int count = 0;
            while (curCSV.moveToNext()) {


                String record = "";

                for (int i = 0; i < 12; i++) {
                    record = record.concat(curCSV.getString(i) + ",");
                }

                record = record.concat(curCSV.getString(12));

                writer.println(record);
                count++;
                Log.wtf(null, "value of record is:" + record);
                Log.d(null, "" + count);

            }


            writer.close();
            curCSV.close();
            db.close();


        } catch (Exception exc) {

            Log.e(TAG, "writeToCSV: " + exc.toString(), null);
            exc.printStackTrace();


        }


    }
}