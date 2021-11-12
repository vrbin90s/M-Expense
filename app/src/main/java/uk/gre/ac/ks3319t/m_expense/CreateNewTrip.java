package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        Button nextButton = (Button)findViewById(R.id.NextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                EditText tripInput = (EditText) findViewById(R.id.tripNameInputField);
                String tripName = tripInput.getText().toString();

                if (tripName.equalsIgnoreCase("")){
                    tripInput.setHint("Trip name is required");
                    tripInput.setHintTextColor(Color.RED);
                } else {
                    getInputs();
                }

            }
        });
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
                        goBack.cancel();
            }
        })
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
            // Here will be the logic to save data into the database
                    }
                }).show();
//        new AlertDialog.Builder(this).setNeutralButton("Next", new DialogInterface())
    }



}