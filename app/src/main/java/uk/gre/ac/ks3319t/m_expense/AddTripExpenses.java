package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class AddTripExpenses extends AppCompatActivity {

    public static boolean dateSelected = false;

    // Boolean to check whether a new expense record has been created
    public static boolean tripCrated;


    // Reference to our expense toolbar
    Toolbar toolbar;
    // References to edit text input fields
    EditText expenseType, expenseAmount, expenseTime, expenseComment;
    // Reference to add new expense button
    Button addNewExpenseButton;
    // Reference to DisplayTripDetails class to get a boolean
    DisplayTripDetails displayTripDetails;
    TripDetails tripDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_expenses);

        // Calling our expense toolbar
        toolbar = (Toolbar) findViewById(R.id.expenseToolbar);
        setSupportActionBar(toolbar);

        // Adding back button to our expense toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper db = new DatabaseHelper(this);

        //Setting a default date in date input field
        EditText defaultInputDate = findViewById(R.id.selectExpenseDate);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = sdf.format(cal.getTime());

        defaultInputDate.setText(strDate);
        //Default date end.


        // Getting our EditText input fields
        expenseType = (EditText) findViewById(R.id.TypeOfExpense);
        expenseAmount = (EditText) findViewById(R.id.AmountOfExpense);
        expenseTime = (EditText) findViewById(R.id.selectExpenseDate);
        expenseComment = (EditText) findViewById(R.id.ExpenseDescription);

        // Reference to our add new expense button
        addNewExpenseButton = (Button) findViewById(R.id.add_NewExpenseButton);


        // Reference to data picker DialogFragment
        DialogFragment dataPicker = new DatePickerFragment();

        expenseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPicker.show(getSupportFragmentManager(), "dataPicker");
            }
        });


        final long rowID = getIntent().getIntExtra("tripID", -1);

        addNewExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText expenseAmountInput = (EditText) findViewById(R.id.AmountOfExpense);
                String expenseInput = expenseAmountInput.getText().toString();

                EditText expenseNameInputField = (EditText) findViewById(R.id.TypeOfExpense);
                String NameInput = expenseNameInputField.getText().toString();

                TextView calendar = (TextView) findViewById(R.id.selectExpenseDate);


                boolean failFlag = false;


                if (expenseInput.isEmpty()) {
                    failFlag = true;
                    expenseAmountInput.setError("Trip name is required");
                }
                if (NameInput.isEmpty()) {
                    failFlag = true;
                    expenseNameInputField.setError("Destination details required");
                }


                if (failFlag == false) {

                    saveDetails(rowID);
                    tripCrated = true;

                }

            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
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
            ((AddTripExpenses) getActivity()).updateTD(td);


        }

    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new AddTripExpenses.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");
    }


    public void updateTD(LocalDate td) {
        expenseTime.setText(td.toString());
        expenseTime.setError(null);
        dateSelected = true;

    }


    private void displayAlert(
            String strExpenseAmount,
            String strExpenseName,
            String strTripDate,
            String strDescription) {
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage(
                "Details entered:\n"
                        + strExpenseAmount + "\n"
                        + strExpenseName + "\n"
                        + strTripDate + "\n"
                        + strDescription + "\n"
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


    public void saveDetails(long id) {


        DatabaseHelper dbHelper = new DatabaseHelper(this);


        String expType = expenseType.getText().toString();
        String expAmount = expenseAmount.getText().toString();
        String expTime = expenseTime.getText().toString();
        String expComment = expenseComment.getText().toString();
        long expense_trip_id = DisplayTripDetails.expense_trip_id;


        long tripID = dbHelper.insertExpenseDetails(expType, expAmount, expTime, expComment, expense_trip_id);


        Toast.makeText(this, "New expense has been created with id: " + tripID,
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(AddTripExpenses.this, DisplayExpenseDetails.class);
        intent.putExtra("tripID", expense_trip_id);
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddTripExpenses.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}