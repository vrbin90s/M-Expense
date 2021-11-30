package uk.gre.ac.ks3319t.m_expense;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> implements Filterable{

    // Variable for context.
    public Context context;
    // Variable that store trip details list
    public List<TripDetails> tripDetailsList;
    // Variable that stores copy of the trip details list items
    private List<TripDetails> copyOfTheMainTripDetailList;
    // Variable that stores database helper class.
    public DatabaseHelper databaseHelper;
    // Referencing database.
    SQLiteDatabase db;





    // Constructor for our variables
    public TripAdapter(Context context, List<TripDetails> tripDetailsList) {
        this.context = context;
        this.tripDetailsList = tripDetailsList;
        copyOfTheMainTripDetailList = new ArrayList<>(tripDetailsList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Bind values to set and show data
        // in textView of each card layout.


        TripDetails tripDetails = tripDetailsList.get(position);
        holder.cardTitle.setText(tripDetails.getTitle());
        holder.cardDescription.setText(tripDetails.getDescription());
        holder.cardDate.setText(tripDetails.getDate());


        holder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int tripID = tripDetails.getTripID();
                databaseHelper = new DatabaseHelper(context);
                db = databaseHelper.getWritableDatabase();
                PopupMenu popupMenu = new PopupMenu(context, holder.optionButton);
                popupMenu.inflate(R.menu.menu_card);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.update_details:

//                                Toast.makeText(context.getApplicationContext(), "This will update trip details with ID: " + tripDetails.getTripID(),
//                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, UpdateTripDetails.class);
                                intent.putExtra("tripID", tripID);
                                context.startActivity(intent);

                                return true;
                            case R.id.delete_details:

                                // On delete button selected call alert dialogue
                                // to confirm delete action
                                // to prevent error deleting the record

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("Delete record");
                                alertDialog.setMessage("Are you sure you want to delete this record?");

                                // If user selects CANCEL button
                                // than do nothing
                                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                // If user selects YES button
                                // delete the record from the database
                                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        db.delete("trip_details","trip_id" + " = " + tripID, null);
                                        notifyItemRangeChanged(position,tripDetailsList.size());
                                        tripDetailsList.remove(position);
                                        notifyItemRemoved(position);
                                        db.close();

                                        Toast.makeText(context.getApplicationContext(),  tripDetails.getTitle() + " record was deleted"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                });

                                AlertDialog dialog = alertDialog.create();
                                dialog.show();


                                return true;

                            default:
                                return onMenuItemClick(menuItem);

                        }
                    }

                });
                popupMenu.show();

            }
        });

        // do something than card is clicked.
        holder.tripCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int tripID = tripDetails.getTripID();


                Context context = view.getContext();
                Toast.makeText(context, tripDetails.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DisplayTripDetails.class);
                intent.putExtra("tripID", tripID);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        // Returning the size of our trip details list.
        return tripDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cardTitle, cardDescription, cardDate;
        CardView tripCard;
        ImageButton optionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.TRIP_TitleText);
            cardDescription = itemView.findViewById(R.id.TRIP_Description);
            cardDate = itemView.findViewById(R.id.TRIP_DateText);
            tripCard = itemView.findViewById(R.id.TRIP_CardID);
            optionButton = itemView.findViewById(R.id.TRIP_OptionsMenu);

        }
    }

    // Method that returns filtered search results to our recycle view.
    @Override
    public Filter getFilter() {
        return filteredList;
    }

    // Method that filters our recycler view items.
    private Filter filteredList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TripDetails> filteredList = new ArrayList<>();
            // If search view is empty than add all items to our recycler view.
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(copyOfTheMainTripDetailList);
                // Else if user type something in search view input field
                // add the item that contains those character
                // into our recycle view.
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                // Gets card title name (trip name) containing input characters
                // and adds to the filtered list.
                for (TripDetails tripItem : copyOfTheMainTripDetailList) {
                    if (tripItem.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(tripItem);
                    }
                }
            }

            // Add filtered result values to the list.
            FilterResults results = new FilterResults();
            results.values = filteredList;

            // Return filtered results.
            return results;
        }

        // Method that notify our trip adaptor
        // about recycler view data changes.
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            tripDetailsList.clear();
            tripDetailsList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };




}
