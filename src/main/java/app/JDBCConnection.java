package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Luong Thi Tra My. Email: s3987023@rmit.edu.vn
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get the personas in the database
     * 
     * @return
     *         Returns an ArrayList of Persona objects
     */
    public ArrayList<Persona> getPersonas() {
        // Create the ArrayList of Persona objects to return
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int ID = results.getInt("num");
                String name = results.getString("name");
                String quotes = results.getString("userQuotes");
                String background = results.getString("background");
                String goals = results.getString("needsAndGoals");
                String skills = results.getString("skills");

                // Create a Persona Object
                Persona persona = new Persona(ID, name, quotes, background, goals, skills);

                // Add the persona object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the personas
        return personas;
    }

    /**
     * Get all of the students in the database
     * 
     * @return
     *         Returns an ArrayList of Student objects
     */
    public ArrayList<Student> getStudents() {
        // Create the ArrayList of Student objects to return
        ArrayList<Student> students = new ArrayList<Student>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Student";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String ID = results.getString("ID");
                String name = results.getString("name");
                String email = results.getString("email");

                // Create a Student Object
                Student student = new Student(ID, name, email);

                // Add the student object to the array
                students.add(student);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the students
        return students;
    }

}
