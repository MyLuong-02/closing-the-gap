package app;

/**
 * Class represeting a student from the Studio Project database
 * 
 * @author Luong Thi Tra My. email: s3987023@rmit.edu.vn
 */
public class Student {
    // student ID, name and email
    private String ID;
    private String name;
    private String email;

    // Create a student and set the fields
    public Student(String ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
