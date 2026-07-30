package models;

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

    @Override
    public String toString() {
        return "BusCard{operator='" + operator + "', boardingTime='" + boardingTime
                + "', droppingTime='" + droppingTime + "', duration='" + duration
                + "', totalSeats=" + totalSeats + ", price=" + price
                + ", rating=" + rating + ", busType='" + busType + "'}";
    }
}