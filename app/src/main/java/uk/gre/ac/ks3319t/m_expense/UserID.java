package uk.gre.ac.ks3319t.m_expense;

import java.util.List;

public class UserID {

    String userID;
    List<ExpenseDetails> expenseDetails;

    public UserID(String userID, List<ExpenseDetails> expenseDetails){
        this.userID = userID;
        this.expenseDetails = expenseDetails;
    }
}
