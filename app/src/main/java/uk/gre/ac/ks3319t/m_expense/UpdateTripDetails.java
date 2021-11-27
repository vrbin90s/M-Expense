package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateTripDetails extends AppCompatActivity {

    private boolean tripDateOrReturnDate = true;
    public static boolean dateSelected = false;


    DatabaseHelper dbHelper;
    EditText upTripName, upDestination, upTripDate, upReturnDate, upDescription, upTransportation;
    Button updateButton;
    RadioButton radioButtonYes;
    RadioButton radioButtonNo;
    RadioGroup radioGroup;
    List<TripDetails> tripDetailsList;
    String tripName, destination, tripDate, returnDate, description, transportation;
    SQLiteDatabase db;




    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip_details);

        CreateNewTrip.newTripActivity = false;

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        upTripName = (EditText) findViewById(R.id.update_tripNameInputField);
        upDestination = (EditText) findViewById(R.id.update_destinationInputField);
        upTripDate = (EditText) findViewById(R.id.selectTripDate);
        upReturnDate = (EditText) findViewById(R.id.selectReturnDate);
        upDescription = (EditText) findViewById(R.id.update_descriptionInputField);
        upTransportation = (EditText) findViewById(R.id.update_transportationInputField);
        radioGroup = findViewById(R.id.riskAssessmentRadioGroup);

        radioButtonYes = (RadioButton) findViewById(R.id.update_radioButton_Yes);
        radioButtonNo = (RadioButton) findViewById(R.id.update_radioButton_No);

        updateButton = (Button) findViewById(R.id.updateButton);

        EditText dataPickerTripDate = findViewById(R.id.selectTripDate);
        EditText dataPickerReturnDate = findViewById(R.id.selectReturnDate);

        DialogFragment datePicker = new CreateNewTrip.DatePickerFragment();

        dataPickerTripDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(),"datePicker");
                tripDateOrReturnDate = true;
            }
        });

        dataPickerReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(),"datePicker");
                tripDateOrReturnDate = false;
            }
        });


        final int rowID = getIntent().getIntExtra("tripID", -1);

        Cursor c1 = db.query("trip_details",null, "trip_id" + " = " + rowID, null,null,null,null);
        tripDetailsList = new ArrayList<TripDetails>();
        tripDetailsList.clear();

        if(c1 != null && c1.getCount() != 0) {
            while (c1.moveToNext()) {
                upTripName.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.TRIP_NAME_COLUMN)));
                upDestination.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.TRIP_DESTINATION_COLUMN)));
                upTripDate.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.TRIP_DATE_COLUMN)));
                upReturnDate.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.RETURN_DATE_COLUMN)));
                upDescription.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.TRIP_DESCRIPTION_COLUMN)));
                upTransportation.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.TRIP_TRANSPORTATION_COLUMN)));
                String radioButtonValue = c1.getString(c1.getColumnIndex(DatabaseHelper.REQUIRES_RISK_ASSESSMENT_COLUMN));

                if (radioButtonValue.length() == 3){
                    radioButtonYes.setChecked(true);
                } else {
                    radioButtonNo.setChecked(true);
                }

            }

        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripName = upTripName.getText().toString();
                destination = upDestination.getText().toString();
                tripDate = upTripDate.getText().toString();
                returnDate = upReturnDate.getText().toString();
                description = upDescription.getText().toString();
                transportation = upTransportation.getText().toString();
                RadioGroup radioGroup = findViewById(R.id.riskAssessmentRadioGroup);
                RadioButton radioButtonTxt = findViewById(radioGroup.getCheckedRadioButtonId());
                String riskAssessment = radioButtonTxt.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.TRIP_NAME_COLUMN, tripName);
                values.put(DatabaseHelper.TRIP_DESTINATION_COLUMN, destination);
                values.put(DatabaseHelper.TRIP_DATE_COLUMN, tripDate);
                values.put(DatabaseHelper.RETURN_DATE_COLUMN, returnDate);
                values.put(DatabaseHelper.TRIP_DESCRIPTION_COLUMN, description);
                values.put(DatabaseHelper.TRIP_TRANSPORTATION_COLUMN, transportation);
                values.put(DatabaseHelper.REQUIRES_RISK_ASSESSMENT_COLUMN, riskAssessment);

                int updateId = db.update("trip_details", values, "trip_id" + " = " + rowID, null);

                if (updateId != -1) {
                    Toast.makeText(UpdateTripDetails.this, "Trip Details Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateTripDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateTripDetails.this, "Trip Detail Update Failed",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    //
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Reference to the risk assessment label to remove error message once radio btn is checked.
        TextView riskAssessment = (TextView) findViewById(R.id.update_riskAssessment_label);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.update_radioButton_Yes:
                if (checked)
                    riskAssessment.setError(null);
                break;
            case R.id.update_radioButton_No:
                if (checked) {
                    riskAssessment.setError(null);
                }
                break;
        }
    }

    // Method that updates selected dates for the
    // relevant EditText fields - trip & return input fields.
    public void updateTD(LocalDate td) {

        if (tripDateOrReturnDate == true) {
            EditText tripDate = findViewById(R.id.selectTripDate);
            tripDate.setText(td.toString());
            tripDate.setError(null);
        }
        if (tripDateOrReturnDate == false){
            EditText returnDate = findViewById(R.id.selectReturnDate);
            returnDate.setText(td.toString());
            returnDate.setError(null);
        }
        dateSelected = true;

    }
}