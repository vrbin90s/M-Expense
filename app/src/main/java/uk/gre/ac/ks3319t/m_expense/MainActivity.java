package uk.gre.ac.ks3319t.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Reference to the list to store all the products
    List<TripDetails> tripDetailsList = new ArrayList<>() ;

    //Reference to the recycleView
    RecyclerView recyclerView;
    TripAdapter adapter;

    //Reference to the setting Button
    MenuItem settingButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHelper db = new DatabaseHelper(this);

        // Getting setting button
        settingButton = (MenuItem) findViewById(R.id.settings_button);


        // getting recycleView from xml file
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing the trip list
        if (SettingsActivity.recordsDeleted == false){
            tripDetailsList = db.getTripDetails();

        } else if (SettingsActivity.recordsDeleted) {

            tripDetailsList.clear();
            DatabaseHelper newDBHelper = new DatabaseHelper(this);
            newDBHelper.getWritableDatabase();
            SQLiteDatabase sqlDB = newDBHelper.getWritableDatabase();
            sqlDB.delete("trip_details",null,null);
            //SettingsActivity.recordsDeleted = true;
            sqlDB.close();
        }




        // creating recycleView
        TripAdapter adapter = new TripAdapter(this,tripDetailsList);

        //setting adapter to recycle view
        recyclerView.setAdapter(adapter);
        System.out.println("Card details: " +tripDetailsList);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateNewTrip.class));
                CreateNewTrip.dateSelected = false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        TripAdapter newTripAdapter= new TripAdapter(this, tripDetailsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newTripAdapter);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.search_InputField);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newTripAdapter != null) {
                    newTripAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });
        return true;


    }


    // Open setting activity once user
    // interacts with setting button.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_button:
                Intent startSettingActivity = new Intent(this,SettingsActivity.class);
                startActivity(startSettingActivity);
                return true;

        }
        return false;
    }


    public class SearchAdapter extends ListActivity {

        List<TripDetails> tripDetailsList = new ArrayList<>() ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            DatabaseHelper db = new DatabaseHelper(this);

            tripDetailsList = db.getTripDetails();

            setListAdapter(new ArrayAdapter<TripDetails>(this, R.layout.list_items, tripDetailsList));
            ListView lv = getListView();
            lv.setTextFilterEnabled(true);
        }
    }



}