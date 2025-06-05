package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 * 
 * @author Luong Thi Tra My, 2024. Email: s3987023@rmit.edu.vn
 * @author Nguyen Phuong Linh, 2024. Email: s4034535@rmit.edu.vn
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" +
                "<title>General Information about the Australian National Agreement on Closing the Gap</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add header content block
        html = html + """
                    <div class='header'>
                        <a href="#">
                            <img src='logo_ctg.png' class='top-image' alt='logo'>
                        </a>
                    </div>
                """;

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
                    <div class='topnav'>
                        <a href='/'>Home</a>
                        <a href='mission.html'>Our Mission</a>
                        <a href='page2A.html'>Age & Health</a>
                        <a href='page2B.html'>Education</a>
                        <a href='page3A.html'>Gap Change</a>
                        <a href='page3B.html'>Similar LGAs</a>
                    </div>
                """;


                html = html + """
                    <div class='header'>
                        <h1>Closing The Gap</h1>
                    </div>
                """;
        // Add Div for page Content
        html = html + "<div class='content'>";


        // Add HTML for the page content
        html = html
                + """
                        
                                                    <h2>17 Socioeconomic Outcomes</h2>
                                                    <br>
                                                    <p>The National Agreement on Closing the Gap has aimed to 17 socioeconomic outcomes across areas that have a positive impact on life for Aboriginal and Torres Strait Islander people. The progress against the targets will be monitored by the Productivity Commission and will help all parties to the National Agreement understand how their efforts are contributing to progress over next 10 years.</p>
                                                    <p>Below, we listed all 17 socioeconomic outcomes of the National Agreement on closing the gap. Especially, for this website, we will focus more on providing users with related information and analysis for two outcomes. We will highlight them here.</p>
                                                    <br>
                                                    <ol>
                                                    <li class='outcome'><strong>Aboriginal and Torres Strait Islander people enjoy long and healthy lives.</strong></li>
                                                    <li>Aboriginal and Torres Strait Islander children are born healthy and strong.</li>
                                                    <li>Aboriginal and Torres Strait Islander children are engaged in high quality, culturally appropriate early childhood education in their early years.</li>
                                                    <li>Aboriginal and Torres Strait Islander children thrive in their early years.</li>
                                                    <li class='outcome'><strong>Aboriginal and Torres Strait Islander students achieve their full learning potential.</strong></li>
                                                    <li>Aboriginal and Torres Strait Islander students reach their full potential through further education pathways.</li>
                                                    <li>Aboriginal and Torres Strait Islander youth are engaged in employment or education.</li>
                                                    <li>Strong economic participation and development of Aboriginal and Torres Strait Islander people and communities.</li>
                                                    <li>Aboriginal and Torres Strait Islander people secure appropriate, affordable housing that is aligned with their priorities and need.</li>
                                                    <li>Aboriginal and Torres Strait Islander people are not overrepresented in the criminal justice system.</li>
                                                    <li>Aboriginal and Torres Strait Islander young people are not overrepresented in the criminal justice system.</li>
                                                    <li>Aboriginal and Torres Strait Islander children are not overrepresented in the child protection system.</li>
                                                    <li>Aboriginal and Torres Strait Islander families and households are safe.</li>
                                                    <li>Aboriginal and Torres Strait Islander people enjoy high levels of social and emotional wellbeing.</li>
                                                    <li>Aboriginal and Torres Strait Islander people maintain a distinctive cultural, spiritual, physical and economic relationship with their land and waters.</li>
                                                    <li>Aboriginal and Torres Strait Islander cultures and languages are strong, supported and flourishing.</li>
                                                    <li>Aboriginal and Torres Strait Islander people have access to information and services enabling participation in informed decision-making regarding their own lives.</li>
                                                    </ol>
                            <br>
                                                    """;

        // Get the ArrayList of Strings of all states
        ArrayList<String> stateNames = getStates();

        // Get the ArrayList of the populations of states/territories
        ArrayList<Integer> totalPopulations16 = getPopulations16();

        // Add HTML for the population list
        html = html
                + """
                        <h2>The Total Population of each State and Territory in 2016 </h2>
                            <br>
                            <p>Below, we present the total population of each state and territory in 2016. The data is collected from the Australian Bureau of Statistics (ABS) and the Australian Institute of Health and Welfare (AIHW).</p>
                            <br>
                                    """;

        // Add HTML table element
        html = html + """
                            <table>
                            <tr>
                            <th>Name</th>
                            <th>Population</th>
                            </tr>
                """;

        // Finally we can print out the total population of each state/territory
        int i;
        for (i = 0; i < stateNames.size(); i++) {
            html = html + "<tr><td>" + stateNames.get(i) + "</td><td>" + totalPopulations16.get(i) + "</td></tr>";
        }

        // Finish the List HTML
        html = html + "</table>" + "<br>";

        // Get the ArrayList of the populations of states/territories in 2021
        ArrayList<Integer> totalPopulations21 = getPopulations21();

        // Add HTML for the population list
        html = html
                + """
                        <h2>The Total Population of each State and Territory in 2021 </h2>
                            <br>
                            <p>Below, we present the total population of each state and territory in 2021. The data is collected from the Australian Bureau of Statistics (ABS) and the Australian Institute of Health and Welfare (AIHW).</p>
                            <br>
                                    """;

        // Add HTML table element
        html = html + """
                                            <table>
                                            <tr>
                                            <th>Name</th>
                                            <th>Population</th>
                                            </tr>
                """;

        // Finally we can print out the total population of each state/territory
        int j;
        for (j = 0; j < stateNames.size(); j++) {
            html = html + "<tr><td>" + stateNames.get(j) + "</td><td>" + totalPopulations21.get(j) + "</td></tr>";
        }

        // Finish the List HTML
        html = html + "</table>" + "<br><hr>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
                    <div class='footer'>
                        <p id='footer-title' >Closing The Gap</p>
                        <br>
                        <p id='copyright'>Copyright 2024</p>
                    </div>
                """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    /**
     * Get the names of states/teritories in the database.
     */
    public ArrayList<String> getStates() {
        // Create the ArrayList of state objects to return
        ArrayList<String> states = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM State ";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String name = results.getString("stateName");

                // Add the state/territory object to the array
                states.add(name);
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

        // Finally we return all of the states/territories
        return states;
    }

    /**
     * Get the total population of states/teritories in 2016 .
     */
    public ArrayList<Integer> getPopulations16() {
        // Create the ArrayList of populations objects to return
        ArrayList<Integer> populations = new ArrayList<Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(LGAPopulation16.count) AS 'total'  FROM State NATURAL JOIN LGA16 NATURAL JOIN LGAPopulation16 GROUP BY State.stateCode";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int population = results.getInt("total");

                // Add the population object to the array
                populations.add(population);
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

        // Finally we return the populations of states/territories
        return populations;
    }

    /**
     * Get the total population of states/teritories in 2021.
     */
    public ArrayList<Integer> getPopulations21() {
        // Create the ArrayList of populations objects to return
        ArrayList<Integer> populations21 = new ArrayList<Integer>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(LGAPopulation21.count) AS 'total'  FROM State NATURAL JOIN LGA21 NATURAL JOIN LGAPopulation21 GROUP BY State.stateCode";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int population21 = results.getInt("total");

                // Add the population object to the array
                populations21.add(population21);
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

        // Finally we return the populations of states/territories
        return populations21;
    }
}
