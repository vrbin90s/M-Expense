package uk.gre.ac.ks3319t.m_expense;

public class TripDetails {

    private int  tripID;
    private String  title, description, date;

    public TripDetails(int tripId,String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.tripID = tripId;
    }

    public int getTripID() {
        return tripID;
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
