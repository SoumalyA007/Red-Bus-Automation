package enums;

public enum SortType {

    LOWEST_PRICE("Lowest Price"),
    HIGHEST_PRICE("Highest Price"),
    EARLIEST_DEPARTURE("Earliest Departure"),
    LATEST_DEPARTURE("Latest Departure"),
    SHORTEST_DURATION("Shortest Duration"),
    HIGHEST_RATING("Highest Rating");

    private final String sortOption;

    SortType(String sortOption) {
        this.sortOption = sortOption;
    }

    public String getSortOption() {
        return sortOption;
    }
}
