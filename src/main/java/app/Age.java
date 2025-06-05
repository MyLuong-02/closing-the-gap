package app;

public class Age {
    private String name;
    private String indigenousStatus;
    private String ageRange;
    private int count;
    private double percentage;

    public Age (String name, String indigenousStatus, String ageRange, int count, double percentage) {
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.ageRange = ageRange;
        this.count = count;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getIndigenousStatus() {
        return indigenousStatus;
    }

    public String getAgeRange() {
        return ageRange;
    }


    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

}
