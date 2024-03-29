package uk.gre.ac.ks3319t.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DisplayTripDetails extends AppCompatActivity {


    // Reference to the toolbar
    Toolbar toolbar;
    // References to the TextView objects
    TextView tripName, destination, tripDate, returnDate, description, transportation, riskAssessment;
    // Variable that stores all trip details list
    List<TripDetails> tripDetailsList;
    // Getting reference to our Database Helper class
    DatabaseHelper dbHelper;
    // Creating variable for our SQLiteDatabase
    SQLiteDatabase db;
    // Reference to our buttons
    Button expensesButton;

    TripDetails tripDetails;

    //References to our layouts
    public LinearLayout tripDetailsLayout;
    public static long expense_trip_id;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trip_details);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Getting our buttons
        expensesButton = (Button) findViewById(R.id.DTD_ExpensesButton);

        // Getting our Layouts
        tripDetailsLayout = (LinearLayout) findViewById(R.id.TripDetails_Layout);


        // Calling custom toolbar
        toolbar = (Toolbar) findViewById(R.id.DTD_CustomToolbar);
        setSupportActionBar(toolbar);

        // Adding default go back button
        // to the toolbar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting TextView items
        tripName = (TextView) findViewById(R.id.DTD_TripNameText);
        destination = (TextView) findViewById(R.id.DTD_DestinationText);
        tripDate = (TextView) findViewById(R.id.DTD_TripDateText);
        returnDate = (TextView) findViewById(R.id.DTD_ReturnDateText);
        description = (TextView) findViewById(R.id.DTD_DescriptionText);
        transportation = (TextView) findViewById(R.id.DTD_TransportationText);
        riskAssessment = (TextView) findViewById(R.id.DTD_RQRiskAssessmentText);

        // Passing selected item ID
        final int rowID = getIntent().getIntExtra("tripID", -1);
        expense_trip_id = rowID;

        // Our cursor calls trip details based on the selected item ID
        Cursor cursor = db.query("trip_details", null, "trip_id" + " = " + rowID, null, null, null, null);
        tripDetailsList = new ArrayList<TripDetails>();
        tripDetailsList.clear();

        // Binds TextView default values with values from the database.
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                tripName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRIP_NAME_COLUMN)));
                destination.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRIP_DESTINATION_COLUMN)));
                tripDate.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRIP_DATE_COLUMN)));
                returnDate.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.RETURN_DATE_COLUMN)));
                description.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRIP_DESCRIPTION_COLUMN)));
                transportation.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRIP_TRANSPORTATION_COLUMN)));
                riskAssessment.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REQUIRES_RISK_ASSESSMENT_COLUMN)));

            }

        }

        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayTripDetails.this, DisplayExpenseDetails.class);
                intent.putExtra("tripID", rowID);
                startActivity(intent);
            }
        });


    }


    // Method that informs system
    // go back to the previous activity once
    // user interacts with the back button.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(DisplayTripDetails.this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}