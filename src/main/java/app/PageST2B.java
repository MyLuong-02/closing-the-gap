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
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Luong Thi Tra My, 2024. Email: s3987023@rmit.edu.vn
 * @Author Nguyen Phuong Linh, 2024. Email:
 */
public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Education</title>";

        // Add some CSS (external file)
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
                        <h1 class='data'>School & Non-school Completion in 2021</h1>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html
                + """
                        <br>
                        <h2>School Level</h2>
                        <p><p>Use the filter to view statistics about the number of people as well as its percentage who completed a specific level of education in each Australian local government area or state/territory in 2021.</p></p>
                        """;

        // Add html for the web form
        html = html + "<form action='page2B.html' method='post' class='form2'>";

        // Get two options for viewing results
        html = html + "<dif class='form-group'>";
        html = html + "<h2>Filter (School)</h2>";
        html = html + "<br>";
        html = html + "<label for='view_drop'>View By: </label>";
        html = html + "<select name='view_drop' id='view_drop'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='LGA'>LGA</option>";
        html = html + "<option value='state'>State/Territory</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        // Get all indigenous status
        ArrayList<String> indigenousStatus = getIndigenousStatus();
        html = html + "<dif class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='indig_drop'>Indigenous Status: </label>";
        html = html + "<select name='indig_drop' id='indig_drop'>";
        html = html + "<option value=''>Select</option>";
        for (String indig : indigenousStatus) {
            html = html + "<option value='" + indig + "'>" + indig + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        html = html + "<dif class='form-group'>";
        // get all school levels
        ArrayList<String> schoolYears = getSchoolYear();
        html = html + "<br>";
        html = html + "<label for='category_drop'class='option'>School Year: </label>";
        html = html + "<select name='category_drop' id='category_drop' class='option'>";
        html = html + "<option value=''>Select</option>";
        for (String category : schoolYears) {
            html = html + "<option value='" + category + "'>" + category + "</option>";
        }
        html = html + "</select>";
        html = html + "</dif>";

        // Add options for users to choose to sort by raw values or proportional values
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='sort_drop'>Display as: </label>";
        html = html + "<select name='sort_drop' id='sort_drop'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='raw'class='option'>Raw Values</option>";
        html = html + "<option value='percentage'class='option'>Percentage</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Add options for users whether sorts result in ascending or descending order
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='order_drop'>Sort By:</label>";
        html = html + "<input type='radio' id='ASC' name='order_drop' value='ASC' >Ascending";
        html = html + "<input type='radio' id='DESC' name='order_drop' value='DESC' checked>Descending";
        html = html + "<br>";
        html = html + "</div>";

        // Add the submit button
        html = html
                + "   <button type='submit' class='btn btn-primary filter' >Sort & Filter</button>";
        html = html + "</form>";

        html = html + "<br>";

        html = html + "<br>";

        // Get the form data
        String view_drop = context.formParam("view_drop");
        String indig_drop = context.formParam("indig_drop");
        String category_drop = context.formParam("category_drop");
        String sort_drop = context.formParam("sort_drop");
        String order_drop = context.formParam("order_drop");

        // If the form data is null
        if (indig_drop == null || category_drop == null || view_drop == null || sort_drop == null
                || order_drop == null) {
            // nothing shows. Therefore, we make some result in HTML to inform users of this
            // situation
            html = html
                    + "<h3><i>You haven't selected any category. Please choose them to get the result! </i></h3>";
        } else {
            // If the form data is not null
            // Output the statistics

            if (view_drop.equals("LGA")) { // If users want to view the result by LGA
                if (sort_drop.equals("raw") && order_drop.equals("ASC")) { // If users want to sort the result by raw
                    // values in ascending order
                    html = html + outputSchoolCompletion21ByRawValuesASC(indig_drop, category_drop);
                } else if (sort_drop.equals("raw") && order_drop.equals("DESC")) { // If users want to sort the result
                    // by raw values in descending order
                    html = html + outputSchoolCompletion21ByRawValuesDESC(indig_drop, category_drop);
                } else if (sort_drop.equals("percentage") && order_drop.equals("ASC")) { // If users want to sort the
                                                                                         // result by percentage in
                                                                                         // ascending order
                    html = html + outputSchoolCompletion21ByPercentageASC(indig_drop, category_drop);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputSchoolCompletion21ByPercentageDESC(indig_drop, category_drop);
                }
            } else { // If users want to view the result by state/territory
                if (sort_drop.equals("raw") && order_drop.equals("ASC")) { // If users want to sort the result by raw
                                                                           // values in ascending order
                    html = html + outputSchoolCompletion21ByStateByRawValuesASC(indig_drop, category_drop);
                } else if (sort_drop.equals("raw") && order_drop.equals("DESC")) { // If users want to sort the result
                                                                                   // by raw values in descending order
                    html = html + outputSchoolCompletion21ByStateByRawValuesDESC(indig_drop, category_drop);
                } else if (sort_drop.equals("percentage") && order_drop.equals("ASC")) { // If users want to sort the
                                                                                         // result by percentage in
                                                                                         // ascending order
                    html = html + outputSchoolCompletion21ByStateByPercentageASC(indig_drop, category_drop);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputSchoolCompletion21ByStateByPercentageDESC(indig_drop, category_drop);
                }
            }
        }

        // Close Content div
        html = html + "</div>";

        html = html + "<div class='content'>";
        html = html + "<br>";
        html = html + "<h2>Non-school level</h2>";
        html = html
                + "<p>Use the filter below to view statistics about the number as well as the proportional values of people who completed a specific level of education in each Australian local government area or state/territory in 2021.</p>";

        // Add html for the web form
        html = html + "<form action='page2B.html' method='post' class='form2'>";
        html = html + "<dif class='form-group'>";
        html = html + "<h2>Filter (Non-school)</h2>";
        html = html + "<br>";
        html = html + "<label for='show_drop'>View By: </label>";
        html = html + "<select name='show_drop' id='show_drop'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='LGA'>LGA</option>";
        html = html + "<option value='state'>State/Territory</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        // Get all indigenous status
        html = html + "<dif class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='indigenous_drop'>Indigenous Status: </label>";
        html = html + "<select name='indigenous_drop' id='indigenous_drop'>";
        html = html + "<option value=''>Select</option>";
        for (String indig : indigenousStatus) {
            html = html + "<option value='" + indig + "'>" + indig + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        // Get all non-school levels
        html = html + "<dif class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='nonSchool_drop'>Non-school level:</label>";
        html = html + "<select name='nonSchool_drop' id='nonSchool_drop'>";
        html = html + "<option value=''>Select</option>";
        ArrayList<String> nonSchoolBrackets = getNonSchoolBracket();
        for (String nonSchoolBracket : nonSchoolBrackets) {
            html = html + "<option value='" + nonSchoolBracket + "'>" + nonSchoolBracket + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</dif>";

        // Add options for users to choose to sort by raw values or proportional values
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html
                + "<label for='filter_drop'>Display as: </label>";
        html = html + "<select name='filter_drop' id='filter_drop'>";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='raw'>Raw Values</option>";
        html = html + "<option value='percentage'>Percentage</option>";
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Add options for users whether sorts result in ascending or descending order
        html = html + "<div class='form-group'>";
        html = html + "<br>";
        html = html + "<label for='order_by'>Sort By: </label>";
        html = html + "<input type='radio' id='ASC' name='order_by' value='ASC' >Ascending";
        html = html + "<input type='radio' id='DESC' name='order_by' value='DESC' checked>Descending";
        html = html + "</div>";
        html = html + "<br>";

        // Add the submit button
        html = html + "   <button type='submit' class='btn btn-primary filter'>Sort & Filter</button>";
        html = html + "</form>";

        html = html + "<br>";

        // Get the form data
        String show_drop = context.formParam("show_drop");
        String indigenous_drop = context.formParam("indigenous_drop");
        String nonSchool_drop = context.formParam("nonSchool_drop");
        String filter_drop = context.formParam("filter_drop");
        String order_by = context.formParam("order_by");

        // If the form data is null
        if (show_drop == null || indigenous_drop == null || nonSchool_drop == null || filter_drop == null
                || order_by == null) {
            // nothing shows. Therefore, we make some result in HTML to inform users of this
            // situation
            html = html + "<h3><i>You haven't selected any category. Please choose them to see the result!</i></h3>";
        } else {
            // If the form data is not null
            // Output the statistics
            if (show_drop.equals("LGA")) { // If users want to view result by LGA
                if (filter_drop.equals("raw") && order_by.equals("ASC")) { // If users want to sort the result by raw
                                                                           // values in ascending order
                    html = html + outputNonSchoolCompletion21ByRawValuesASC(indigenous_drop, nonSchool_drop);
                } else if (filter_drop.equals("raw") && order_by.equals("DESC")) { // If users want to sort the result
                                                                                   // by raw values in descending order
                    html = html + outputNonSchoolCompletion21ByRawValuesDESC(indigenous_drop, nonSchool_drop);
                } else if (filter_drop.equals("percentage") && order_by.equals("ASC")) { // If users want to sort the
                                                                                         // result by percentage in
                                                                                         // ascending order
                    html = html + outputNonSchoolCompletion21ByPercentageASC(indigenous_drop, nonSchool_drop);
                } else { // If users want to sort the result by percentage in descending order
                    html = html + outputNonSchoolCompletion21ByPercentageDESC(indigenous_drop, nonSchool_drop);
                }
            } else { // if users want to view result by states/territories
                if (filter_drop.equals("raw") && order_by.equals("ASC")) {// if users want to sort the result by raw
                                                                          // values in ascending order
                    html = html + outputNonSchoolCompletion21ByStateByRawValuesASC(indigenous_drop, nonSchool_drop);
                } else if (filter_drop.equals("raw") && order_by.equals("DESC")) { // if users want to sort the result
                                                                                   // by raw values in descending order
                    html = html + outputNonSchoolCompletion21ByStateByRawValuesDESC(indigenous_drop, nonSchool_drop);
                } else if (filter_drop.equals("percentage") && order_by.equals("ASC")) { // if users want to sort the
                                                                                         // result by percentage in
                                                                                         // ascending order
                    html = html + outputNonSchoolCompletion21ByStateByPercentageASC(indigenous_drop, nonSchool_drop);
                } else { // if users want to sort the result by percentage in descending order
                    html = html + outputNonSchoolCompletion21ByStateByPercentageDESC(indigenous_drop, nonSchool_drop);
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

    // Output HTML page show school completion of each LGA in 2021 by raw values in
    // ascending order
    public String outputSchoolCompletion21ByRawValuesASC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the number of " + indig + " people who " + category
                    + " in each Australian local government area in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                    + " in each Australian local government area in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools = sdb.sortSchoolCompletion21ByRawValuesASC(indig, category);
        int i = 1;
        for (School school : schools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + school.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output HTML page show school completion of each LGA in 2021 by raw values in
    // descending order
    public String outputSchoolCompletion21ByRawValuesDESC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the number of " + indig + " people who " + category
                    + " in each Australian local government area in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                    + " in each Australian local government area in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools = sdb.sortSchoolCompletion21ByRawValuesDESC(indig, category);
        int i = 1;
        for (School school : schools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + school.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output HTML page show school completion of each LGA in 2021 by percentage in
    // ascending order
    public String outputSchoolCompletion21ByPercentageASC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the percentage of " + indig + " people who " + category
                    + " in each Australian local government area in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                    + " in each Australian local government area in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools = sdb.sortSchoolCompletion21ByPercentageASC(indig, category);
        int i = 1;
        for (School school : schools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            if (school.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", school.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";
        html = html
                + """

                        <p><em>Note: N/A in Percentage column means that there is no data available or the percentage is 0.</em></p>
                        <br>
                        <hr>
                                """;

        // Return html
        return html;
    }

    // Output HTML page show school completion of each LGA in 2021 by percentage in
    // descending order
    public String outputSchoolCompletion21ByPercentageDESC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the percentage of " + indig + " people who " + category
                    + " in each Australian local government area in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                    + " in each Australian local government area in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools = sdb.sortSchoolCompletion21ByPercentageDESC(indig, category);
        int i = 1;
        for (School school : schools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            if (school.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", school.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";
        html = html
                + """
                        <p><em>Note: N/A in Percentage column means that there is no data available or the percentage is 0..</em></p>
                        <br>
                        <hr>
                            """;

        // Return html
        return html;
    }

    // Output html page show school completion of each state/territory in 2021 by
    // raw values in ascending order
    public String outputSchoolCompletion21ByStateByRawValuesASC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the number of " + indig + " people who " + category
                    + " in each Australian state/territory in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                    + " in each Australian state/territory in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools2 = sdb.sortSchoolCompletion21ByIndigByCategoryByStateByRawValuesASC(indig, category);
        int i = 1;
        for (School school : schools2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + school.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show school completion of each state/territory in 2021 by
    // raw values in descending order
    public String outputSchoolCompletion21ByStateByRawValuesDESC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the number of " + indig + " people who " + category
                    + " in each Australian state/territory in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                    + " in each Australian state/territory in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools2 = sdb.sortSchoolCompletion21ByIndigByCategoryByStateByRawValuesDESC(indig, category);
        int i = 1;
        for (School school : schools2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + school.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show school completion of each state/territory in 2021 by
    // percentage in ascending order
    public String outputSchoolCompletion21ByStateByPercentageASC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the percentage of " + indig + " people who " + category
                    + " in each Australian state/territory in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                    + " in each Australian state/territory in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools2 = sdb.sortSchoolCompletion21ByIndigByCategoryByStateByPercentageASC(indig, category);
        int i = 1;
        for (School school : schools2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + String.format("%.2f", school.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output html page show school completion of each state/territory in 2021 by
    // percentage in descending order
    public String outputSchoolCompletion21ByStateByPercentageDESC(String indig, String category) {
        String html = "";
        if (category.equals("did not go to school")) {
            html = html + "<h3>Statistics about the percentage of " + indig + " people who " + category
                    + " in each Australian state/territory in 2021</h3>";
        } else {
            html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                    + " in each Australian state/territory in 2021</h3>";
        }

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>School Year</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<School> schools2 = sdb.sortSchoolCompletion21ByIndigByCategoryByStateByPercentageDESC(indig,
                category);
        int i = 1;
        for (School school : schools2) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + school.getName() + "</td>";
            html = html + "<td>" + school.getIndigenousStatus() + "</td>";
            html = html + "<td>" + school.getSchoolYear() + "</td>";
            html = html + "<td>" + String.format("%.2f", school.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return the html
        return html;
    }

    // Output the result of sorting non-school completion of each LGA in 2021 by raw
    // values in ascending order
    public String outputNonSchoolCompletion21ByRawValuesASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                + " level in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByRawValuesASC(indig, category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + nonSchool.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each LGA in 2021 by raw
    // values in descending order
    public String outputNonSchoolCompletion21ByRawValuesDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                + " level in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByRawValuesDESC(indig, category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + nonSchool.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each LGA in 2021 by
    // percentage in ascending order
    public String outputNonSchoolCompletion21ByPercentageASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                + " level in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByPercentageASC(indig, category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            if (nonSchool.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", nonSchool.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html
                + """

                        <br>
                        <p><em>Note: N/A in Percentage column means that there is no data available or the percentage is 0.</em></p>
                        <br>
                        <hr>
                                """;

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each LGA in 2021 by
    // percentage in descending order
    public String outputNonSchoolCompletion21ByPercentageDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                + " level in each Australian local government area in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>LGA</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByPercentageDESC(indig, category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            if (nonSchool.getPercentage() == 0) {
                html = html + "<td>N/A</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", nonSchool.getPercentage()) + "%</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html
                + """
                        <br>
                        <p><em>Note: N/A in Percentage column means that there is no data available or the percentage = 0.</em></p>
                        <br>
                        <hr>
                            """;

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each state/territory in
    // 2021 by raw values in ascending order
    public String outputNonSchoolCompletion21ByStateByRawValuesASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                + " level in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByIndigByCategoryByStateByRawValuesASC(indig,
                category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + nonSchool.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each state/territory in
    // 2021 by raw values in descending order
    public String outputNonSchoolCompletion21ByStateByRawValuesDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the number of " + indig + " people completed " + category
                + " level in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Total</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByIndigByCategoryByStateByRawValuesDESC(indig,
                category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + nonSchool.getCount() + "</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each state/territory in
    // 2021 by percentage in ascending order
    public String outputNonSchoolCompletion21ByStateByPercentageASC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                + " level in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByIndigByCategoryByStateByPercentageASC(indig,
                category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + String.format("%.2f", nonSchool.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Output the result of sorting non-school completion of each state/territory in
    // 2021 by percentage in descending order
    public String outputNonSchoolCompletion21ByStateByPercentageDESC(String indig, String category) {
        String html = "";
        html = html + "<h3>Statistics about the percentage of " + indig + " people completed " + category
                + " level in each Australian state/territory in 2021</h3>";

        // Add HTML table to present the statistics
        html = html + """
                <br>
                        <table>
                    <tr>
                    <th>Ranking</th>
                    <th>State/Territory</th>
                    <th>Indigenous Status</th>
                    <th>Non-school level</th>
                    <th>Percentage</th>
                    </tr>
                    """;

        // Use SDBConnection to add html and data for the table
        SDBConnection sdb = new SDBConnection();
        ArrayList<Nonschool> nonSchools = sdb.sortNonSchoolCompletion21ByIndigByCategoryByStateByPercentageDESC(indig,
                category);
        int i = 1;
        for (Nonschool nonSchool : nonSchools) {
            html = html + "<tr>";
            html = html + "<td>" + i + "</td>";
            html = html + "<td>" + nonSchool.getName() + "</td>";
            html = html + "<td>" + nonSchool.getIndigenousStatus() + "</td>";
            html = html + "<td>" + nonSchool.getNonSchoolBracket() + "</td>";
            html = html + "<td>" + String.format("%.2f", nonSchool.getPercentage()) + "%</td>";
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br><hr>";

        // Return html
        return html;
    }

    // Get all indigenous status in the database
    public ArrayList<String> getIndigenousStatus() {
        // Creat the ArrayList to return
        ArrayList<String> indigenousStatus = new ArrayList<String>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT indigenousStatus FROM LGASchoolCompletion21 EXCEPT SELECT indigenousStatus FROM LGASchoolCompletion21 WHERE indigenousStatus = 'indigenous not stated'";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String indigenous = results.getString("indigenousStatus");

                // Add the indigenous status to the ArrayList
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

    // Get all school years in the database
    public ArrayList<String> getSchoolYear() {
        // Creat the ArrayList of school years to return
        ArrayList<String> schoolYears = new ArrayList<String>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT schoolYear FROM LGASchoolCompletion21";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String schoolYear = results.getString("schoolYear");

                // Add the all school years to the ArrayList
                schoolYears.add(schoolYear);
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
        return schoolYears;
    }

    // Get all non-school bracket in the database
    public ArrayList<String> getNonSchoolBracket() {
        // Creat the ArrayList of all non-school bracket to return
        ArrayList<String> nonSchoolBrackets = new ArrayList<String>();

        // Setup the variable for the SDB connection
        Connection connection = null;

        try {
            // Connect to SDB data base
            connection = DriverManager.getConnection(SDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT nonSchoolBracket FROM LGANonSchoolCompletion21";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String nonSchoolBracket = results.getString("nonSchoolBracket");

                // Add the non-school bracket to the ArrayList
                nonSchoolBrackets.add(nonSchoolBracket);
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
        return nonSchoolBrackets;
    }
}
