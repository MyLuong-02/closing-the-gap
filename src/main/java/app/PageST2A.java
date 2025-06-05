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
 * using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Luong Thi Tra My, 2024. Email: s3987023@rmit.edu.vn
 * @author Nguyen Phuong Linh, 2024. Email: s4034535@rmit.edu.vn
 */
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Age and Health Statistics </title>";

        // Add some CSS (external file)
        html = html + "<script src='https://code.jquery.com/jquery-3.6.4.min.js'></script>";
        html = html + "<script src='script.js'></script>";
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add logo
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

        // Add header content block
        html = html + """
                    <div class='header'>
                        <h1>Age & Health</h1>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html
                + """

                        <h2>Age Demographic</h2>
                            <p>Use the filter to view statistics of various age demographics in different LGAs/States/Territory.</p>

                            """;

        // Add html for the web form
        html = html + "<form action='page2A.html' method='post' class='form2'>";

        html = html + "<dif class='form-group'>";
        html = html + "<h2>Filter (Age Groups)</h2>";
        html = html + "<br>";
        html = html + "<label for='view'>View By: </label>";
        html = html + "<select name='view' id='view'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='LGA'>LGA</option>";
        html = html + "<option value='state'>State/Territory</option>";
        html = html + "</select>";
        html = html + "</dif>";

        // Get all indigenous status
        ArrayList<String> indigenousStatus = getIndigenousStatus();
        html = html + "<br>";
        html = html + "<dif class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='indig'>Indigenous Status: </label>";
        html = html + "<select name='indig' id='indig'>";
        html = html + "<option value=''>Select</option>";
        for (String indig : indigenousStatus) {
            html = html + "<option value='" + indig + "'>" + indig + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        html = html + "<dif class='form-group'>";

        // get age
        ArrayList<String> ages = getAgeRange();
        html = html + "<br>";
        html = html + "<label for='age'class='option'>Age Range: </label>";
        html = html + "<select name='age' id='age' class='option'>";
        html = html + "<option value=''>Select</option>";
        for (String age : ages) {
            html = html + "<option value='" + age + "'>" + age + "</option>";
        }
        html = html + "</select>";
        html = html + "</dif>";

        html = html + "<dif class='form-group'>";

        // Add options for users to choose to sort by raw values or proportional values
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='sort'>Display as: </label>";
        html = html + "<select name='sort' id='sort'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='raw'class='option'>Raw Values</option>";
        html = html + "<option value='percentage'class='option'>Percentage</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Add options for users whether sorts result in ascending or descending order
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='orderbutton'>Sort By: </label>";
        html = html + "<input type='radio' id='ASC' name='order' value='ASC' >Ascending";
        html = html + "<input type='radio' id='DESC' name='order' value='DESC' checked>Descending";
        html = html + "<br>";
        html = html + "</div>";

        // Add the submit button
        html = html + "   <button type='submit' class='btn btn-primary filter'>Sort & Filter</button>";

        html = html + "</form>";

        html = html + "<br>";
        // Close Content div
        html = html + "</div>";

        html = html + "<div class='content'>";
        // Get the form data
        String view = context.formParam("view");
        String indig = context.formParam("indig");
        String age = context.formParam("age");
        String sort = context.formParam("sort");
        String order = context.formParam("order");

        // If the form data is null
        if (view == null || indig == null || age == null || sort == null
                || order == null) {
            // nothing shows. Therefore, we make some result in HTML to inform users of this
            // situation
            html = html + "<h3><i>You haven't selected any category. Please choose them to get the result!</i></h3>";
        } else {
            // If the form data is not null
            // Output the statistics

            if (view.equals("LGA")) { // If users want to view the result by LGA
                if (sort.equals("raw") && order.equals("ASC")) { // If users want to sort the result by raw
                    // values in ascending order
                    html = html + outputAgeByRawValuesASC(indig, age);
                } else if (sort.equals("raw") && order.equals("DESC")) { // If users want to sort the result
                    // by raw values in descending order
                    html = html + outputAgeByRawValuesDESC(indig, age);
                } else if (sort.equals("percentage") && order.equals("ASC")) { // If users want to sort the
                                                                               // result by percentage in
                                                                               // ascending order
                    html = html + outputAgeByPercentage21ASC(indig, age);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputAgeByPercentage21DESC(indig, age);
                }
            } else { // If users want to view the result by state/territory
                if (sort.equals("raw") && order.equals("ASC")) { // If users want to sort the result by raw
                                                                 // values in ascending order
                    html = html + outputAgeByStateByRawValuesASC(indig, age);
                } else if (sort.equals("raw") && order.equals("DESC")) { // If users want to sort the result
                                                                         // by raw values in descending order
                    html = html + outputAgeByStateByRawValuesDESC(indig, age);
                } else if (sort.equals("percentage") && order.equals("ASC")) { // If users want to sort the
                                                                               // result by percentage in
                                                                               // ascending order
                    html = html + outputAgeByStateByPercentage21ASC(indig, age);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputAgeByStateByPercentage21DESC(indig, age);
                }
            }
        }
        html = html + "</div>";

        html = html + "<div class='content'>";
        html = html
                + """

                        <h2>Health Conditions</h2>
                            <p>Use the filter to view statistics of long-term health conditions in Australia.</p>
                            """;

        html = html + "</div>";

        html = html + "<div class='content'>";
        html = html + "<form action='page2A.html' method='post' class='form2'>";
        html = html + "<dif class='form-group'>";
        html = html + "<h2>Filter (Health Conditions)</h2>";
        html = html + "<br>";
        html = html + "<label for='view_by'>View By: </label>";
        html = html + "<select name='view_by' id='view_by'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='LGA'>LGA</option>";
        html = html + "<option value='state'>State/Territory</option>";
        html = html + "</select>";
        html = html + "</dif>";

        // Get all indigenous status
        html = html + "<br>";
        html = html + "<dif class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='indigenous_status'>Indigenous Status: </label>";
        html = html + "<select name='indigenous_status' id='indigenous_status'>";
        html = html + "<option value=''>Select</option>";
        for (String status : indigenousStatus) {
            html = html + "<option value='" + status + "'>" + status + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        html = html + "<dif class='form-group'>";

        // get all health conditions
        ArrayList<String> conditions = getCondition();
        html = html + "<br>";
        html = html + "<label for='condition'class='option'>Health Condition: </label>";
        html = html + "<select name='condition' id='condition' class='option'>";
        html = html + "<option value=''>Select</option>";
        for (String condition : conditions) {
            html = html + "<option value='" + condition + "'>" + condition + "</option>";
        }
        html = html + "</select>";
        html = html + "</dif>";

        html = html + "<dif class='form-group'>";

        // Add options for users to choose to sort by raw values or proportional values
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='sort_by'>Display as: </label>";
        html = html + "<select name='sort_by' id='sort_by'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='raw'class='option'>Raw Values</option>";
        html = html + "<option value='percentage'class='option'>Percentage</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Add options for users whether sorts result in ascending or descending order
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='orderbutton'>Sort By: </label>";
        html = html + "<input type='radio' id='ASC' name='orderbutton' value='ASC' >Ascending";
        html = html + "<input type='radio' id='DESC' name='orderbutton' value='DESC' checked>Descending";
        html = html + "<br>";
        html = html + "</div>";

        // Add the submit button
        html = html + "   <button type='submit' class='btn btn-primary filter'>Sort & Filter</button>";
        html = html + "</form>";

        html = html + "<br>";
        html = html + "</div>";

        html = html + "<div class='content'>";

        // Get the form data
        String viewstate = context.formParam("view_by");
        String status = context.formParam("indigenous_status");
        String condition = context.formParam("condition");
        String sorting = context.formParam("sort_by");
        String orderbutton = context.formParam("orderbutton");

        // If the form data is null
        if (viewstate == null || status == null || condition == null || sorting == null
                || orderbutton == null) {
            // nothing shows. Therefore, we make some result in HTML to inform users of this
            // situation

            html = html + "<h3><i>You haven't selected any category. Please choose them to get the result!</i></h3>";
        } else {
            // If the form data is not null
            // Output the statistics

            if (viewstate.equals("LGA")) { // If users want to view the result by LGA
                if (sorting.equals("raw") && orderbutton.equals("ASC")) { // If users want to sort the result by raw
                    // values in ascending order
                    html = html + outputByHealthByRawValuesASC(status, condition);
                } else if (sorting.equals("raw") && orderbutton.equals("DESC")) { // If users want to sort the result
                    // by raw values in descending order
                    html = html + outputByHealthByRawValuesDESC(status, condition);
                } else if (sorting.equals("percentage") && orderbutton.equals("ASC")) { // If users want to sort the
                                                                                        // result by percentage in
                                                                                        // ascending order
                    html = html + outputByHealthByPercentageASC(status, condition);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputByHealthByPercentageDESC(status, condition);
                }
            } else { // If users want to view the result by state/territory
                if (sorting.equals("raw") && orderbutton.equals("ASC")) { // If users want to sort the result by raw
                                                                          // values in ascending order
                    html = html + outputByHealthByStateByRawValuesASC(status, condition);
                } else if (sorting.equals("raw") && orderbutton.equals("DESC")) { // If users want to sort the result
                                                                                  // by raw values in descending order
                    html = html + outputByHealthByStateByRawValuesDESC(status, condition);
                } else if (sorting.equals("percentage") && orderbutton.equals("ASC")) { // If users want to sort the
                                                                                        // result by percentage in
                                                                                        // ascending order
                    html = html + outputByHealthByStateByPercentageASC(status, condition);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputByHealthByStateByPercentageDESC(status, condition);
                }
            }
        }
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

    // Output HTML page show age of each LGA in 2021 by raw values in
    // ascending order
    public String outputAgeByRawValuesASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people aged " + category
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages = ahdb.sortAgeByRawValuesASC(indig, category);
        int i = 1;
        for (Age age : ages) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + age.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output HTML page show age demographic of each LGA in 2021 by raw values in
    // descending order
    public String outputAgeByRawValuesDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people aged " + category
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages = ahdb.sortAgeByRawValuesDESC(indig, category);
        int i = 1;
        for (Age age : ages) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + age.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output HTML page show age demographic of each LGA in 2021 by percentage in
    // ascending order
    public String outputAgeByPercentage21ASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people aged " + category
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages = ahdb.sortAgeByPercentageASC(indig, category);
        int i = 1;
        for (Age age : ages) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            if (age.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", age.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";
        html = html + """

                <p><em>Note: N/A in Percentage column means that there is no data available for it.</em></p>
                <br>
                <hr>
                        """;

        // Return html
        return html;
    }

    // Output HTML page show age demographic of each LGA in 2021 by percentage in
    // descending order
    public String outputAgeByPercentage21DESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people aged " + category
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages = ahdb.sortAgeByPercentageDESC(indig, category);
        int i = 1;
        for (Age age : ages) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            if (age.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", age.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";
        html = html + """
                <p><em>Note: N/A in Percentage column means that there is no data available for it.</em></p>
                <br>
                <hr>
                    """;

        // Return html
        return html;
    }

    // Output html page show age demographic of each state/territory in 2021 by
    // raw values in ascending order
    public String outputAgeByStateByRawValuesASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people aged " + category
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages2 = ahdb.sortAgeByIndigByCategoryByStateByRawValuesASC(indig, category);
        int i = 1;
        for (Age age : ages2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + age.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show age demographic of each state/territory in 2021 by
    // raw values in descending order
    public String outputAgeByStateByRawValuesDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people aged " + category
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages2 = ahdb.sortAgeByIndigByCategoryByStateByRawValueDESC(indig, category);
        int i = 1;
        for (Age age : ages2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + age.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show age demographic of each state/territory in 2021 by
    // percentage in ascending order
    public String outputAgeByStateByPercentage21ASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people aged " + category
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages2 = ahdb.sortAgeByIndigByCategoryByStateByPercentageASC(indig, category);
        int i = 1;
        for (Age age : ages2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + String.format("%.2f", age.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show age demographic of each state/territory in 2021 by
    // percentage in descending order
    public String outputAgeByStateByPercentage21DESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people aged " + category
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Age</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Age> ages2 = ahdb.sortAgeByIndigByCategoryByStateByPercentageDESC(indig,
                category);
        int i = 1;
        for (Age age : ages2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + age.getName() + "</td>";
            html = html + "<td>" + age.getIndigenousStatus() + "</td>";
            html = html + "<td>" + age.getAgeRange() + "</td>";
            html = html + "<td>" + String.format("%.2f", age.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Get all indigenous status in the database
    public ArrayList<String> getIndigenousStatus() {
        // Creat the ArrayList to return
        ArrayList<String> indigenousStatus = new ArrayList<String>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT indigenousStatus FROM LGAPopulation21 EXCEPT SELECT indigenousStatus FROM LGAPopulation21 WHERE indigenousStatus = 'indigenous not stated'";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String indigenous = results.getString("indigenousStatus");

                // Add the School Object to the ArrayList
                indigenousStatus.add(indigenous);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Return the ArrayList
        return indigenousStatus;
    }

    // Output the result of sorting health conditions of each LGA in 2021 by raw
    // values in ascending order
    public String outputByHealthByRawValuesASC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people with " + condition
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByRawValuesASC(indig, condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + health.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each LGA in 2021 by raw
    // values in descending order
    public String outputByHealthByRawValuesDESC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people with " + condition
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByRawValuesDESC(indig, condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + health.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each LGA in 2021 by
    // percentage in ascending order
    public String outputByHealthByPercentageASC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people with " + condition
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByPercentageASC(indig, condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            if (health.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", health.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + """

                <br>
                <p><em>Note: N/A in Percentage column means that there is no data available for it.</em></p>
                <br>
                <hr>
                        """;

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each LGA in 2021 by
    // percentage in descending order
    public String outputByHealthByPercentageDESC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people with " + condition
                + " in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByPercentageDESC(indig, condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            if (health.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", health.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + """
                <br>
                <p><em>Note: N/A in Percentage column means that there is no data available for it.</em></p>
                <br>
                <hr>
                    """;

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each state/territory in
    // 2021 by raw values in ascending order
    public String outputByHealthByStateByRawValuesASC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people with " + condition
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByRawValuesByStateASC(indig,
                condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + health.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each state/territory in
    // 2021 by raw values in descending order
    public String outputByHealthByStateByRawValuesDESC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people with " + condition
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByRawValuesByStateDESC(indig,
                condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + health.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each state/territory in
    // 2021 by percentage in ascending order
    public String outputByHealthByStateByPercentageASC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people with " + condition
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByPercentageByStateASC(indig,
                condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + String.format("%.2f", health.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting health conditions of each state/territory in
    // 2021 by percentage in descending order
    public String outputByHealthByStateByPercentageDESC(String indig, String condition) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people with " + condition
                + " in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Health Condition</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use AHDBConnection to add html and data for the table
        AHDBConnection ahdb = new AHDBConnection();
        ArrayList<Health> healths = ahdb.sortByHealthByPercentageByStateDESC(indig,
                condition);
        int i = 1;
        for (Health health : healths) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + health.getName() + "</td>";
            html = html + "<td>" + health.getIndigenousStatus() + "</td>";
            html = html + "<td>" + health.getCondition() + "</td>";
            html = html + "<td>" + String.format("%.2f", health.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Get all age range in the database
    public ArrayList<String> getAgeRange() {
        // Creat the ArrayList of string to return
        ArrayList<String> ageRanges = new ArrayList<String>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT ageCategory FROM LGAPopulation21";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String ageRange = results.getString("ageCategory");

                // Add the age range to the ArrayList
                ageRanges.add(ageRange);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Return the ArrayList
        return ageRanges;
    }

    // Get all health conditions in the database
    public ArrayList<String> getCondition() {
        // Creat the ArrayList of all health conditions to return
        ArrayList<String> conditions = new ArrayList<String>();

        // Setup the variable for the AHDB connection
        Connection connection = null;

        try {
            // Connect to AHDB data base
            connection = DriverManager.getConnection(AHDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT condition FROM LGALTHC21";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String condition = results.getString("condition");

                // Add the health condition to the ArrayList
                conditions.add(condition);
            }

            // Close the connection
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Return the ArrayList
        return conditions;
    }

}
