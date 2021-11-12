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

import java.time.LocalDate;

public class CreateNewTrip extends AppCompatActivity {

    public static boolean dateSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        TextView riskAssessment = (TextView) findViewById(R.id.riskAssessment_label);
        String riskAssessmentString = riskAssessment.getText().toString();

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
                }
                if (destinationInput.isEmpty()){
                    failFlag = true;
                    destinationInputField.setError("Destination details required");
                }

                if (dateSelected == false) {
                    failFlag = true;
                    calendar.setError("Date selection required");
                }

                if (radioGroup.getCheckedRadioButtonId() == -1){
                    failFlag = true;
                    riskAssessment.setError("Please select radio button");

                }

                if (failFlag == false){
                    getInputs();
                }

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
            DatePickerDialog.OnDateSetListener {    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


                LocalDate d = LocalDate.now();
                int year = d.getYear();
                int month = d.getMonthValue();
                int day = d.getDayOfMonth();



                return new DatePickerDialog(getActivity(), this, year, --month, day);

            }
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                LocalDate td = LocalDate.of(year, ++month, day);
                ((CreateNewTrip)getActivity()).updateTD(td);


            }


    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");
    }

    public void updateTD(LocalDate td) {
        TextView tripDate = (TextView) findViewById(R.id.selectTripDate);
        tripDate.setText(td.toString());
        tripDate.setError(null);
        dateSelected = true;

    }

    private void getInputs(){
        EditText tripInput = (EditText) findViewById(R.id.tripNameInputField);
        EditText destinationInput = (EditText) findViewById(R.id.destinationInputField);
        TextView tripDateInput = (TextView) findViewById(R.id.selectTripDate);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.riskAssessmentRadioGroup);
        RadioButton radioButtonInput = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        EditText descriptionInput = (EditText) findViewById(R.id.destinationInputField);
        Spinner tranDropdown = (Spinner) findViewById(R.id.transportationSpinner);
        EditText depositAmount = (EditText) findViewById(R.id.depositAmountInputField);

        String strTripName = tripInput.getText().toString(),
                strDestination = destinationInput.getText().toString(),
                strTripDate = tripDateInput.getText().toString(),
                strRiskAssessment = radioButtonInput.getText().toString(),
                strDescription = descriptionInput.getText().toString(),
                strTransportation = tranDropdown.getSelectedItem().toString(),
                strDeposit = depositAmount.getText().toString();
        displayAlert(strTripName, strDestination, strTripDate, strRiskAssessment, strDescription, strTransportation, strDeposit);


    }

    private void displayAlert(
            String strTripName,
            String strDestination,
            String strTripDate,
            String strRiskAssessment,
            String strDescription,
            String strTransportation,
            String strDeposit) {
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage(
                "Details entered:\n"
                +strTripName +"\n"
                +strDestination +"\n"
                +strTripDate + "\n"
                +strRiskAssessment + "\n"
                +strDescription + "\n"
                +strTransportation +"\n"
                +strDeposit
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
                    }
                }).show();
    }



}