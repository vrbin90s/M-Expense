package uk.gre.ac.ks3319t.m_expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;

public class AddTripExpenses extends AppCompatActivity {
    public static boolean dateSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_expenses);


        Button nextButton = (Button)findViewById(R.id.NextExpensesButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                EditText expenseAmountInput = (EditText) findViewById(R.id.AmountOfExpense);
                String expenseInput = expenseAmountInput.getText().toString();

                EditText expenseNameInputField = (EditText) findViewById(R.id.TypeOfExpense);
                String NameInput = expenseNameInputField.getText().toString();

                TextView calendar = (TextView) findViewById(R.id.selectExpenseDate);



                boolean failFlag = false;


                if (expenseInput.isEmpty()){
                    failFlag = true;
                    expenseAmountInput.setError("Trip name is required");
                }
                if (NameInput.isEmpty()){
                    failFlag = true;
                    expenseNameInputField.setError("Destination details required");
                }

                if (dateSelected == false) {
                    failFlag = true;
                    calendar.setError("Date selection required");
                }

                if (failFlag == false){
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
            ((AddTripExpenses)getActivity()).updateTD(td);


        }


    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new AddTripExpenses.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");
    }

    public void updateTD(LocalDate td) {
        TextView tripDate = (TextView) findViewById(R.id.selectTripDate);
        tripDate.setText(td.toString());
        tripDate.setError(null);
        dateSelected = true;

    }

    private void getInputs(){
        EditText expenseInput = (EditText) findViewById(R.id.TypeOfExpense);
        EditText nameInput = (EditText) findViewById(R.id.AmountOfExpense);
        TextView tripDateInput = (TextView) findViewById(R.id.selectTripDate);


        EditText descriptionInput = (EditText) findViewById(R.id.ExpenseDescription);



        String strExpenseAmount = expenseInput.getText().toString(),
                strExpenseName = nameInput.getText().toString(),
                strTripDate = tripDateInput.getText().toString(),

                strDescription = descriptionInput.getText().toString();


        displayAlert(strExpenseAmount, strExpenseName, strTripDate, strDescription);


    }

    private void displayAlert(
            String strExpenseAmount,
            String strExpenseName,
            String strTripDate,
            String strDescription) {
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage(
                "Details entered:\n"
                        +strExpenseAmount +"\n"
                        +strExpenseName+"\n"
                        +strTripDate + "\n"
                        +strDescription + "\n"
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