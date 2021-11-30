package uk.gre.ac.ks3319t.m_expense;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    // Variable for context
    public Context context;
    // Variable that stores expense detail list
    public List<ExpenseDetails> expenseDetailsList;
    // Variable that stores database helper class
    private DatabaseHelper databaseHelper;
    // Referencing SQLite database.
    SQLiteDatabase db;

    // Constructor for our variables
    public ExpenseAdapter(Context context, List<ExpenseDetails> expenseDetails) {
        this.context = context;
        this.expenseDetailsList = expenseDetails;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_expense_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ExpenseDetails expenseDetails = expenseDetailsList.get(position);
        holder.expTitle.setText(expenseDetails.getExpType());
        holder.expAmount.setText(expenseDetails.getExpAmount());
        holder.expTime.setText(expenseDetails.getExpDate());
        holder.expComment.setText(expenseDetails.getExpComment());

        holder.optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int expID = expenseDetails.getExpID();
                databaseHelper = new DatabaseHelper(context);
                db = databaseHelper.getWritableDatabase();
                PopupMenu popupMenu = new PopupMenu(context, holder.optionsButton);
                popupMenu.inflate(R.menu.exmpense_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.delete_expense:

                                // On delete button selected call alert dialogue
                                // to confirm delete action (error prevention)

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("Delete record");
                                alertDialog.setMessage("Are you sure you want to delete this record?");

                                // Cancel button
                                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                // Confirm button
                                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        db.delete(DatabaseHelper.EXPENSE_TABLE_NAME, "expense_id" + " = " + expID, null );
                                        notifyItemRangeChanged(position, expenseDetailsList.size());
                                        expenseDetailsList.remove(position);
                                        notifyItemRemoved(position);
                                        db.close();

                                        Toast.makeText(context.getApplicationContext(), expenseDetails.getExpType() + " record was deleted",
                                                Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public int getItemCount() {
        return expenseDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView expTitle, expAmount, expTime, expComment;
        CardView expCard;
        ImageButton optionsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            expTitle = itemView.findViewById(R.id.EXP_TitleText);
            expAmount = itemView.findViewById(R.id.EXP_AmountText);
            expTime = itemView.findViewById(R.id.EXP_DateText);
            expComment = itemView.findViewById(R.id.EXP_CommentText);
            expCard = itemView.findViewById(R.id.EXP_CardID);
            optionsButton = itemView.findViewById(R.id.EXP_OptionsMenu);
        }
    }
}
