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
 */
public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Finding Similar Local Government Areas</title>";

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
                        <h1>Similar LGAs</h1>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
                <p>This page allows you to find LGAs that have similar characteristics with the selected LGA.</p>
                """;

        // Close Content div
        html = html + "</div>";

        html = html
                + """
                        <form method='post' class='form1'>
                        <h2 class='function'>Filter</h2>
                        <div class='option'>
                            <label for='year'>Census year</label><br>
                            <input type="radio" id="2016" name="year" value="2016">
                            <label for="2016">2016</label>
                            <input type="radio" id="2021" name="year" value="2021">
                            <label for="2021">2021</label>
                        </div>
                        <div class='option'>
                            <label for="set">Choose a Data Set</label>
                            <select name="data_set" id="data_set">
                            <option value="1">Set 1</option>
                            <option value="2">Set 2</option>
                            <option value="3">Set 3</option>
                            <option value="h4">Set 4</option>
                        </select>
                        </div>
                        <br>
                        <div class='option'>
                            <label for="population">Indigenous Status</label><br>
                            <input type="checkbox" id="indigenous" name="status" value="indigenous">
                            <label for="indigenous">Indigenous</label><br>
                            <input type="checkbox" id="non-indigenous" name="status" value="non-indigenous">
                            <label for="non-indigenous">Non-Indigenous</label>
                        </div>
                        <div class='option'>
                            <label for="Sex">Sex</label><br>
                            <input type="checkbox" id="female" name="sex" value="female">
                            <label for="female">Female</label>
                            <input type="checkbox" id="male" name="sex" value="male">
                            <label for="male">Male</label>
                        </div>
                        <div class='option'>
                            <label for='display'>Display as</label><br>
                            <input type="radio" id="raw-values" name="display" value="Raw values">
                            <label for="raw-values">Raw values</label><br>
                            <input type="radio" id="css" name="display" value="Percentage">
                            <label for="percentage">Percentage</label><br>
                        </div>
                        <div class='option'>
                            <label for="sort">Sort by</label>
                            <select name="sort">
                            <option value="most">Most similar</option>
                            <option value="least">Least similar</option>
                        </select>
                        <br>
                        </div>
                        <div class='option'>
                        <label for="type">Number of results to display</label>
                            <input type="number" name="number" placeholder="Enter a number" min="1">
                        </div>
                        <button type="submit" class='filter'>Sort & Filter</button>
                        </form>
                            """;

        // Footer
        html = html + """
                    <div class='footer fix'>
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

}
