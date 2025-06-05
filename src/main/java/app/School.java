package app;

/*
 *  Class representing a school from the Studio Project database
 * 
 * @Author Luong Thi Tra My. email: s3987023@rmit.edu.vn
 */
public class School {
    // All attributes need for identifying school completion in each LGA,
    // state/territory
    private String name;
    private String indigenousStatus;
    private String schoolYear;
    private int count;
    private double percentage;

    // Create a school and set the fields
    public School(String name, String indigenousStatus, String schoolYear, int count, double percentage) {
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.schoolYear = schoolYear;
        this.count = count;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getIndigenousStatus() {
        return indigenousStatus;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

}
