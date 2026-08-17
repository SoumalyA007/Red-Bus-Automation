package enums;

public enum TimeWindow {
    MORNING(6, 12),
    AFTERNOON(12, 18),
    EVENING(18, 24),
    NIGHT(24, 6); // wraps past midnight — handle specially

    private final int startHour;
    private final int endHour;

    TimeWindow(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public boolean contains(int hour) {
        if (startHour < endHour) {
            return hour >= startHour && hour < endHour;
        }
        // wraps midnight, e.g. NIGHT: 21–6
        return hour >= startHour || hour < endHour;
    }
}
