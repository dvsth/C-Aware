package devseth.questionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import static android.content.ContentValues.TAG;

/**
 * Created by Dev Seth on 6/28/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    //create a reference to the database we are handling
    private static SQLiteDatabase database;

    //database and table info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SurveyRecords";
    private static final String TABLE_RESPONSES = "responses";

    //identifier - primary key
    private static final String KEY_ID = "id";
    private static int counter;

    //demographics
    private static final String DEM_AGE = "age";
    private static final String DEM_SEX = "sex";

    //responses - currently has one string field where all answers are concatenated
    //TODO - add 10 columns each corresponding to a response
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
    private static String[] QUESTION_ARRAY;

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
        QUESTION_ARRAY = new String[]{DEM_AGE, DEM_SEX, QUES_1, QUES_2, QUES_3, QUES_4, QUES_5, QUES_6, QUES_7, QUES_8, QUES_9, QUES_10};

        ContentValues values = new ContentValues();
        for (int i = 0; i < 12; i++) {
            values.put(QUESTION_ARRAY[i], inputResponses[i]);

        }

        getWritableDatabase().insert(TABLE_RESPONSES, null, values);

    }

    public void writeToCSV() {


        File exportFolder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "SurveyDB");

        boolean success = true;

        if (!exportFolder.exists()) {
            success = exportFolder.mkdirs();
        }
        if (success) {


        } else {

        }


        File exportCSV;
        PrintWriter writer;


        try {
            exportCSV = new File(exportFolder, "xyz.txt");
            exportCSV.createNewFile();
            writer = new PrintWriter(new FileWriter(exportCSV));

            SQLiteDatabase db = getReadableDatabase();                           //open the database for reading

            Cursor curCSV = db.rawQuery("select * from responses;", null);      //feed the table to cursor

            writer.println("key, age, sex, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10");                       //write header of CSV


            while (curCSV.moveToNext()) {


                String record = "";

                for (int i = 0; i < 13; i++) {
                    record += curCSV.getString(i) + ",";
                }

                writer.println(record);

            }

            curCSV.close();
            db.close();

        } catch (Exception exc) {

            Log.e(TAG, "writeToCSV: " + exc.toString(), null);

        }

    }

}