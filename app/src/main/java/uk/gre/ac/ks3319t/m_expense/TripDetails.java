package uk.gre.ac.ks3319t.m_expense;

public class TripDetails {

    /**
     * This class is based on the code example provided at https://androidtuts4u.blogspot.com/2018/06/android-cardview-and-sqlite-example.html
     * Android CardView And SQLite example
     */

    /* Variables that store details for our trips */

    // Variable that store our trip ID's.
    private int tripID;

    // Variables that store trip title, description and trip date.
    private String title, description, date;

    // Constructor for our trip variables
    public TripDetails(int tripId, String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.tripID = tripId;
    }

    // Methods that are used to get trip details specified in our variables.
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
