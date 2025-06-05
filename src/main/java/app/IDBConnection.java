package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite database in Java.
 *
 * @author Luong Thi Tra My. Email: s3987023@rmit.edu.vn
 */

public class IDBConnection {
    // Name of the database file (contained in database foler)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a IDB Object so we can keep talking to the database
     */
    public IDBConnection() {
        System.out.println("Created IDB Connection Object");
    }

    /**
     * This method will return a list of info objects that contain the data related
     * to the gap between indigenous and non-indigenous people in 2016
     * from the database.
     * 
     * @return ArrayList<Info>
     */
    public ArrayList<Info> getInfo2016(String sex, int m, int n) {
        // Create an ArrayList to store the info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set up the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation16 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = 'indigenous' AND PO.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGAPopulation16 P WHERE P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "' AND (P.ageMin BETWEEN '" + m + "' AND '" + n
                    + "') GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation16 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = 'non-indigenous' AND PO.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGAPopulation16 P WHERE P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "' AND (P.ageMin BETWEEN '" + m + "' AND '" + n
                    + "') GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    /**
     * This method will return a list of info objects that contain the data related
     * to the gap between indigenous and non-indigenous people in 2016
     * from the database.
     * 
     * @return ArrayList<Info>
     */
    public ArrayList<Info> getInfo2021(String sex, int m, int n) {
        // Create an ArrayList to store the info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set up the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation21 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = 'indigenous' AND PO.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGAPopulation21 P WHERE P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "' AND (P.ageMin BETWEEN '" + m + "' AND '" + n
                    + "') GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation21 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = 'non-indigenous' AND PO.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGAPopulation21 P WHERE P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "' AND (P.ageMin BETWEEN '" + m + "' AND '" + n
                    + "') GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // This method will return an ArrayList of Change16To21 objects that computes
    // the improvement or decline between 2016 and 2021 in each LGA
    public ArrayList<Change16To21> getChangeByAge(String indig, String sex, int ageMin, int ageMax) {
        // Create an ArrayList to store the Change16To21 objects
        ArrayList<Change16To21> changeList = new ArrayList<Change16To21>();

        // Set up the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent', NI.percentage/I.percentage AS 'gap' FROM (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation16 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = '"
                    + indig + "' AND PO.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGAPopulation16 P WHERE P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex + "' AND (P.ageMin BETWEEN '" + ageMin + "' AND '" + ageMax
                    + "') GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(P.count) AS 'total', CAST(SUM(P.count) AS FLOAT) / (SELECT SUM(PO.count) FROM LGAPopulation21 PO WHERE PO.LGACode = P.LGACode AND PO.indigenousStatus = '"
                    + indig + "' AND PO.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGAPopulation21 P WHERE P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex + "' AND (P.ageMin BETWEEN '" + ageMin + "' AND '" + ageMax
                    + "') GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode ORDER BY gap DESC;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");
                double gap = results.getDouble("gap");

                // Create a new Change16To21 object with the data from the database
                Change16To21 change = new Change16To21(name, total, percentage, sum, percent, gap);

                // Add the Change16To21 object to the ArrayList
                changeList.add(change);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of Change16To21 objects
        return changeList;
    }

    // Get statistics about the gap between indigenous and non-indigenous people in
    // 2021 based on user selection
    public ArrayList<Info> getInfoByHealthCondition(String sex, String condition) {
        // Create an ArrayList of Info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL query and set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(LTHC.count) AS 'total', CAST(SUM(LTHC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = LTHC.LGACode AND P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGALTHC21 LTHC WHERE LTHC.indigenousStatus = 'indigenous' AND LTHC.sex = '"
                    + sex
                    + "' AND LTHC.condition = '" + condition
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(LTHC.count) AS 'total', CAST(SUM(LTHC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = LTHC.LGACode AND P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGALTHC21 LTHC WHERE LTHC.indigenousStatus = 'non-indigenous' AND LTHC.sex = '"
                    + sex
                    + "' AND LTHC.condition = '" + condition + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // Get statistics about the gap between indigenous and non-indigenous people in
    // 2016 with specific school year
    public ArrayList<Info> getInfoBySchoolYear16(String sex, String schoolYear) {
        // Create an ArrayList of Info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL query and set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGASchoolCompletion16 SC WHERE SC.indigenousStatus = 'indigenous' AND SC.sex = '"
                    + sex
                    + "' AND SC.schoolYear= '" + schoolYear
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGASchoolCompletion16 SC WHERE SC.indigenousStatus = 'non-indigenous' AND SC.sex = '"
                    + sex
                    + "' AND SC.schoolYear = '" + schoolYear + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // Get statistics about the gap between indigenous and non-indigenous people in
    // 2021 with specific school year
    public ArrayList<Info> getInfoBySchoolYear21(String sex, String schoolYear) {
        // Create an ArrayList of Info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL query and set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGASchoolCompletion21 SC WHERE SC.indigenousStatus = 'indigenous' AND SC.sex = '"
                    + sex
                    + "' AND SC.schoolYear= '" + schoolYear
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGASchoolCompletion21 SC WHERE SC.indigenousStatus = 'non-indigenous' AND SC.sex = '"
                    + sex
                    + "' AND SC.schoolYear = '" + schoolYear + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // Get changes in school completion rate between 2016 and 2021
    public ArrayList<Change16To21> getChangeBySchoolYear(String indig, String sex, String schoolYear) {
        // Create an ArrayList to store the Change16To21 objects
        ArrayList<Change16To21> changeList = new ArrayList<Change16To21>();

        // Set up the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent', NI.percentage/I.percentage AS 'gap' FROM (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGASchoolCompletion16 SC WHERE SC.indigenousStatus = '"
                    + indig + "' AND SC.sex = '" + sex + "' AND SC.schoolYear = '" + schoolYear
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(SC.count) AS 'total', CAST(SUM(SC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = SC.LGACode AND P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGASchoolCompletion21 SC WHERE SC.indigenousStatus = '"
                    + indig + "' AND SC.sex = '" + sex + "' AND SC.schoolYear = '" + schoolYear
                    + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode ORDER BY gap DESC;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");
                double gap = results.getDouble("gap");

                // Create a new Change16To21 object with the data from the database
                Change16To21 change = new Change16To21(name, total, percentage, sum, percent, gap);

                // Add the Change16To21 object to the ArrayList
                changeList.add(change);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of Change16To21 objects
        return changeList;
    }

    // Get statistics about the gap between indigenous and non-indigenous people in
    // 2016 with specific level of non-school education
    public ArrayList<Info> getInfoByNonSchoolBracket16(String sex, String nonSchoolBracket) {
        // Create an ArrayList of Info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL query and set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGANonSchoolCompletion16 NSC WHERE NSC.indigenousStatus = 'indigenous' AND NSC.sex = '"
                    + sex
                    + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGANonSchoolCompletion16 NSC WHERE NSC.indigenousStatus = 'non-indigenous' AND NSC.sex = '"
                    + sex
                    + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // Get statistics about the gap between indigenous and non-indigenous people in
    // 2021 with specific level of non-school education
    public ArrayList<Info> getInfoByNonSchoolBracket21(String sex, String nonSchoolBracket) {
        // Create an ArrayList of Info objects
        ArrayList<Info> infoList = new ArrayList<Info>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL query and set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent' FROM (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = 'indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGANonSchoolCompletion21 NSC WHERE NSC.indigenousStatus = 'indigenous' AND NSC.sex = '"
                    + sex
                    + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = 'non-indigenous' AND P.sex = '"
                    + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGANonSchoolCompletion21 NSC WHERE NSC.indigenousStatus = 'non-indigenous' AND NSC.sex = '"
                    + sex
                    + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");

                // Create a new info object with the data from the database
                Info info = new Info(name, total, percentage, sum, percent);

                // Add the info object to the ArrayList
                infoList.add(info);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of info objects
        return infoList;
    }

    // Get changes in non-school completion rate between 2016 and 2021
    public ArrayList<Change16To21> getChangeByNonSchoolBracket(String indig, String sex, String nonSchoolBracket) {
        // Create an ArrayList to store the Change16To21 objects
        ArrayList<Change16To21> changeList = new ArrayList<Change16To21>();

        // Set up the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT I.name, I.total, I.percentage, NI.total AS 'sum', NI.percentage AS 'percent', NI.percentage/I.percentage AS 'gap' FROM (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation16 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA16 lga NATURAL JOIN LGANonSchoolCompletion16 NSC WHERE NSC.indigenousStatus = '"
                    + indig + "' AND NSC.sex = '" + sex + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) I JOIN (SELECT lga.LGACode, lga.name, SUM(NSC.count) AS 'total', CAST(SUM(NSC.count) AS FLOAT) / (SELECT SUM(P.count) FROM LGAPopulation21 P WHERE P.LGACode = NSC.LGACode AND P.indigenousStatus = '"
                    + indig + "' AND P.sex = '" + sex
                    + "') * 100 AS 'percentage' FROM LGA21 lga NATURAL JOIN LGANonSchoolCompletion21 NSC WHERE NSC.indigenousStatus = '"
                    + indig + "' AND NSC.sex = '" + sex + "' AND NSC.nonSchoolBracket= '" + nonSchoolBracket
                    + "' GROUP BY lga.LGACode) NI ON I.LGACode = NI.LGACode ORDER BY gap DESC;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String name = results.getString("name");
                int total = results.getInt("total");
                double percentage = results.getDouble("percentage");
                int sum = results.getInt("sum");
                double percent = results.getDouble("percent");
                double gap = results.getDouble("gap");

                // Create a new Change16To21 object with the data from the database
                Change16To21 change = new Change16To21(name, total, percentage, sum, percent, gap);

                // Add the Change16To21 object to the ArrayList
                changeList.add(change);
            }

            // Clost the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, print the error message
            System.err.println(e.getMessage());
        } finally {
            // Safety code to clean up
            try {
                // Close the connection if it is still open
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection close failed
                System.err.println(e.getMessage());
            }
        }

        // Return the ArrayList of Change16To21 objects
        return changeList;
    }
}
