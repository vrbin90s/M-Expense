package uk.gre.ac.ks3319t.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DisplayExpenseDetails extends AppCompatActivity {


    // Reference to the toolbar
    Toolbar toolbar;
    // References to the TextView objects
    DatabaseHelper dbHelper;
    // Creating variable for our SQLiteDatabase
    SQLiteDatabase db;
    // Reference to our buttons
    Button detailsButton;
    FloatingActionButton fabAddNewExpense;

    // Reference to the list that stores all expense details
    List<ExpenseDetails> expenseDetailsList = new ArrayList<>();

    // References to edit text input fields
    EditText expenseType, expenseAmount, expenseTime, expenseComment;

    // Reference to our recyclerview
    private RecyclerView recyclerView;

    public static long trip_expense_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expense_details);

        // Passing selected item ID
        final int rowID = getIntent().getIntExtra("tripID", -1);
        trip_expense_id = rowID;


        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Getting RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.EXP_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populating expenses list
        expenseDetailsList = databaseHelper.getExpensesDetails();


        // Creating RecyclerView
        ExpenseAdapter adapter = new ExpenseAdapter(this, expenseDetailsList);
        // Setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Getting our buttons
        detailsButton = (Button) findViewById(R.id.DTD_DetailsButton);
        fabAddNewExpense = (FloatingActionButton) findViewById(R.id.addExpenseButton);


        // Calling custom toolbar
        toolbar = (Toolbar) findViewById(R.id.EXP_CustomToolbar);
        setSupportActionBar(toolbar);

        // Adding default go back button
        // to the toolbar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fabAddNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayExpenseDetails.this, AddTripExpenses.class);
                intent.putExtra("tripID", rowID);
                startActivity(intent);
            }
        });

        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayExpenseDetails.this, DisplayTripDetails.class);
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
                startActivity(new Intent(DisplayExpenseDetails.this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}