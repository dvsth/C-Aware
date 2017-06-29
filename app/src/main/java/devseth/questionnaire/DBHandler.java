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

import static android.content.ContentValues.TAG;

/**
 * Created by Dev Seth on 6/28/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    //create a reference to the database we are handling
    private SQLiteDatabase database;

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
    private static final String RES_ALL = "responseall";

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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RESPONSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEM_AGE + " INTEGER,"
                + DEM_SEX + " TEXT," + RES_ALL + " TEXT" + ")";
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

        String concatResponses = "";

        for (int i = 0; i < inputResponses.length; i++) {
            concatResponses += inputResponses[i];
        }

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, (int) (100*Math.random()));
        values.put(DEM_AGE, "52");
        values.put(DEM_SEX, "Male");
        values.put(RES_ALL, concatResponses);
        getWritableDatabase().insert(TABLE_RESPONSES, null, values);

    }

    public String displayResponse() {

        Cursor row = getReadableDatabase().rawQuery("SELECT id FROM " + TABLE_RESPONSES + " WHERE age ='52';", null);
        row.moveToFirst();
        String returnString = row.getString(0);
        row.close();
        writeToCSV();
        return returnString;

    }

    public void writeToCSV() {



        File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File exportCSV;
        PrintWriter writer = null;


        try {
            exportCSV = new File(exportDir, "MyCSVFile.txt");
            exportCSV.createNewFile();
            writer = new PrintWriter(new FileWriter(exportCSV));

            SQLiteDatabase db = this.getReadableDatabase(); //open the database for reading

            /**Let's read the first table of the database.
             * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
             * containing all records of the table (all fields).
             * The code of this class is omitted for brevity.
             */
            Cursor curCSV = db.rawQuery("select * from responses;", null);

            //Write the name of the table and the name of the columns (comma separated values) in the .csv file.

            writer.println("key, age, sex, responseall");

            while (curCSV.moveToNext()) {
                String key = curCSV.getString(0);
                String age = curCSV.getString(1);
                String sex = curCSV.getString(2);
                String responseall = curCSV.getString(3);


                String record = key + "," + age + "," + sex + "," + responseall + ",";
                writer.println(record);
                curCSV.close();
                db.close();
            }

        } catch (Exception exc) {

            Log.e(TAG, "writeToCSV: " + exc.toString(), null);

        }

    }


}


