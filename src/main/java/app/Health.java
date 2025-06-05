package app;

public class Health {
    private String name;
    private String indigenousStatus;
    private String condition;
    private int count;
    private double percentage;

    public Health(String name, String indigenousStatus, String condition, int count, double percentage) {
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.condition = condition;
        this.count = count;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getIndigenousStatus() {
        return indigenousStatus;
    }

    public String getCondition() {
        return condition;
    }

    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

}
