package uk.gre.ac.ks3319t.m_expense;

public class TripDetails {

    private int cardID;
    private String title, description, date;

    public TripDetails(int cardID,String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
