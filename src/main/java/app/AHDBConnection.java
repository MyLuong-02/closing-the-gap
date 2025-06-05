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

public class AHDBConnection {
    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a AHDB Object so we can keep talking to the database
     */
    public AHDBConnection() {
        System.out.println("Created AHDB Connection Object");
    }

    /**
     * Sort all of the LGAs by raw values of people at a specific age range in 2021
     * in the database in
     * ascending order.
     * 
     * @return
     *         Returns an ArrayList of Age objects
     */
    public ArrayList<Age> sortAgeByRawValuesASC(String indig, String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, SUM(LGAP.count) AS 'sum', CAST(SUM(LGAP.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, 0 AS 'sum', 0 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    /**
     * Sort all of the LGAs by raw values of people at a specific age range in 2021
     * in the database in
     * descending order.
     * 
     * @return
     *         Returns an ArrayList of Age objects
     */
    public ArrayList<Age> sortAgeByRawValuesDESC(String indig, String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, SUM(LGAP.count) AS 'sum', CAST(SUM(LGAP.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, 0 AS 'sum', 0 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode ORDER BY sum DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    /**
     * Sort all of the LGAs by proportional values of people at a specific age range
     * in 2021 in the database in
     * ascending order.
     * 
     * @return
     *         Returns an ArrayList of Age objects
     */
    public ArrayList<Age> sortAgeByPercentageASC(String indig, String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, SUM(LGAP.count) AS 'sum', CAST(SUM(LGAP.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, 0 AS 'sum', 0 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage ASC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    /**
     * Sort all of the LGAs by percentage values of people at a specific age range
     * in 2021 in the database in
     * descending order.
     * 
     * @return
     *         Returns an ArrayList of Age objects
     */
    public ArrayList<Age> sortAgeByPercentageDESC(String indig, String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, SUM(LGAP.count) AS 'sum', CAST(SUM(LGAP.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, LGAP.indigenousStatus, LGAP.ageCategory, 0 AS 'sum', 0 AS 'percentage' FROM LGAPopulation21 LGAP JOIN PopulationByIndig21 P ON LGAP.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (LGAP.indigenousStatus = '"
                    + indig + "' AND LGAP.indigenousStatus = P.indigenousStatus) AND LGAP.ageCategory= '" + category
                    + "' GROUP BY P.LGACode ORDER BY percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    // Sort all of the states/territories by raw values of people at a specific age
    // range in 2021 in the database.
    public ArrayList<Age> sortAgeByIndigByCategoryByStateByRawValuesASC(String indig,
            String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SP.indigenousStatus, SP.ageCategory, SP.Total, CAST(SP.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByAgeByIndig21 SP JOIN StatesPopulationByIndig21 S ON SP.stateCode = S.stateCode WHERE (SP.indigenousStatus = '"
                    + indig + "' AND SP.indigenousStatus = S.indigenousStatus) AND SP.ageCategory= '" + category
                    + "' GROUP BY S.stateCode ORDER BY SP.Total";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    // Sort all of the states/territories by raw values of people at a specific age
    // range in 2021 in the database in descending order.
    public ArrayList<Age> sortAgeByIndigByCategoryByStateByRawValueDESC(String indig,
            String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SP.indigenousStatus, SP.ageCategory, SP.Total, CAST(SP.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByAgeByIndig21 SP JOIN StatesPopulationByIndig21 S ON SP.stateCode = S.stateCode WHERE (SP.indigenousStatus = '"
                    + indig + "' AND SP.indigenousStatus = S.indigenousStatus) AND SP.ageCategory= '" + category
                    + "' GROUP BY S.stateCode ORDER BY SP.Total DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    // Sort all of the states/territories by percentage values of people at a
    // specific age range in 2021 in the database in ascending order.
    public ArrayList<Age> sortAgeByIndigByCategoryByStateByPercentageASC(String indig,
            String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SP.indigenousStatus, SP.ageCategory, SP.Total, CAST(SP.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByAgeByIndig21 SP JOIN StatesPopulationByIndig21 S ON SP.stateCode = S.stateCode WHERE (SP.indigenousStatus = '"
                    + indig + "' AND SP.indigenousStatus = S.indigenousStatus) AND SP.ageCategory= '" + category
                    + "' GROUP BY S.stateCode ORDER BY percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    // Sort all of the states/territories by percentage values of people at a
    // specific age range in 2021 in the database in descending order.
    public ArrayList<Age> sortAgeByIndigByCategoryByStateByPercentageDESC(String indig,
            String category) {
        // Creat the ArrayList of Age objects to return
        ArrayList<Age> ages = new ArrayList<Age>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT S.stateName, SP.indigenousStatus, SP.ageCategory, SP.Total, CAST(SP.Total AS FLOAT) / S.Total * 100 AS 'Percentage' FROM StatesByAgeByIndig21 SP JOIN StatesPopulationByIndig21 S ON SP.stateCode = S.stateCode WHERE (SP.indigenousStatus = '"
                    + indig + "' AND SP.indigenousStatus = S.indigenousStatus) AND SP.ageCategory= '" + category
                    + "' GROUP BY S.stateCode ORDER BY Percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String ageRange = results.getString("ageCategory");
                int count = results.getInt("Total");
                double percentage = results.getDouble("Percentage");

                // Create a Age Object
                Age age = new Age(name, indigenousStatus, ageRange, count, percentage);

                // Add the Age Object to the ArrayList
                ages.add(age);
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

        // Return the ArrayList of Age Objects
        return ages;
    }

    // Sort all LGA by raw values of people who have health conditions in 2021 in
    // the database in ascending order.
    public ArrayList<Health> sortByHealthByRawValuesASC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, HC.indigenousStatus, HC.condition, SUM(HC.count) AS 'sum', CAST(SUM(HC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, HC.indigenousStatus, HC.condition, 0 AS 'sum', 0 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode ORDER BY sum";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all LGA by raw values of people who have health conditions in 2021 in
    // the database in descending order.
    public ArrayList<Health> sortByHealthByRawValuesDESC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, HC.indigenousStatus, HC.condition, SUM(HC.count) AS 'sum', CAST(SUM(HC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, HC.indigenousStatus, HC.condition, 0 AS 'sum', 0 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode ORDER BY sum DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all LGA by percentage values of people who have health conditions in
    // 2021 in the database in ascending order.
    public ArrayList<Health> sortByHealthByPercentageASC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, HC.indigenousStatus, HC.condition, SUM(HC.count) AS 'sum', CAST(SUM(HC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, HC.indigenousStatus, HC.condition, 0 AS 'sum', 0 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode ORDER BY percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all LGA by percentage values of people who have health conditions in
    // 2021 in the database in descending order.
    public ArrayList<Health> sortByHealthByPercentageDESC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga.name, HC.indigenousStatus, HC.condition, SUM(HC.count) AS 'sum', CAST(SUM(HC.count) AS FLOAT) / P.total * 100 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total > 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode UNION SELECT lga.name, HC.indigenousStatus, HC.condition, 0 AS 'sum', 0 AS 'percentage' FROM LGALTHC21 HC JOIN PopulationByIndig21 P ON HC.LGACode = P.LGACode NATURAL JOIN LGA21 lga WHERE P.total = 0 AND (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = P.indigenousStatus) AND HC.condition = '" + condition
                    + "' GROUP BY P.LGACode ORDER BY percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("name");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("sum");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all states/territories by raw values of people who have health
    // conditions in 2021 in the database in ascending order.
    public ArrayList<Health> sortByHealthByRawValuesByStateASC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT S.stateName, HC.indigenousStatus, HC.condition, hc.Total, CAST(hc.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByHealthByIndig21 HC JOIN StatesPopulationByIndig21 S ON HC.stateCode = S.stateCode WHERE (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = S.indigenousStatus) AND HC.condition= '" + condition
                    + "' GROUP BY S.stateCode ORDER BY HC.Total";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all states/territories by raw values of people who have health
    // conditions in 2021 in the database in descending order.
    public ArrayList<Health> sortByHealthByRawValuesByStateDESC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT S.stateName, HC.indigenousStatus, HC.condition, hc.Total, CAST(hc.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByHealthByIndig21 HC JOIN StatesPopulationByIndig21 S ON HC.stateCode = S.stateCode WHERE (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = S.indigenousStatus) AND HC.condition= '" + condition
                    + "' GROUP BY S.stateCode ORDER BY HC.Total DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all states/territories by percentage values of people who have health
    // conditions in 2021 in the database in ascending order.
    public ArrayList<Health> sortByHealthByPercentageByStateASC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT S.stateName, HC.indigenousStatus, HC.condition, hc.Total, CAST(hc.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByHealthByIndig21 HC JOIN StatesPopulationByIndig21 S ON HC.stateCode = S.stateCode WHERE (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = S.indigenousStatus) AND HC.condition= '" + condition
                    + "' GROUP BY S.stateCode ORDER BY percentage";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }

    // Sort all states/territories by percentage values of people who have health
    // conditions in 2021 in the database in descending order.
    public ArrayList<Health> sortByHealthByPercentageByStateDESC(String indig, String condition) {
        // Creat the ArrayList of Health objects to return
        ArrayList<Health> healths = new ArrayList<Health>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT S.stateName, HC.indigenousStatus, HC.condition, hc.Total, CAST(hc.Total AS FLOAT) / S.Total * 100 AS 'percentage' FROM StatesByHealthByIndig21 HC JOIN StatesPopulationByIndig21 S ON HC.stateCode = S.stateCode WHERE (HC.indigenousStatus = '"
                    + indig + "' AND HC.indigenousStatus = S.indigenousStatus) AND HC.condition= '" + condition
                    + "' GROUP BY S.stateCode ORDER BY percentage DESC";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("stateName");
                String indigenousStatus = results.getString("indigenousStatus");
                String healthCondition = results.getString("condition");
                int count = results.getInt("Total");
                double percentage = results.getDouble("percentage");

                // Create a Health Object
                Health health = new Health(name, indigenousStatus, healthCondition, count, percentage);

                // Add the Health Object to the ArrayList
                healths.add(health);
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

        // Return the ArrayList of Health Objects
        return healths;
    }
}
