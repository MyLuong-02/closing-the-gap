package app;
/* This class is created to store all information needed for showing the gap between 2016 and 2021 
@Author: Luong Thi Tra My
@Email: s3987023
*/

public class Change16To21 {
    // Declare variables
    private String name;
    private int count16;
    private double percent16;
    private int count21;
    private double percent21;
    private double change;

    // Constructor
    public Change16To21(String name, int count16, double percent16, int count21, double percent21, double change) {
        this.name = name;
        this.count16 = count16;
        this.percent16 = percent16;
        this.count21 = count21;
        this.percent21 = percent21;
        this.change = change;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getCount16() {
        return count16;
    }

    public double getPercent16() {
        return percent16;
    }

    public int getCount21() {
        return count21;
    }

    public double getPercent21() {
        return percent21;
    }

    public double getChange() {
        return change;
    }

}
