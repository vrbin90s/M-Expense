package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class CreateNewTrip extends AppCompatActivity {

    public static boolean dateSelected = false;

    private boolean tripDateOrReturnDate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        TextView riskAssessment = (TextView) findViewById(R.id.riskAssessment_label);
        String riskAssessmentString = riskAssessment.getText().toString();

        EditText tripDate = findViewById(R.id.selectTripDate);
        EditText returnDate = findViewById(R.id.selectReturnDate);

        DialogFragment datePicker = new DatePickerFragment();

        tripDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(),"datePicker");
                tripDateOrReturnDate = true;
            }
        });

        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(),"datePicker");
                tripDateOrReturnDate = false;
            }
        });

        // Setting a default date in date input field
//        EditText defaultInputDate = findViewById(R.id.selectTripDate);
//
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String strDate = sdf.format(cal.getTime());
//
//        defaultInputDate.setText(strDate);
        // Default date end.



        if (riskAssessmentString.length() == 21) {
            dateSelected = false;
        }

        Button nextButton = (Button)findViewById(R.id.NextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                EditText tripInput = (EditText) findViewById(R.id.tripNameInputField);
                String tripName = tripInput.getText().toString();

                EditText destinationInputField = (EditText) findViewById(R.id.destinationInputField);
                String destinationInput = destinationInputField.getText().toString();

                TextView calendar = (TextView) findViewById(R.id.selectTripDate);

                TextView riskAssessment = (TextView) findViewById(R.id.riskAssessment_label);
                String riskAssessmentString = riskAssessment.getText().toString();
                int checkTextLength = riskAssessmentString.length();


                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.riskAssessmentRadioGroup);
                RadioButton radioButtonYes = findViewById(R.id.radioButton_Yes);


                boolean failFlag = false;


                if (tripName.isEmpty()){
                    failFlag = true;
                    tripInput.setError("Trip name is required");
                } else {
                    failFlag = false;
                }
                if (destinationInput.isEmpty()){
                    failFlag = true;
                    destinationInputField.setError("Destination details required");
                } else {
                    failFlag = false;
                }

                if (dateSelected == false) {
                    failFlag = true;
                    calendar.setError("Date selection required");
                } else {
                    failFlag = false;
                }

                if (radioGroup.getCheckedRadioButtonId() == -1){
                    failFlag = true;
                    riskAssessment.setError("Please select radio button");

                } else {
                    failFlag = false;
                }

                if (failFlag == false){

                }
                getInputs();

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Reference to the risk assessment label to remove error message once radio btn is checked.
        TextView riskAssessment = (TextView) findViewById(R.id.riskAssessment_label);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton_Yes:
                if (checked)
                    riskAssessment.setError(null);
                    break;
            case R.id.radioButton_No:
                if (checked) {
                    riskAssessment.setError(null);
                }
                    break;
        }
    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener { @NonNull
        @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                LocalDate d = LocalDate.now();
                int year = d.getYear();
                int month = d.getMonthValue();
                int day = d.getDayOfMonth();

                return new DatePickerDialog(getActivity(), this, year, --month, day);
            }
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                LocalDate date = LocalDate.of(year, ++month, day);
                ((CreateNewTrip)getActivity()).updateTD(date);
            }
    }


    public void showDatePickerDialogForTripDate(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialogForReturnDate(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

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

    private void getInputs(){
        EditText nameTxt = findViewById(R.id.tripNameInputField);
        EditText destinationTxt = findViewById(R.id.destinationInputField);
        EditText tripDateTxt = findViewById(R.id.selectTripDate);
        EditText returnDateTxt = findViewById(R.id.selectReturnDate);
        EditText descriptionTxt = findViewById(R.id.descriptionInputField);
        EditText transportationTxt = findViewById(R.id.transportationInputField);
        RadioGroup radioGroup = findViewById(R.id.riskAssessmentRadioGroup);
        RadioButton radioButtonTxt = findViewById(radioGroup.getCheckedRadioButtonId());

        String strTripName = nameTxt.getText().toString(),
                strDestination = destinationTxt.getText().toString(),
                strTripDate = tripDateTxt.getText().toString(),
                strReturnDate = returnDateTxt.getText().toString(),
                strDescription = descriptionTxt.getText().toString(),
                strTransportation = transportationTxt.getText().toString(),
                strRiskAssessment = radioButtonTxt.getText().toString();
        displayAlert(strTripName, strDestination, strTripDate, strReturnDate, strDescription, strTransportation,strRiskAssessment);


    }

    private void displayAlert(
            String strTripName,
            String strDestination,
            String strTripDate,
            String strReturnDate,
            String strDescription,
            String strTransportation,
            String strRiskAssessment
            ) {
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage(
                "Details entered:\n"
                        +strTripName        +   "\n"
                        +strDestination     +   "\n"
                        +strTripDate        +   "\n"
                        +strReturnDate      +   "\n"
                        +strDescription     +   "\n"
                        +strTransportation  +   "\n"
                        +strRiskAssessment
        )
                .setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface goBack, int i) {

            }
        })
                .setPositiveButton("Save Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
            // Here will be the logic to save data into the database
                        saveDetails();
                    }
                }).show();
    }

    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText nameTxt = findViewById(R.id.tripNameInputField);
        EditText destinationTxt = findViewById(R.id.destinationInputField);
        EditText tripDateTxt = findViewById(R.id.selectTripDate);
        EditText returnDateTxt = findViewById(R.id.selectReturnDate);
        EditText descriptionTxt = findViewById(R.id.descriptionInputField);
        EditText transportationTxt = findViewById(R.id.transportationInputField);
        RadioGroup radioGroup = findViewById(R.id.riskAssessmentRadioGroup);
        RadioButton radioButtonTxt = findViewById(radioGroup.getCheckedRadioButtonId());


        String name = nameTxt.getText().toString();
        String destination = destinationTxt.getText().toString();
        String tripDate = tripDateTxt.getText().toString();
        String returnDate = returnDateTxt.getText().toString();
        String description = descriptionTxt.getText().toString();
        String transportation = transportationTxt.getText().toString();
        String riskAssessment = radioButtonTxt.getText().toString();



        long tripID = dbHelper.insertDetails(name, destination, tripDate, returnDate,description,transportation,riskAssessment);

        Toast.makeText(this, "New trip has been created with id: " + tripID,
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}