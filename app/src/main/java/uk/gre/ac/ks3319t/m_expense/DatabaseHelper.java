package uk.gre.ac.ks3319t.m_expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trip_details";

    public static final String TRIP_ID_COLUMN = "trip_id";
    public static final String TRIP_NAME_COLUMN = "name";
    public static final String TRIP_DESTINATION_COLUMN = "destination";
    public static final String TRIP_DATE_COLUMN = "trip_date";
    public static final String RETURN_DATE_COLUMN = "return_date";
    public static final String TRIP_DESCRIPTION_COLUMN = "description";
    public static final String TRIP_TRANSPORTATION_COLUMN = "transportation";
    public static final String REQUIRES_RISK_ASSESSMENT_COLUMN = "risk_assessment";

    private SQLiteDatabase database;
    private TripDetails tripDetails;


    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            DATABASE_NAME,
            TRIP_ID_COLUMN,
            TRIP_NAME_COLUMN,
            TRIP_DESTINATION_COLUMN,
            TRIP_DATE_COLUMN,
            RETURN_DATE_COLUMN,
            TRIP_DESCRIPTION_COLUMN,
            TRIP_TRANSPORTATION_COLUMN,
            REQUIRES_RISK_ASSESSMENT_COLUMN);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertDetails(String name, String destination, String tripDate, String returnDate, String description, String transportation, String riskAssessment) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_NAME_COLUMN, name);
        rowValues.put(TRIP_DESTINATION_COLUMN, destination);
        rowValues.put(TRIP_DATE_COLUMN, tripDate);
        rowValues.put(RETURN_DATE_COLUMN, returnDate);
        rowValues.put(TRIP_DESCRIPTION_COLUMN, description);
        rowValues.put(TRIP_TRANSPORTATION_COLUMN, transportation);
        rowValues.put(REQUIRES_RISK_ASSESSMENT_COLUMN, riskAssessment);

        return database.insertOrThrow(DATABASE_NAME, null, rowValues);
    }

    public void deleteTrip(int tripID){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" DELETE FROM " + DATABASE_NAME + " WHERE " + TRIP_ID_COLUMN + "='" + tripID + "'");
    }

    public List<TripDetails> getCardDetails() {

        List<TripDetails> tripDetails = new ArrayList<>();

        Cursor results = database.query("trip_details", new String[] {"trip_id", "name", "destination","description", "trip_date", "return_date", "risk_assessment" },
                null, null, null, null, "name");


        while (results.moveToNext()) {
            int id = results.getInt(0);
            String title = results.getString(1);
            String description = results.getString(3);
            String date = results.getString(5);

            TripDetails data = new TripDetails(id,title,description,date);
            tripDetails.add(data);
        }
        return tripDetails;

    }

    public String getDetails() {
        Cursor results = database.query("trip_details", new String[] {"trip_id", "name", "destination", "trip_date", "return_date", "description", "transportation", "risk_assessment" },
                null, null, null, null, "name");

        String resultText = "";

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            String destination = results.getString(2);
            String tripDate = results.getString(3);
            String returnDate = results.getString(4);
            String description = results.getString(5);
            String transportation = results.getString(6);
            String riskAssessment = results.getString(7);

            resultText += id + " " + name + " " + destination + " " + tripDate + " " + returnDate + " " + description + " " + transportation + " " +riskAssessment + "\n";

            results.moveToNext();
        }

        return resultText;

    }



}