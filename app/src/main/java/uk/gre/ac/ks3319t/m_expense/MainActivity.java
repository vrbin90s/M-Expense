package uk.gre.ac.ks3319t.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //a list to store all the products
    List<TripDetails> tripDetailsList = new ArrayList<>() ;
    ArrayList<String> items;

    //the recycleView
    RecyclerView recyclerView;
    TripAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);


        // getting recycleView from xml file
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing the trip list
        tripDetailsList = db.getCardDetails();



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
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

}