[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=13373557&assignment_repo_type=AssignmentRepo)
# COSC3056 - Programming Studio 1
# Semester: Semester 3 - 2023
# Studio Project 
# Group HN_G05 
# Author: Nguyen Phuong Linh 
# Author: Luong Thi Tra My

This program includes:

* A Java class for the Index page (index.html).
* 6x Java classes for 6 pages. Additional pages can be added by adding additional classes.
* JDBCConnection Java class, that uses the CTG Database. This class contains several methods to return the total population for states/territories, Personas, Students' information 
* AHDBConnection Java class: This uses CTG database. It contains lots of methods to return results as requirements of web application for level 2 sub-task A.
* SDBConnection Java class: This uses CTG database. It contains lots of methods to return results as requirements of web application for level 2 sub-task B.
* IDBConnection Java class: This also uses CTG database. It contains methods which used to retrieve data from the database based on user input and return all required information back to users in HTML format. It is used for level 3 sub-task A
* Several files such as Student.java, Persona.java, Age.java, Health.java, School.java, NonSchool.java, Info.java, Change16To21.java are used to store all necessary information for a specific category.
* Examples CSS (common.css) file in the resources directory.
* image (logo.png) file in the resources directory with where to locate the photo of the logo of the website 
* Starting database:
    * vtp.db - contains a database based on the ER Model.
* helper program (VTPProcessCSV.java) that is used to load the SQLite database by using Java to read the CSV files and JDBC insert statements to update the VtP SQLite database.
* helper SQL files (vtp_create_tables.sql) that creates 12 tables based on the CTG ER Model.
* Note: There are some views created inside the database to save the SQL queries for some tasks which reduces the complex of SQL queries written inside several methods.

Classes backing Web pages:
├── PageIndex.java                    - Homepage page for Level 1 Sub-task A
├── PageMission.java                  - Mission Statement page for Level 1 Sub-task B
├── PageST2/3.java                - Sets of 4 Java files backing the 4 pages for 4 Level2/3 sub-tasks.

Other Classes:
├── java/app                                - Package location for all Java files for the webserver
│         ├── App.java                      - Main Application entrypoint for Javalin
│         └── JDBCConnection.java           - JDBC Connection class based on Studio Project Workshop content
├── java/helper                             - Location of the helper file for loading SQLite with JDBC
│         └── VTPProcessCSV.java            - Helper Java program to load SQLite database from the provided CSVs

Folders:
```bash
├── /src/main                    - Location of all files as required by build configuration
│         ├── java               - Java Source location
│         │    ├── app           - Package location for all Java files for the webserver
│         │    └── helper        - Location of the helper file for loading SQLite with JDBC
│         └── resources          - Web resources (html templates / style sheets)
│               ├── css          - CSS Style-sheets. Base example style sheet (common.css) provided
│               └── images       - Image files. Base example image (RMIT Logo) provided
│ 
├── /target                      - build directory (DO NOT MODIFY)
├── /database                    - The folder to store sqlite database files (*.db files), SQL script (*.sql), and other files related to the database
├── pom.xml                      - Configure Build (DO NOT MODIFY)
└── README.md                    - This file ;)
```

Current Libraries:
* org.xerial.sqlite-jdbc (SQLite JDBC library)
* javalin (lightweight Java Webserver)
* thymeleaf (HTML template) - https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html

Libraries required as dependencies:
* By javalin
   * slf4j-simple (lightweight logging)
* By xerial/jdbc
   * sqlite-jdbc

# Building & Running the code
There are two places code can be run from
1. The **main** web server program
2. the **optional** helper program to use JDBC to load your SQLite database from the CSVs using Java

## Running the Main web server
You can run the main webserver program similar to the project workshop activities
1. Open this project within VSCode
2. Allow VSCode to read the pom.xml file
 - Allow the popups to run and "say yes" to VSCode configuring the build
 - Allow VSCode to download the required Java libraries
3. To Build & Run
 - Open the ``src/main/java/app/App.java`` source file, and select "Run" from the pop-up above the main function
4. Go to: http://localhost:7001

## Running the Helper Program
The helper program in ``src/main/java/helper/VTPProcessCSV.java`` can be run separetly from the main webserver. This gives a demonstration of how you can use Java to read the provided CSV files and store the information in an SQLite database. This example transforms the data in the ``database/lga_indigenous_status_by_age_by_sex_census_2016.csv`` file to match the format of the ``PopulationStatistics`` entity as given in the example ER Model for Milestone 1 for the Voice to Parliament social challenge. That is, the code converts the columns of the CSV into rows that can be loaded into the SQLite database using ``INSERT`` statements.

You can run the optional helper program by
1. Open this ``src/main/java/helper/VTPProcessCSV.java`` source file
1. Select "Debug" from the pop-up above the main function (or "Debug Java" from the top-right dropdown)
1. Allow the program to run

You can modify this file as you wish, for other tables and CSVs. When modifying you may need to pay attention to:
* ``DATABASE`` field to change the database location
* ``CSV_FILE`` to change which CSV file is bring read
* ``categoty``, ``status``, and ``sex`` arrays which should match the setup of the CSV file being read
* ``INSERT`` statement construction to:
    * Change the table being used
    * Column data being stored


# DEV Container for GitHub Codespaces
The ```.devcontainer``` folder contains configuration files for GitHub Codespaces.
This ensures that when the GitHub classroom is cloned, the workspace is correctly configured for Java (V16) and with the required VSCode extensions.
This folder will not affect a *local* VSCode setup on a computer.

**🚨 DO NOT MODIFY THE CONTENTS OF THIS FOLDER. 🚨**

# Authors
* COSC3056 teaching team, School of Science and Technologies, RMIT Vietnam.
* Dr. Timothy Wiley, School of Computing Technologies, STEM College, RMIT University.
* Prof. Santha Sumanasekara, School of Computing Technologies, STEM College, RMIT University.

Copyright RMIT University (c) 2023

