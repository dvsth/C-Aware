package devseth.questionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RESPONSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DEM_AGE + " INTEGER,"
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

        for (int i = 3; i < inputResponses.length; i++) {
            concatResponses += inputResponses[i];
        }

        ContentValues values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(DEM_AGE, inputResponses[1]);
        values.put(DEM_SEX, inputResponses[2]);
        values.put(RES_ALL, concatResponses);
        database.insert(TABLE_RESPONSES, null, values);

    }


}
