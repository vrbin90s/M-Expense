package uk.gre.ac.ks3319t.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static boolean recordsDeleted = false;
    // Reference to the toolbar
    Toolbar toolbar;
    // Reference to proceed button
    Button proceedButton;
    // Calling upload DB button

    // Reference to check box
    Button uploadButton;
    // Reference to check box
    CheckBox deleteCheckBox;
    CheckBox uploadCheckBox;
    // Calling DatabaseHelper Class
    DatabaseHelper dbHelper;
    // Boolean to check whether button was clicked.
    private boolean clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Calling custom toolbar
        toolbar = (Toolbar) findViewById(R.id.actionBarWithBackButton);
        setSupportActionBar(toolbar);

        // Calling proceed button
        proceedButton = (Button) findViewById(R.id.proceedButton);

        // Calling delete check box
        deleteCheckBox = (CheckBox) findViewById(R.id.deleteCheckBox);
        uploadCheckBox = (CheckBox) findViewById(R.id.JSON_Checkbox);


        // Adding default go back button
        // to the toolbar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;

                // Check if proceed button have been clicked
                if (clicked) {
                    // Check if delete items checkbox is checked
                    // and that upload checkbox is unchecked.
                    if (deleteCheckBox.isChecked() && !uploadCheckBox.isChecked()) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialog.setTitle("Delete all trip record");
                        alertDialog.setMessage("Are you sure you want to delete all trip records?");

                        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                recordsDeleted = true;
                                //databaseHelper.DeleteAllRecords();

                                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                startActivity(intent);
                                //dialogInterface.cancel();

                            }
                        });
                        alertDialog.show();
                    }
                }

                if (clicked) {
                    // Check if upload checkbox is checked
                    // and that delete all records check box is unchecked.
                    if (uploadCheckBox.isChecked() && !deleteCheckBox.isChecked()) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                        alertDialog.setTitle("Upload expense details");
                        alertDialog.setMessage("Are you sure you want to upload all expense details to the web based server?");

                        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                recordsDeleted = true;
                                //databaseHelper.DeleteAllRecords();

                                Intent intent = new Intent(SettingsActivity.this, DatabaseJsonActivity.class);
                                startActivity(intent);
                                //dialogInterface.cancel();

                            }
                        });
                        alertDialog.show();


                    }

                    if (clicked && deleteCheckBox.isChecked() && uploadCheckBox.isChecked()) {
                        Toast.makeText(SettingsActivity.this, "Only one checkbox has to be checked at the time",
                                Toast.LENGTH_SHORT).show();
                    }
                }
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
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Returning menu item
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}