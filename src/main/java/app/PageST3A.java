package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ArrayList; // Add missing import for ArrayList

/**
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Luong Thi Tra My, 2024. Email: s3987023@rmit.edu.vn
 * @author Nguyen Phuong Linh, 2024. Email
 */
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Changing the Gap </title>";

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

        html = html + "<dif class='header'>";
        html = html + "<h1>Changing the Gap </h1>";
        html = html + "<br>";
        html = html
                + "<p class='content'>Using the filter below to choose the data set as well as specific categories you want to see the results. </p>";
        html = html + "</div>";

        // Add html form
        html = html + "<form action='page3A.html' method='post' class='form1'>";
        html = html + "<div class='option'>";
        html = html + "<h2>Filter</h2>";
        html = html + "<br>";
        html = html + "<label for='data_set'>Choose a Data Set (1-4): </label>";
        html = html + "<select name='data_set' id='data_set' onchange='toggleDropdown()'> ";
        html = html + "<option value=''>Select</option>";
        html = html + "<option value='1'>Set 1: Age Demographics</option>";
        html = html + "<option value='2'>Set 2: Health Conditions</option>";
        html = html + "<option value='3'>Set 3: School Completion</option>";
        html = html + "<option value='4'>Set 4: Non-school Completion</option>";
        html = html + "</select>";
        html = html + "</div>";

        // Add JavaScript to toggle the dropdown list
        html = html + "<script>";
        html = html + "function toggleDropdown() {";
        html = html + "var value = document.getElementById('data_set').value;";
        html = html + "if (value == 1) {";
        html = html + "document.getElementById('age_min').style.display = 'block';";
        html = html + "document.getElementById('age_max').style.display = 'block';";
        html = html + "document.getElementById('health_condition').style.display = 'none';";
        html = html + "document.getElementById('school_year').style.display = 'none';";
        html = html + "} else if (value == 2){";
        html = html + "document.getElementById('health_condition').style.display = 'block';";
        html = html + "document.getElementById('age_min').style.display = 'none';";
        html = html + "document.getElementById('age_max').style.display = 'none';";
        html = html + "document.getElementById('school_year').style.display = 'none';";
        html = html + "} else if (value == 3){";
        html = html + "document.getElementById('school_year').style.display = 'block';";
        html = html + "document.getElementById('age_min').style.display = 'none';";
        html = html + "document.getElementById('age_max').style.display = 'none';";
        html = html + "document.getElementById('health_condition').style.display = 'none';";
        html = html + "} else if (value == 4){";
        html = html + "document.getElementById('non_school_bracket').style.display = 'block';";
        html = html + "document.getElementById('age_min').style.display = 'none';";
        html = html + "document.getElementById('age_max').style.display = 'none';";
        html = html + "document.getElementById('health_condition').style.display = 'none';";
        html = html + "document.getElementById('school_year').style.display = 'none';";
        html = html + "} else {";
        html = html + "document.getElementById('age_min').style.display = 'none';";
        html = html + "document.getElementById('age_max').style.display = 'none';";
        html = html + "document.getElementById('health_condition').style.display = 'none';";
        html = html + "document.getElementById('school_year').style.display = 'none';";
        html = html + "document.getElementById('non_school_bracket').style.display = 'none';";
        html = html + "}";
        html = html + "}";
        html = html + "</script>";

        // Add all options for minimum value of age
        html = html + "<div class='dropdown'>";
        html = html + "<br>";
        html = html + "<select name='age_min' id='age_min'>";
        html = html + "<option value=''>Minimum value for age range</option>";
        ArrayList<Integer> ageMin = getAgeMin();
        for (int age : ageMin) {
            html = html + "<option value='" + age + "'>" + age + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Get all options for maximum value of age
        html = html + "<div class='dropdown'>";
        html = html + "<br>";
        html = html + "<select name='age_max' id='age_max'>";
        html = html + "<option value=''>Maximum value for age range</option>";
        ArrayList<Integer> ageMax = getAgeMax();
        for (int age : ageMax) {
            html = html + "<option value='" + age + "'>" + age + "</option>";
        }
        html = html + "</select>";
        html = html + "<br>";
        html = html + "</div>";

        // Create a checkbox list of health conditions
        html = html + "<div class='check-list' id='health_condition' style='display: none;'>"; // Hide by default
        html = html + "<label for='health_condition'>Health conditions:<br></label>";
        ArrayList<String> healthConditions = getHealthConditions();
        for (String condition : healthConditions) {
            html = html + "<input type='checkbox' name='health_condition' value='" + condition + "'>" + condition
                    + "<br>";
        }
        html = html + "</div>";

        // Create a dropdown list of school year
        html = html + "<div class='dropdown' id='school_year' style='display: none;'>"; // Hide by default
        html = html + "<label for='school_year'>School year: </label>";
        html = html + "<select name='school_year' id='school_year'>";
        html = html + "<option value=''>Select</option>";
        ArrayList<String> schoolYears = getSchoolYear();
        for (String year : schoolYears) {
            html = html + "<option value='" + year + "'>" + year + "</option>";
        }
        html = html + "</select>";
        html = html + "</div>";

        // Create a checkbox list of non-school completion
        html = html + "<div class='check-list' id='non_school_bracket' style='display: none;'>"; // Hide by default
        html = html + "<label for='non_school_bracket'>Non-school level: <br></label>";
        ArrayList<String> nonSchoolBrackets = getNonSchoolBracket();
        for (String bracket : nonSchoolBrackets) {
            html = html + "<input type='checkbox' name='non_school_bracket' value='" + bracket + "'>" + bracket
                    + "<br>";
        }
        html = html + "</div>";

        // Create a checkbox list of indigenous status
        html = html + "<div class='check-list' id='indigenous_status'>";
        html = html + "<br>";
        html = html + "<label for='indigenous_status'>Indigenous status: </label>";
        html = html + "<input type='checkbox' name='indigenous_status' value='indigenous'> Indigenous";
        html = html + "<input type='checkbox' name='indigenous_status' value='non-indigenous'> Non-indigenous";
        html = html + "</div>";

        // Add options for choosing gender
        html = html + "<div class='option'>";
        html = html + "<br>";
        html = html + "<label for='gender'>Gender:</label>";
        html = html + "<input type='checkbox' name='gender' value='f'> Female";
        html = html + "<input type='checkbox' name='gender' value='m'> Male";
        html = html + "</div>";
        html = html + "<br>";

        // Add the submit button
        html = html + "   <button type='submit' class='btn btn-primary filter'>Sort & Filter</button>";
        html = html + "</form>";

        html = html + "<br>";

        // Get the form data
        String dataSet = context.formParam("data_set");
        int ageMinimum = 0;
        int ageMaximum = 0;
        String indigenousStatus = context.formParam("indigenous_status");
        String sex = context.formParam("gender");
        String condition = context.formParam("health_condition");
        String schoolYear = context.formParam("school_year");
        String nonSchoolBracket = context.formParam("non_school_bracket");

        try {
            ageMinimum = Integer.parseInt(context.formParam("age_min"));
            ageMaximum = Integer.parseInt(context.formParam("age_max"));
        } catch (NumberFormatException e) {
            // Handle the exception if the age parameters cannot be parsed
        }

        // If the form data is null
        if (dataSet == null) {
            html = html
                    + "<h3 class='content'><i>You haven't selected any category or data set yet. Please choose from the option lists above to see the results.</i></h3>";
        } else if (dataSet.equals("1")) { // if user selects Age Demographics
            html = html
                    + "<h2 class='function'>The Gap between Indigenous and Non-indigenous people in both 2016 and 2021 with specific age Categories </h2>";
            // Add HTML for the page content
            html = html
                    + "<p class='data'>This part aims to show you statistics about the gap between indigenous and non-indigenous people in both 2016 and 2021 so that you can have a deeper understanding about the gap between them. These statistics are presented with both raw values and proportional values to support you when you need it for processing data.</p>";
            html = html + "<br>";
            html = html + outputByAge(sex, ageMinimum, ageMaximum);
            html = html + "<h2>Improvement/Decline of each LGA from 2016 to 2021 </h2>";
            html = html + "<br>";
            html = html
                    + "<p class='data'>This part aims to show you the result of computing improvement or decline for each LGA from 2016 to 2021 . The higher the number in Improvement/Decline (times) column is, the more improvement the LGA has. On the contrary, the lower the Improvement/Decline (times) is, the more decline the LGA has.</p>";
            html = html + "<br>";
            html = html + outputChangeByAge(indigenousStatus, sex, ageMinimum, ageMaximum);
        } else if (dataSet.equals("2")) {// if user selects Health Conditions
            html = html
                    + "<h2 class='function'>The Gap between Indigenous and Non-indigenous people in 2021 with health conditions</h2>";

            // Add HTML for the page content
            html = html
                    + "<p class='data'>This part aims to show you statistics about the gap between indigenous and non-indigenous people in 2021 so that you can have a deeper understanding about the gap between them. These statistics are presented with both raw values and proportional values to support you when you need it for processing data.</p><br>";
            html = html + outputByHealthCondition(sex, condition);
        } else if (dataSet.equals("3")) {// if user selects School Completion
            html = html
                    + "<h2 class='function'>The Gap between Indigenous and Non-indigenous people in both 2016 and 2021 with specific school year</h2>";
            html = html + "<br>";
            // Add HTML for the page content
            html = html
                    + "<p class='data'>This part aims to show you statistics about the gap between indigenous and non-indigenous people in both 2016 and 2021 so that you can have a deeper understanding about the gap between them. These statistics are presented with both raw values and proportional values to support you when you need it for processing data.</p>";
            html = html + "<br>";

            html = html + outputBySchoolYear(sex, schoolYear);
            html = html + "<h2>Improvement/Decline of each LGA from 2016 to 2021 </h2>";
            html = html + "<br>";
            html = html
                    + "<p class='data'>This part aims to show you the result of computing improvement or decline for each LGA from 2016 to 2021 . The higher the number in Improvement/Decline (times) column is, the more improvement the LGA has. On the contrary, the lower the Improvement/Decline (times) is, the more decline the LGA has.</p>";
            html = html + "<br>";
            html = html + outputChangeBySchoolYear(indigenousStatus, sex, schoolYear);
        } else {// If user selects non-school completion
            html = html
                    + "<h2 class='function'>The Gap between Indigenous and Non-indigenous people in both 2016 and 2021 with specific non-school category</h2>";
            html = html + "<br>";
            // Add HTML for the page content
            html = html
                    + "<p class='data'>This part aims to show you statistics about the gap between indigenous and non-indigenous people in both 2016 and 2021 so that you can have a deeper understanding about the gap between them. These statistics are presented with both raw values and proportional values to support you when you need it for processing data.</p>";
            html = html + "<br>";

            html = html + outputByNonSchoolBracket(sex, nonSchoolBracket);
            html = html + "<h2>Improvement/Decline of each LGA from 2016 to 2021 </h2>";
            html = html + "<br>";
            html = html
                    + "<p class='data'>This part aims to show you the result of computing improvement or decline for each LGA from 2016 to 2021 . The higher the number in Improvement/Decline (times) column is, the more improvement the LGA has. On the contrary, the lower the Improvement/Decline (times) is, the more decline the LGA has.</p>";
            html = html + "<br>";
            html = html + outputChangeByNonSchoolBracket(indigenousStatus, sex, nonSchoolBracket);
        }

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + "<div class='footer'>";
        html = html + "<p id=\"footer-title\">Closing The Gap</p>";
        html = html + "<br>";
        html = html + "<p id=\"copyright\">Copyright 2024</p>";
        html = html + "</div>";

        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    // Output content in html to show the gap between indigenous and non-indigenous
    // people in 2016 and 2021 with specific age categories
    public String outputByAge(String sex, int ageMin, int ageMax) {
        // Create a string to store the HTML output
        String html = "";
        html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people from " + ageMin
                + " to " + ageMax + " years old in 2016</h3>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2016
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + sex + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Info> infoList = idb.getInfo2016(sex, ageMin, ageMax);

        // Loop through the infoList to get the data
        for (Info info : infoList) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        // Output the statistics about the gap between indigenous and non-indigenous
        // people in 2021
        html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people from " + ageMin
                + " to " + ageMax + " years old in 2021</h3>";
        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2021
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        ArrayList<Info> infos = idb.getInfo2021(sex, ageMin, ageMax);

        // Loop through the infoList to get the data
        for (Info info : infos) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """

                        <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0.</em></p>
                        <br>
                        <hr>
                                """;

        // Return the html string
        return html;
    }

    // Output content in html to show the gap between indigenous and non-indigenous
    // people in 2021 with health conditions
    public String outputByHealthCondition(String sex, String condition) {
        // Create a string to store the HTML output
        String html = "";
        html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people with " + condition
                + " in 2021</h3>";
        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2021
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + sex + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Info> infoList = idb.getInfoByHealthCondition(sex, condition);

        // Loop through the infoList to get the data
        for (Info info : infoList) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """
                            <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                            <br>
                            <hr>
                        """;

        // Return the html string
        return html;
    }

    // Output content in html to show the improvement/decline of each LGA from 2016
    // to 2021
    public String outputChangeByAge(String indig, String sex, int ageMin, int ageMax) {
        // Create a string to store the HTML output
        String html = "";
        html = html + "<h3>Improvement/Decline of each LGA from 2016 to 2021</h3>";
        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2021
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>Ranking</th>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Total (2016)</th>";
        html = html + "<th>Percentage (2016)</th>";
        html = html + "<th>Total (2021)</th>";
        html = html + "<th>Percentage (2021)</th>";
        html = html + "<th>Improvement/Decline (times)</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Change16To21> changes = idb.getChangeByAge(indig, sex, ageMin, ageMax);

        int i = 1;
        // Loop through the infoList to get the data
        for (Change16To21 change : changes) {
            html = html + "<tr>";
            if (i == 1 || change.getChange() == 0) {
                html = html + "<td><strong>" + i + "</strong></td>";
                html = html + "<td><strong>" + change.getName() + "</strong></td>";
                html = html + "<td><strong>" + change.getCount16() + "</strong></td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent16()) + "%"
                            + "</strong></td>";
                }
                html = html + "<td><strong>" + change.getCount21() + "</strong></td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent21()) + "%"
                            + "</strong></td>";
                }
                if (change.getChange() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getChange()) + "</strong></td>";
                }
            } else {
                html = html + "<td>" + i + "</td>";
                html = html + "<td>" + change.getName() + "</td>";
                html = html + "<td>" + change.getCount16() + "</td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent16()) + "%" + "</td>";
                }
                html = html + "<td>" + change.getCount21() + "</td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent21()) + "%" + "</td>";
                }
                html = html + "<td>" + String.format("%.2f", change.getChange()) + "</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """
                            <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                            <br>
                            <hr>
                        """;

        // Return the html string
        return html;
    }

    // Output content in html to show the gap between indigenous and non-indigenous
    // people in both 2016 and 2021 with school years
    public String outputBySchoolYear(String sex, String schoolYear) {
        // Create a string to store the HTML output
        String html = "";
        if (schoolYear.equals("did not go to school")) {
            html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people who " + schoolYear
                    + " in 2016 </h3>";
        } else {
            html = html
                    + "<h3>Statistics about the gap between indigenous and non-indigenous people who have completed "
                    + schoolYear + " in 2016 </h3>";
        }
        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2016
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + sex + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Info> infoList = idb.getInfoBySchoolYear16(sex, schoolYear);

        // Loop through the infoList to get the data
        for (Info info : infoList) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        // Output the statistics about the gap between indigenous and non-indigenous
        // people in 2021
        if (schoolYear.equals("did not go to school")) {
            html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people who " + schoolYear
                    + " in 2021 </h3>";
        } else {
            html = html
                    + "<h3>Statistics about the gap between indigenous and non-indigenous people who have completed "
                    + schoolYear + " in 2021 </h3>";
        }

        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2021
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + schoolYear + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + schoolYear + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        ArrayList<Info> infos = idb.getInfoBySchoolYear21(sex, schoolYear);

        // Loop through the infoList to get the data
        for (Info info : infos) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """

                        <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                        <br>
                        <hr>
                                """;

        // Return the html string
        return html;
    }

    // Output content in html to show the improvement/decline of each LGA from 2016
    // to 2021 with school years
    public String outputChangeBySchoolYear(String indig, String sex, String schoolYear) {
        // Create a string to store the HTML output
        String html = "";
        if (schoolYear.equals("did not go to school")) {
            html = html + "<h3>Improvement/Decline of each LGA from 2016 to 2021 for people who " + schoolYear
                    + "</h3>";
        } else {
            html = html + "<h3>Improvement/Decline of each LGA from 2016 to 2021 for people who have completed "
                    + schoolYear + "</h3>";
        }

        html = html + "<br>";

        // Create a table to show the statistics about the gap between indigenous and
        // non-indigenous people in 2021
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>Ranking</th>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Total (2016)</th>";
        html = html + "<th>Percentage (2016)</th>";
        html = html + "<th>Total (2021)</th>";
        html = html + "<th>Percentage (2021)</th>";
        html = html + "<th>Improvement/Decline (times)</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Change16To21> changes = idb.getChangeBySchoolYear(indig, sex, schoolYear);

        int i = 1;
        // Loop through the infoList to get the data
        for (Change16To21 change : changes) {
            html = html + "<tr>";
            if (i == 1 || change.getChange() == 0) {
                html = html + "<td><strong>" + i + "</strong></td>";
                html = html + "<td><strong>" + change.getName() + "</strong></td>";
                html = html + "<td><strong>" + change.getCount16() + "</strong></td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent16()) + "%"
                            + "</strong></td>";
                }
                html = html + "<td><strong>" + change.getCount21() + "</strong></td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent21()) + "%"
                            + "</strong></td>";
                }
                if (change.getChange() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getChange()) + "</strong></td>";
                }
            } else {
                html = html + "<td>" + i + "</td>";
                html = html + "<td>" + change.getName() + "</td>";
                html = html + "<td>" + change.getCount16() + "</td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent16()) + "%" + "</td>";
                }
                html = html + "<td>" + change.getCount21() + "</td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent21()) + "%" + "</td>";
                }
                html = html + "<td>" + String.format("%.2f", change.getChange()) + "</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """
                            <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                            <br>
                            <hr>
                        """;

        // Return the html string
        return html;
    }

    // Output content in html to show the gap between indigenous and non-indigenous
    // people in both 2016 and 2021 with specific level of non-school education
    public String outputByNonSchoolBracket(String sex, String nonSchoolBracket) {
        // Create a string to store the HTML output
        String html = "";
        html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people who completed "
                + nonSchoolBracket
                + " level in 2016</h3>";

        html = html + "<br>";

        // Create a table
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + sex + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Info> infoList = idb.getInfoByNonSchoolBracket16(sex, nonSchoolBracket);

        // Loop through the infoList to get the data
        for (Info info : infoList) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        // Output the statistics about the gap between indigenous and non-indigenous
        // people in 2021
        html = html + "<h3>Statistics about the gap between indigenous and non-indigenous people who completed "
                + nonSchoolBracket
                + " level in 2021</h3>";

        html = html + "<br>";

        // Create a table
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Indigenous People (" + sex + ")</th>";
        html = html + "<th>Indigenous Percentage</th>";
        html = html + "<th>Non-indigenous People (" + sex + ")</th>";
        html = html + "<th>Non-indigenous Percentage</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        ArrayList<Info> infos = idb.getInfoByNonSchoolBracket21(sex, nonSchoolBracket);

        // Loop through the infoList to get the data
        for (Info info : infos) {
            html = html + "<tr>";
            html = html + "<td>" + info.getName() + "</td>";
            html = html + "<td>" + info.getCountIndig() + "</td>";
            if (info.getPercentIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentIndig()) + "%" + "</td>";
            }
            html = html + "<td>" + info.getCountNonIndig() + "</td>";
            if (info.getPercentNonIndig() == 0) {
                html = html + "<td> N/A" + "</td>";
            } else {
                html = html + "<td>" + String.format("%.2f", info.getPercentNonIndig()) + "%" + "</td>";
            }
            html = html + "</tr>";
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """
                            <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                            <br>
                            <hr>
                        """;

        // Return the html string
        return html;
    }

    // Output content in html to show the improvement/decline of each LGA from 2016
    // to 2021 with specific level of non-school education
    public String outputChangeByNonSchoolBracket(String indig, String sex, String nonSchoolBracket) {
        // Create a string to store the HTML output
        String html = "";
        html = html + "<h3>Improvement/Decline of each LGA from 2016 to 2021 for people who completed "
                + nonSchoolBracket
                + " level</h3>";

        html = html + "<br>";

        // Create a table
        html = html + "<table class='data-table'>";
        html = html + "<tr>";
        html = html + "<th>Ranking</th>";
        html = html + "<th>LGA Name</th>";
        html = html + "<th>Total (2016)</th>";
        html = html + "<th>Percentage (2016)</th>";
        html = html + "<th>Total (2021)</th>";
        html = html + "<th>Percentage (2021)</th>";
        html = html + "<th>Improvement/Decline (times)</th>";
        html = html + "</tr>";

        // Use the IDB connection to get the data from the database
        IDBConnection idb = new IDBConnection();
        ArrayList<Change16To21> changes = idb.getChangeByNonSchoolBracket(indig, sex, nonSchoolBracket);

        int i = 1;
        // Loop through the changes list to get the data
        for (Change16To21 change : changes) {
            html = html + "<tr>";
            if (i == 1 || change.getChange() == 0) {
                html = html + "<td><strong>" + i + "</strong></td>";
                html = html + "<td><strong>" + change.getName() + "</strong></td>";
                html = html + "<td><strong>" + change.getCount16() + "</strong></td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent16()) + "%"
                            + "</strong></td>";
                }
                html = html + "<td><strong>" + change.getCount21() + "</strong></td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getPercent21()) + "%"
                            + "</strong></td>";
                }
                if (change.getChange() == 0) {
                    html = html + "<td><strong> N/A" + "</strong></td>";
                } else {
                    html = html + "<td><strong>" + String.format("%.2f", change.getChange()) + "</strong></td>";
                }
            } else {
                html = html + "<td>" + i + "</td>";
                html = html + "<td>" + change.getName() + "</td>";
                html = html + "<td>" + change.getCount16() + "</td>";
                if (change.getPercent16() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent16()) + "%" + "</td>";
                }
                html = html + "<td>" + change.getCount21() + "</td>";
                if (change.getPercent21() == 0) {
                    html = html + "<td> N/A" + "</td>";
                } else {
                    html = html + "<td>" + String.format("%.2f", change.getPercent21()) + "%" + "</td>";
                }
                html = html + "<td>" + String.format("%.2f", change.getChange()) + "</td>";
            }
            html = html + "</tr>";
            i++;
        }

        // Close the table
        html = html + "</table>";
        html = html + "<br>";

        html = html
                + """
                            <p><em>Note: N/A in the table means that there is no data available for that category or its percentage is 0%.</em></p>
                            <br>
                            <hr>
                        """;

        // Return the html string
        return html;
    }

    // Get age range with all minimum values from the database
    public ArrayList<Integer> getAgeMin() {
        // Create an array list to store the age range
        ArrayList<Integer> ageMin = new ArrayList<Integer>();

        // Set the connection to the database
        Connection connection = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection(IDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT DISTINCT ageMin FROM LGAPopulation21 ORDER BY ageMin ASC;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                int age = results.getInt("ageMin");

                // Add the age to the array list
                ageMin.add(age);
            }

            // Close the connection
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
                // If there is an error, print the error message
                System.err.println(e);
            }
        }

        // Return the array list
        return ageMin;
    }

    // Get age range with all maximum values from the database
    public ArrayList<Integer> getAgeMax() {
        // Create an array list to store the age range
        ArrayList<Integer> ageMax = new ArrayList<Integer>();

        // Set the connection to the database
        Connection connection = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection(IDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Set the query
            String query = "SELECT DISTINCT ageMax FROM LGAPopulation21 ORDER BY ageMax ASC;";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                int age = results.getInt("ageMax");

                // Add the age to the array list
                ageMax.add(age);
            }

            // Close the connection
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
                // If there is an error, print the error message
                System.err.println(e);
            }
        }

        // Return the array list
        return ageMax;
    }

    // Get all health conditions in 2021
    public ArrayList<String> getHealthConditions() {
        // Create an ArrayList to store all health conditions
        ArrayList<String> conditions = new ArrayList<String>();

        // Set the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect IDB to the database
            connection = DriverManager.getConnection(IDBConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // set the query
            String query = "SELECT DISTINCT condition FROM LGALTHC21";

            // Get the result set from the query
            ResultSet results = statement.executeQuery(query);

            // Process all of the result sets
            while (results.next()) {
                // Look up the column we need
                String c = results.getString("condition");

                // Add the condition to the ArrayList
                conditions.add(c);
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
        return conditions;
    }

    // Get all school years in the database
    public ArrayList<String> getSchoolYear() {
        // Creat the ArrayList of all school years to return
        ArrayList<String> schoolYears = new ArrayList<String>();

        // Setup the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect to IDB data base
            connection = DriverManager.getConnection(IDBConnection.DATABASE);

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

                // Add the School year to the ArrayList
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
        // Creat the ArrayList of all non-school brackets to return
        ArrayList<String> nonSchoolBrackets = new ArrayList<String>();

        // Setup the variable for the IDB connection
        Connection connection = null;

        try {
            // Connect to IDB data base
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
