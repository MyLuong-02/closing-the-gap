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
 * Mission HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Luong Thi Tra My, 2024. Email: s3987023@rmit.edu.vn.
 * @author Nguyen Phuong Linh, 2024. Email: s4034535@rmit.edu.vn.
 */
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Our Mission</title>";

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
                        <h1>Our Mission</h1>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html
                + """
                        <h2>Mission Statement</h2>
                        <p>Our mission is to build a web application which addresses the <q>Closing the Gap</q> social challenge. This website is to support governments, indigenous leaders, and the general public observe progress towards targets of the National Agreement on Closing the Gap across the last two Australian censuses including 2016 and 2021.</p>
                        <p>The purpose of our website is to provide users with information about the content of the National Agreement as well as detailed statistics about two of the 17 socioeconomic outcomes of the National Agreement in Australia.</p>
                        <h2>How our site can be used </h2>
                        <p>Our website is divided into several web pages. Each of these takes responsibility for a separated task which can help users be easy to use and navigate through different pages.</p>
                        <p>There are 6 pages within our website. Each page will show users different information and provide them with different features to satisfy their needs.</p>
                        <ul>
                        <li><b>Home</b> page: This page is the first page users see when accessing our website at first. It shows users a navigation bar attached with hyperlinks to go to other web pages. Besides, users are also able to explore the 17 socioeconomic outcomes of the National Agreement on Closing the Gap as well as see two outcomes being highlighted which will be showned and analyzed specifically in our website. Especially, the total population of each Australia's state and territory in both 2016 and 2021 are also presented to users. Therefore, users can see and collect statistics easily without having any difficulties.</li>
                        <li><b>Our Mission</b> page: On this page, users can look for information related to our website such as its purpose, its mission, target users as well as name and contact of each member in out development team.</li>
                        <li>
                        <b>Age and Health Conditions in 2021</b> page: This page serves users the following features:
                        <ul>
                        <li>Enabling users to examine at the granularity of each LGA or states/territories by viewing raw total values or proportional values of either age demographics or health conditions through selecting options from dropdown lists.</li>
                        <li>Enabling the user to sort the LGAs, or states/territories by either the raw values or proportional values in both ascending and descending order as well as clearly see its ranking.</li>
                        </ul>
                        </li>
                        <li>
                        <b>School and Non-school Completion in 2021</b> page: This page presents to users statistics about school or non-school completion of indigenous and non-indigenous people in 2021 based on the user selection. It is provided with the following functions:
                        <ul>
                        <li>Enabling users to examine at the granularity of each LGA or states/territories by viewing raw total values or proportional values of either school or non-school completion through selecting options from dropdown lists.</li>
                        <li>Enabling the user to selectively view categories of school and non-school completions. As a result, The user can choose to show or hide different school and non-school completion categories, from showing all categories at once to viewing only one category.</li>
                        <li>Enabling the user to sort the LGAs, or states/territories by either the raw values or proportional values in both ascending and descending order as well as clearly see its ranking.</li>
                        </ul>
                        </li>
                        <li><b>Changing Gap between Indigenous and Non-indigenous People in 2016 and 2021</b> page: This page enables users to explore a deeper view of the gap between indigenous and non-indigenous people in the two socioeconomic outcomes highlighted in the <q>Home</q> page. This page allows users to select categories they want to see such as indigenous or non-indigenous people, one or more health conditions, etc. Then, it will show the result to the user with their matched filters. Besides, it also shows them the LGA with best improvement as well as the LGA with worst decline.</li>
                        <li><b>Finding LGAs with Similar Characteristics</b> page: For this page, it helps users to find out LGAs that have the most similar characteristics with their chosen LGA. For this feature, the user will choose one LGA from a list and then select other properties such as outcome, year, status, etc. Then, based on user selected LGA and related properties, the system will find and return the result back to the user.</li>
                        </ul>
                                """;

        // Using JDBC to lookup the students
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the personas
        ArrayList<Persona> personas = jdbc.getPersonas();

        // Add HTML for the persona list
        html = html
                + """
                        <h2>Personas</h2>
                        <br>
                            """;

        // Finally we can print out all of the personas' information
        for (Persona persona : personas) {
            html = html + "<h3>Persona number " + persona.getID() + ": " + persona.getName() + "</h3>";
            html = html + "<ul>";
            html = html + "<li><b>Wants:</b> <q>" + persona.getQuotes() + "</q></li>";
            html = html + "<li><b>Background:</b> " + persona.getBackground() + "</li>";
            html = html + "<li><b>Needs and Goals:</b> " + persona.getGoals() + "</li>";
            html = html + "<li><b>Skills and Experience:</b> " + persona.getSkills() + "</li>";
            html = html + "</ul>";
            html = html + "<br>";
        }

        // Next we will ask this *class* for the students
        ArrayList<Student> students = jdbc.getStudents();

        // Add HTML for the student list
        html = html
                + """
                        <h2>Our Team</h2>
                        <br>
                        <p>This studio project is developed by a team of 2 students. Below is several information about us. Should you have any questions related to this studio project, Feel free to send us an email according to the contact below.</p>
                        <br>
                        <table>
                            <tr>
                                <th>Student ID</th>
                                <th>Name</th>
                                <th>Email</th>
                            </tr>
                        """;

        // Finally we can print out all of the students' information
        for (Student student : students) {
            html = html + "<tr><td>" + student.getID() + "</td><td>" + student.getName() + "</td><td><a href=\"mailto"
                    + student.getEmail() + "\">" + student.getEmail() + "</td></tr>";
        }

        // Finish the List HTML
        html = html + "</table>";
        html = html + "<br><hr>";

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

}
