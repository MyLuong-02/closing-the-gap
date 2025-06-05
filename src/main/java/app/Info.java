package app;

/* This class is created to store all information needed for showing the gap between indigenous and non-indigenous people in two censuses 
 * 
 * @Author: Luong Thi Tra My
 * @Email: s3987023@rmit.edu.vn
*/
public class Info {
    // Declare variables
    private String name;
    private int countIndig;
    private double percentIndig;
    private int countNonIndig;
    private double percentNonIndig;

    // Constructor
    public Info(String name, int countIndig, double percentIndig, int countNonIndig, double percentNonIndig) {
        this.name = name;
        this.countIndig = countIndig;
        this.percentIndig = percentIndig;
        this.countNonIndig = countNonIndig;
        this.percentNonIndig = percentNonIndig;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getCountIndig() {
        return countIndig;
    }

    public double getPercentIndig() {
        return percentIndig;
    }

    public int getCountNonIndig() {
        return countNonIndig;
    }

    public double getPercentNonIndig() {
        return percentNonIndig;
    }

}
