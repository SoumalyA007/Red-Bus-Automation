package enums;

public enum Sorting {

    RATING("Ratings"),
    DEPARTURE_TIME("Departure Time"),
    PRICE("Price"),
    NOT_SELECTED(""),
    ASCENDING("Ascending"),
    DESCENDING("Descending");


    private final String displayName;

    Sorting(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
