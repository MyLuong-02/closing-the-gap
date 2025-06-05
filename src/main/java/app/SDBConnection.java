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

public class SDBConnection {
    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a SDB Object so we can keep talking to the database
     */
    public SDBConnection() {
        System.out.println("Created SDB Connection Object");
    }

    /**
     * Sort all of the LGAs by raw values of people who
     * have completed a specific level of school in 2021 in the database in
     * ascending order.
     * 
     * @return
     *         Returns an ArrayList of School objects
     */
    public ArrayList<School> sortSchoolCompletion21ByRawValuesASC(String indig, String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, SC.indigenousStatus, SC.schoolYear, SUM(SC.count) AS 'sum', CAST(SUM(SC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, SC.indigenousStatus, SC.schoolYear, 0 AS 'sum', 'NaN' AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    /**
     * Sort all of the LGAs by raw values of people who
     * have completed a specific level of school in 2021 in the database in
     * descending order.
     * 
     * @return
     *         Returns an ArrayList of School objects
     */
    public ArrayList<School> sortSchoolCompletion21ByRawValuesDESC(String indig, String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, SC.indigenousStatus, SC.schoolYear, SUM(SC.count) AS 'sum', CAST(SUM(SC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, SC.indigenousStatus, SC.schoolYear, 0 AS 'sum', 'NaN' AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    /**
     * Sort all of the LGAs by proportional values of people who
     * have completed a specific level of school in 2021 in the database in
     * ascending order.
     * 
     * @return
     *         Returns an ArrayList of School objects
     */
    public ArrayList<School> sortSchoolCompletion21ByPercentageASC(String indig, String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, SC.indigenousStatus, SC.schoolYear, SUM(SC.count) AS 'sum', CAST(SUM(SC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, SC.indigenousStatus, SC.schoolYear, 0 AS 'sum', 0.00 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage";
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    /**
     * Sort all of the LGAs by percentage values of people who
     * have completed a specific level of school in 2021 in the database in
     * descending order.
     * 
     * @return
     *         Returns an ArrayList of School objects
     */
    public ArrayList<School> sortSchoolCompletion21ByPercentageDESC(String indig, String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, SC.indigenousStatus, SC.schoolYear, SUM(SC.count) AS 'sum', CAST(SUM(SC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, SC.indigenousStatus, SC.schoolYear, 0 AS 'sum', 0.00 AS 'percentage' FROM LGASchoolCompletion21 SC JOIN PopulationByIndig21 P ON SC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = P.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    // Sort all of the states/territories by raw values of people who have completed
    // a specific level of school in 2021 in the database.
    public ArrayList<School> sortSchoolCompletion21ByIndigByCategoryByStateByRawValuesASC(String indig,
            String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SC.indigenousStatus, SC.schoolYear, SC.Total, CAST(SC.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesSchoolCompletionByIndig21 SC JOIN StatesPopulationByIndig21 S ON SC.stateCode = S.stateCode WHERE (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = S.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY S.stateCode ORDER BY SC.Total";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    // Sort all of the states/territories by raw values of people who have completed
    // a specific level of school in 2021 in the database in descending order.
    public ArrayList<School> sortSchoolCompletion21ByIndigByCategoryByStateByRawValuesDESC(String indig,
            String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SC.indigenousStatus, SC.schoolYear, SC.Total, CAST(SC.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesSchoolCompletionByIndig21 SC JOIN StatesPopulationByIndig21 S ON SC.stateCode = S.stateCode WHERE (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = S.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY S.stateCode ORDER BY SC.Total DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    // Sort all of the states/territories by percentage values of people who have
    // completed
    // a specific level of school in 2021 in the database in ascending order.
    public ArrayList<School> sortSchoolCompletion21ByIndigByCategoryByStateByPercentageASC(String indig,
            String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SC.indigenousStatus, SC.schoolYear, SC.Total, CAST(SC.Total AS FLOAT) / S.Total * 100 AS 'Percentage' FROM StatesSchoolCompletionByIndig21 SC JOIN StatesPopulationByIndig21 S ON SC.stateCode = S.stateCode WHERE (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = S.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY S.stateCode ORDER BY Percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    // Sort all of the states/territories by percentage values of people who have
    // completed
    // a specific level of school in 2021 in the database in descending order.
    public ArrayList<School> sortSchoolCompletion21ByIndigByCategoryByStateByPercentageDESC(String indig,
            String category) {
        // Creat the ArrayList of School objects to return
        ArrayList<School> schools = new ArrayList<School>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SC.indigenousStatus, SC.schoolYear, SC.Total, CAST(SC.Total AS FLOAT) / S.Total * 100 AS 'Percentage' FROM StatesSchoolCompletionByIndig21 SC JOIN StatesPopulationByIndig21 S ON SC.stateCode = S.stateCode WHERE (SC.indigenousStatus = '"
                    + indig + "' AND SC.indigenousStatus = S.indigenousStatus) AND SC.schoolYear = '" + category
                    + "' GROUP BY S.stateCode ORDER BY Percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String schoolYear = results.getString("schoolYear");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a School Object
                School school = new School(name, indigenousStatus, schoolYear, count, percentage);

                // Add the School Object to the ArrayList
                schools.add(school);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of School Objects
        return schools;
    }

    // Sort all of the LGAs by raw values in ascending order to show the number of
    // people who completed a specific level outside of school system in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByRawValuesASC(String indig, String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, SUM(NSC.count) AS 'sum', CAST(SUM(NSC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, 0 AS 'sum', 'NaN' AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the LGAs by raw values in descending order to show the number of
    // people who completed a specific level outside of school system in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByRawValuesDESC(String indig, String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, SUM(NSC.count) AS 'sum', CAST(SUM(NSC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, 0 AS 'sum', 'NaN' AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the LGAs by percentage values in ascending order to show the
    // percentage of people who completed a specific level outside of school system
    // over the particular population in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByPercentageASC(String indig, String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, SUM(NSC.count) AS 'sum', CAST(SUM(NSC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, 0 AS 'sum', 0.00 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the LGAs by proportional values in descending order to show the
    // percentage of people who completed a specific level outside of school system
    // in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByPercentageDESC(String indig, String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, SUM(NSC.count) AS 'sum', CAST(SUM(NSC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, NSC.indigenousStatus, NSC.nonSchoolBracket, 0 AS 'sum', 0.00 AS 'percentage' FROM LGANonSchoolCompletion21 NSC JOIN PopulationByIndig21 P ON NSC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = P.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("sum");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the states/territories by raw values in ascending order to show
    // the number of people who completed a specific level outside of school system
    // in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByIndigByCategoryByStateByRawValuesASC(String indig,
            String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, NSC.indigenousStatus, NSC.nonSchoolBracket, NSC.Total, CAST(NSC.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesNonSchoolCompletionByIndig21 NSC JOIN StatesPopulationByIndig21 S ON NSC.stateCode = S.stateCode WHERE (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = S.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY S.stateCode ORDER BY NSC.Total";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the states/territories by raw values in descending order to show
    // the number of people who completed a specific level outside of school system
    // in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByIndigByCategoryByStateByRawValuesDESC(String indig,
            String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, NSC.indigenousStatus, NSC.nonSchoolBracket, NSC.Total, CAST(NSC.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesNonSchoolCompletionByIndig21 NSC JOIN StatesPopulationByIndig21 S ON NSC.stateCode = S.stateCode WHERE (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = S.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY S.stateCode ORDER BY NSC.Total DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the states/territories by percentage values in ascending order to
    // show the percentage of people who completed a specific level outside of
    // school system over the particular population in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByIndigByCategoryByStateByPercentageASC(String indig,
            String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, NSC.indigenousStatus, NSC.nonSchoolBracket, NSC.Total, CAST(NSC.Total AS FLOAT) / S.Total * 100 AS 'Percentage' FROM StatesNonSchoolCompletionByIndig21 NSC JOIN StatesPopulationByIndig21 S ON NSC.stateCode = S.stateCode WHERE (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = S.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY S.stateCode ORDER BY Percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }

    // Sort all of the states/territories by percentage values in descending order
    // to show the percentage of people who completed a specific level outside of
    // school system in 2021
    public ArrayList<Nonschool> sortNonSchoolCompletion21ByIndigByCategoryByStateByPercentageDESC(String indig,
            String category) {
        // Creat the ArrayList of Nonschool objects to return
        ArrayList<Nonschool> nonschools = new ArrayList<Nonschool>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, NSC.indigenousStatus, NSC.nonSchoolBracket, NSC.Total, CAST(NSC.Total AS FLOAT) / S.Total * 100 AS 'Percentage' FROM StatesNonSchoolCompletionByIndig21 NSC JOIN StatesPopulationByIndig21 S ON NSC.stateCode = S.stateCode WHERE (NSC.indigenousStatus = '"
                    + indig + "' AND NSC.indigenousStatus = S.indigenousStatus) AND NSC.nonSchoolBracket = '" + category
                    + "' GROUP BY S.stateCode ORDER BY Percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String nonSchoolBracket = results.getString("nonSchoolBracket");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a Nonschool Object
                Nonschool nonschool = new Nonschool(name, indigenousStatus, nonSchoolBracket, count, percentage);

                // Add the Nonschool Object to the ArrayList
                nonschools.add(nonschool);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
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

        // Return the ArrayList of Nonschool Objects
        return nonschools;
    }
}
