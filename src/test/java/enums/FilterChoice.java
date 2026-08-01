package enums;

public enum FilterChoice {

    //........BUS TYPE........//
    AC("AC"),
    NON_AC("NonAC"),
    SLEEPER("Sleeper"),
    SEATER("Seater"),
    VOLVO_BUS("Volvo Buses"),

    //........SINGLE_WINDOW_SEATER_SLEEPER........//
    SINGLE_SEAT("Single Seat"),

    //........DEPARTURE_TIME........//
    //........DROPOFF_TIME........//
    MORNING("Morning"),
    EVENING("Evening"),
    AFTERNOON("Afternoon"),
    NIGHT("Night"),

    //........BUS_FEATURES........//
    LIVE_TRACKING("Live Tracking"),
    HIGH_RATED_BUSES("High Rated Buses"),
    DEALS("Deals"),
    PRIMO_BUS("Primo Buses"),
    FREE_CANCELLATION("Free Cancellation"),

    //........BUS_FEATURES........//
    /* Can be different for different cities */
    GREENLINE("Greenline"),

    //.......BOARDING POINT........//
    /* Can be different for different cities */
    AIRPORT("Airport"),

    //.......DROPPING POINT........//
    /* Can be different for different cities */
    OTHERS("Others"),

    //.......AMENITIES........//
    WIFI("WiFi"),
    CHARGING_POINT("Charging Point"),
    TV("TV"),
    WATER_BOTTLE("Water Bottle"),
    BLANKETS("Blankets"),

    //.......SPECIAL_BUS_FEATURES........//
    ON_TIME("On Time"),
    FREE_DATE_CHANGE("Free Date Change"),

    //.......RTC_BUS_TYPE........//
    EXPRESS("Express"),
    VOLVO("Volvo");


    private final String choice;

    FilterChoice(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }


}
