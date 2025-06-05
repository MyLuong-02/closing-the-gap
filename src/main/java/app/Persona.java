package app;

/*This class is to store all information needed for presenting a persona 
 * @Author: Luong Thi Tra My. Email: s3987023@rmit.edu.vn
 */
public class Persona {
    // Declare variables
    private int ID;
    private String name;
    private String quotes;
    private String background;
    private String goals;
    private String skills;

    // Constructor
    public Persona(int ID, String name, String quotes, String background, String goals, String skills) {
        this.ID = ID;
        this.name = name;
        this.quotes = quotes;
        this.background = background;
        this.goals = goals;
        this.skills = skills;
    }

    // Getter
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getQuotes() {
        return quotes;
    }

    public String getBackground() {
        return background;
    }

    public String getGoals() {
        return goals;
    }

    public String getSkills() {
        return skills;
    }
}
