package dev.gobert.objects;

public class Discount {
    String eventType;
    double percent;

    public Discount() {
    }

    public Discount(String eventType, double percent) {
        this.eventType = eventType;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "\nDiscounts " +
                "\n=========" +
                "\nEvent:   " + eventType +
                "\nPercent: " + percent;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
