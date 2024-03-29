package uk.gre.ac.ks3319t.m_expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Variable storing database name
    private static final String DATABASE_NAME = "MExpense_DB";

    // Variables storing table names
    public static final String TRIP_TABLE_NAME = "trip_details";
    public static final String EXPENSE_TABLE_NAME = "expense_details";

    // Variables for the trip table columns in the database
    public static final String TRIP_ID_COLUMN = "trip_id";
    public static final String TRIP_NAME_COLUMN = "name";
    public static final String TRIP_DESTINATION_COLUMN = "destination";
    public static final String TRIP_DATE_COLUMN = "trip_date";
    public static final String RETURN_DATE_COLUMN = "return_date";
    public static final String TRIP_DESCRIPTION_COLUMN = "description";
    public static final String TRIP_TRANSPORTATION_COLUMN = "transportation";
    public static final String REQUIRES_RISK_ASSESSMENT_COLUMN = "risk_assessment";

    // Variables for the expenses table columns in the database
    public static final String EXPENSE_ID_COLUMN = "expense_id";
    public static final String EXPENSE_TYPE_COLUMN = "expense_type";
    public static final String EXPENSE_AMOUNT_COLUMN = "expense_amount";
    public static final String EXPENSE_TIME_COLUMN = "expense_time";
    public static final String EXPENSE_COMMENTS_COLUMN = "expense_comments";
    public static final String EXPENSE_TRIP_ID_COLUMN = "expense_trip_id";

    // Reference to SQLiteDatabase
    private SQLiteDatabase database;

    // Reference to trip details data list
    private ArrayList<TripDetails> tripDetailsList = new ArrayList<TripDetails>();


    // Create table for trip details
    private static final String CREATE_TRIP_DETAILS_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            TRIP_TABLE_NAME,
            TRIP_ID_COLUMN,
            TRIP_NAME_COLUMN,
            TRIP_DESTINATION_COLUMN,
            TRIP_DATE_COLUMN,
            RETURN_DATE_COLUMN,
            TRIP_DESCRIPTION_COLUMN,
            TRIP_TRANSPORTATION_COLUMN,
            REQUIRES_RISK_ASSESSMENT_COLUMN);

    // Create table for expense details
    private static final String CREATE_EXPENSE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            EXPENSE_TABLE_NAME,
            EXPENSE_ID_COLUMN,
            EXPENSE_TYPE_COLUMN,
            EXPENSE_AMOUNT_COLUMN,
            EXPENSE_TIME_COLUMN,
            EXPENSE_COMMENTS_COLUMN,
            EXPENSE_TRIP_ID_COLUMN);


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create new tables
        db.execSQL(CREATE_TRIP_DETAILS_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Check whether table already exists in the database
        // if yes - Drop the table.
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);

        onCreate(db);
    }

    // Inserting data into trip table
    public long insertDetails(String name, String destination, String tripDate, String returnDate, String description, String transportation, String riskAssessment) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_NAME_COLUMN, name);
        rowValues.put(TRIP_DESTINATION_COLUMN, destination);
        rowValues.put(TRIP_DATE_COLUMN, tripDate);
        rowValues.put(RETURN_DATE_COLUMN, returnDate);
        rowValues.put(TRIP_DESCRIPTION_COLUMN, description);
        rowValues.put(TRIP_TRANSPORTATION_COLUMN, transportation);
        rowValues.put(REQUIRES_RISK_ASSESSMENT_COLUMN, riskAssessment);

        return database.insertOrThrow(TRIP_TABLE_NAME, null, rowValues);
    }

    // Inserting data into expense table
    public long insertExpenseDetails(String exp_type, String exp_amount, String exp_time, String exp_description, long exp_trip_id) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(EXPENSE_TYPE_COLUMN, exp_type);
        rowValues.put(EXPENSE_AMOUNT_COLUMN, exp_amount);
        rowValues.put(EXPENSE_TIME_COLUMN, exp_time);
        rowValues.put(EXPENSE_COMMENTS_COLUMN, exp_description);
        rowValues.put(EXPENSE_TRIP_ID_COLUMN, exp_trip_id);

        return database.insertOrThrow(EXPENSE_TABLE_NAME, null, rowValues);
    }


    // Retrieving data from trip_details table to populate trip cards
    public List<TripDetails> getTripDetails() {

        List<TripDetails> tripDetails = new ArrayList<>();

        Cursor results = database.query("trip_details", new String[]{"trip_id", "name", "destination", "description", "trip_date", "return_date", "risk_assessment"},
                null, null, null, null, "name");


        while (results.moveToNext()) {
            int id = results.getInt(0);
            String title = results.getString(1);
            String description = results.getString(3);
            String date = results.getString(4);

            TripDetails data = new TripDetails(id, title, description, date);
            tripDetails.add(data);
        }
        return tripDetails;

    }

    // Retrieving data from expense_details table to populate expense cards
    public List<ExpenseDetails> getExpensesDetails() {

        List<ExpenseDetails> expenseDetails = new ArrayList<>();


        Cursor results = database.query(EXPENSE_TABLE_NAME, new String[]{"expense_id", "expense_type", "expense_amount", "expense_time", "expense_comments", "expense_trip_id"},
                "expense_trip_id" + " = " + DisplayTripDetails.expense_trip_id, null, null, null, null);

        while (results.moveToNext()) {
            int id = results.getInt(0);
            String expName = results.getString(1);
            String expAmount = results.getString(2);
            String expTime = results.getString(3);
            String expComment = results.getString(4);
            long exp_trip_id = results.getInt(5);

            ExpenseDetails data = new ExpenseDetails(id, expName, expAmount, expTime, expComment, exp_trip_id);
            expenseDetails.add(data);
        }
        return expenseDetails;

    }

}