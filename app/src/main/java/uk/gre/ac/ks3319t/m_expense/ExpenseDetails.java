package uk.gre.ac.ks3319t.m_expense;

public class ExpenseDetails {

    /**
     * This class is based on the code example provided at https://androidtuts4u.blogspot.com/2018/06/android-cardview-and-sqlite-example.html
     * Android CardView And SQLite example
     */

    /* Variables that store details for our trips */

    // Variable that store our trip ID's.
    private int expID;
    private long exp_trip_id;

    // Variables that store expense name, amount, date, comment;
    private String expType, expAmount, expDate, expComment;


    // Constructor for our expense details
    public ExpenseDetails(int expID, String expenseType, String expenseAmount, String expenseDate, String expenseComment, long exp_trip_id) {
        this.expID = expID;
        this.expType = expenseType;
        this.expAmount = expenseAmount;
        this.expDate = expenseDate;
        this.expComment = expenseComment;
        this.exp_trip_id = exp_trip_id;

    }

    public int getExpID() {
        return expID;
    }

    public String getExpType() {
        return expType;
    }

    public String getExpAmount() {
        return expAmount;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getExpComment() {
        return expComment;
    }
}
