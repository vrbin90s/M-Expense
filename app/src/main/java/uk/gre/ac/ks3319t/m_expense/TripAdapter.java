package uk.gre.ac.ks3319t.m_expense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private Context context;
    private List<TripDetails> tripDetailsList;
    private DatabaseHelper db;



    // Constructor
    public TripAdapter(Context context, List<TripDetails> tripDetailsList) {
        this.context = context;
        this.tripDetailsList = tripDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textView and image of each card layout


        TripDetails tripDetails = tripDetailsList.get(position);
        holder.cardTitle.setText(tripDetails.getTitle());
        holder.cardDescription.setText(tripDetails.getDescription());
        holder.cardDate.setText(tripDetails.getDate());

        holder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, holder.optionButton);
                popupMenu.inflate(R.menu.menu_card);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.update_details:
                                Toast.makeText(context.getApplicationContext(), "This will update trip details",
                                        Toast.LENGTH_SHORT).show();

                                return true;
                            case R.id.delete_details:
                                Toast.makeText(context.getApplicationContext(), "This will delete this trip with ID " + tripDetails.getCardID()
                                        , Toast.LENGTH_SHORT).show();
                             /*   Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);*/

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
                Context context = view.getContext();
                Toast.makeText(context, tripDetails.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        // this method is used to show number of card items
        // in recycle view.
        return tripDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cardTitle, cardDescription, cardDate;
        CardView tripCard;
        ImageButton optionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardDescription = itemView.findViewById(R.id.cardDescription);
            cardDate = itemView.findViewById(R.id.cardDate);
            tripCard = itemView.findViewById(R.id.tripCard);
            optionButton = itemView.findViewById(R.id.options_menu);



        }
    }



}
