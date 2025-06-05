package app;

/* 
 * Class representing a nonschool from the Studio Project database
 * 
 * @Author Luong Thi Tra My. email: s3987023@rmit.edu.vn
 */
public class Nonschool {
    // All attributes need for identifying nonschool completion in each LGA,
    // state/territory
    private String name;
    private String indigenousStatus;
    private String schoolBracket;
    private int count;
    private double percentage;

    // Create a nonschool constructor and set the fields
    public Nonschool(String name, String indigenousStatus, String schoolBracket, int count, double percentage) {
        this.name = name;
        this.indigenousStatus = indigenousStatus;
        this.schoolBracket = schoolBracket;
        this.count = count;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getIndigenousStatus() {
        return indigenousStatus;
    }

    public String getNonSchoolBracket() {
        return schoolBracket;
    }

    public int getCount() {
        return count;
    }

    public double getPercentage() {
        return percentage;
    }

}
