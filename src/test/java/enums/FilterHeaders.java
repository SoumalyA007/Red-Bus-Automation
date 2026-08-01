package enums;

public enum FilterHeaders {

    //........ENUMS........//
    DEPARTURE_TIME("Departure time from source"),
    ARRIVAL_TIME("Arrival time at destination"),
    BUS_TYPE("Bus Type"),
    SINGLE_WINDOW_SEATER_SLEEPER("Single window seater/sleeper"),
    BUS_FEATURES("Bus Features"),
    BUS_OPERATOR("Bus Operator"),
    AMENITIES("Amenities"),
    BOARDING_POINT("Boarding Point"),
    DROPPING_POINT("Dropping Point"),
    SPECIAL_BUS_FEATURES("Special Bus Features"),
    RTC_BUS_TYPE("RTC Bus Type");

    private final String header;

    FilterHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}

