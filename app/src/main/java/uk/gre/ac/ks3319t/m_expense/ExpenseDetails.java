package uk.gre.ac.ks3319t.m_expense;

public class ExpenseDetails {

    /* Variables that store details for our trips */

    // Variable that store our trip ID's.
    private int expID;

    // Variables that store expense name, amount, date, comment;
    private String expType, expAmount, expDate, expComment;

    // Constructor for our expense details
    public ExpenseDetails(int expID, String expenseType, String expenseAmount, String expenseDate, String expenseComment) {
        this.expID = expID;
        this.expType = expenseType;
        this.expAmount = expenseAmount;
        this.expDate = expenseDate;
        this.expComment = expenseComment;
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
