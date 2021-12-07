package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;


public class CreateNewTrip extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private TextView textLatLong, textAddress;
    private ResultReceiver resultReceiver;


    public static boolean dateSelected = false;
    public static boolean newTripActivity = true;

    private boolean tripDateOrReturnDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        resultReceiver = new AddressResultReceiver(new Handler());
        textLatLong = findViewById(R.id.textLatLong);
        textAddress = findViewById(R.id.textAddress);

        /**
         * Location Script
         * This is based on the code provided at: https://www.youtube.com/watch?v=ari3iD-3q8c
         * Request user for permissions.
         */
        findViewById(R.id.buttonGetCurrentLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            CreateNewTrip.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    getCurrentLocation();
                }
            }
        });

        newTripActivity = true;

        TextView riskAssessment = (TextView) findViewById(R.id.update_riskAssessment_label);
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





        if (riskAssessmentString.length() == 21) {
            dateSelected = false;
        }

        Button nextButton = (Button)findViewById(R.id.updateButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                EditText tripInput = (EditText) findViewById(R.id.update_tripNameInputField);
                String tripName = tripInput.getText().toString();

                EditText destinationInputField = (EditText) findViewById(R.id.update_destinationInputField);
                String destinationInput = destinationInputField.getText().toString();

                TextView calendar = (TextView) findViewById(R.id.selectTripDate);

                TextView riskAssessment = (TextView) findViewById(R.id.update_riskAssessment_label);
                String riskAssessmentString = riskAssessment.getText().toString();
                int checkTextLength = riskAssessmentString.length();


                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.riskAssessmentRadioGroup);
                RadioButton radioButtonYes = findViewById(R.id.update_radioButton_Yes);


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
                if(failFlag == false){
                    getInputs();
                }

            }
        });
    }

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

                if(newTripActivity == true){
                    ((CreateNewTrip) getActivity()).updateTD(date);
                } else {
                    ((UpdateTripDetails)getActivity()).updateTD(date);
                }


            }
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

    /**
     * Location Script
     * Check for permissions for obtaining location, notify if declined.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Location Script
     * Receive current location script (Coordinates).
     */
    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(CreateNewTrip.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    /**
                     * Location Script
                     * On location result, populate the textview with coordinates.
                     */
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(CreateNewTrip.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            textLatLong.setText(
                                    String.format(
                                            "Latitude: %s\nLongitude: %s",
                                            latitude,
                                            longitude
                                    )
                            );

                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddressFromLatLong(location);
                        }
                    }

                }, Looper.getMainLooper());


    }
    /**
     * Location Script
     * Receive latitude and longitude and calculate the address using google API.
     */
    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);
    }

    /**
     * Location Script
     * Class for result receiver script. Handles receiving and sending the location.
     */
    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Location Script
         * Receive location result receive, create a toast.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == Constants.SUCCESS_RESULT) {
                textAddress.setText(resultData.getString(Constants.RESULT_DATA_KEY));
            }  else {
                Toast.makeText(CreateNewTrip.this,resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void getInputs(){
        EditText nameTxt = findViewById(R.id.update_tripNameInputField);
        EditText destinationTxt = findViewById(R.id.update_destinationInputField);
        EditText tripDateTxt = findViewById(R.id.selectTripDate);
        EditText returnDateTxt = findViewById(R.id.selectReturnDate);
        EditText descriptionTxt = findViewById(R.id.update_descriptionInputField);
        EditText transportationTxt = findViewById(R.id.update_transportationInputField);
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

        EditText nameTxt = findViewById(R.id.update_tripNameInputField);
        EditText destinationTxt = findViewById(R.id.update_destinationInputField);
        EditText tripDateTxt = findViewById(R.id.selectTripDate);
        EditText returnDateTxt = findViewById(R.id.selectReturnDate);
        EditText descriptionTxt = findViewById(R.id.update_descriptionInputField);
        EditText transportationTxt = findViewById(R.id.update_transportationInputField);
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