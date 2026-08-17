package models;

import enums.TimeWindow;

public class BusCard {

    private final String operator;
    private final String boardingTime;
    private final String droppingTime;
    private final String duration;
    private final int totalSeats;
    private final double price;
    private final double rating;
    private final String busType;

    public BusCard(String operator, String boardingTime, String droppingTime, String duration,
                   int totalSeats, double price, double rating, String busType) {
        this.operator = operator;
        this.boardingTime = boardingTime;
        this.droppingTime = droppingTime;
        this.duration = duration;
        this.totalSeats = totalSeats;
        this.price = price;
        this.rating = rating;
        this.busType = busType;
    }

    public String getOperator() { return operator; }
    public String getDepartureTime() { return boardingTime; }
    public String getArrivalTime() { return droppingTime; }
    public String getDuration() { return duration; }
    public int getSeatsLeft() { return totalSeats; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getBusType() { return busType; }
    public boolean isBoardingWithinWindow(TimeWindow window) {
        return window.contains(parseHour(boardingTime));
    }

    public boolean isDroppingWithinWindow(TimeWindow window) {
        return window.contains(parseHour(droppingTime));
    }

    private int parseHour(String time) {
        time = time.trim().toUpperCase();
        String[] parts = time.split("[: ]"); // splits "1:15 PM" into ["1","15","PM"]
        int hour = Integer.parseInt(parts[0]);
        String meridiem = parts[parts.length - 1]; // "AM" or "PM"

        if (meridiem.equals("PM") && hour != 12) {
            hour += 12;
        } else if (meridiem.equals("AM") && hour == 12) {
            hour = 0;
        }
        return hour;
    }

    @Override
    public String toString() {
        return "BusCard{operator='" + operator + "', boardingTime='" + boardingTime
                + "', droppingTime='" + droppingTime + "', duration='" + duration
                + "', totalSeats=" + totalSeats + ", price=" + price
                + ", rating=" + rating + ", busType='" + busType + "'}";
    }
}