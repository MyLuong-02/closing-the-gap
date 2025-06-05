package helper;

import java.io.File;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * This program opens a CSV file from the Closing-the-Gap data set
 * and uses JDBC to load up data into the database.
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statements!
 *
 * @author Luong Thi Tra My. Email: s3987023@rmit.edu.vn
 * 
 */
public class VTPProcessCSV {

   // Create variables to assign database and CSV file
   private static final String DATABASE = "jdbc:sqlite:database/vtp.db";
   private static final String CSV_FILE = "database/lga_non_school_education_by_indigenous_status_by_sex_census_2021.csv";

   public static void main(String[] args) {
      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // Create String list to store all non-school completion brackets
      String category[] = {
            "Postgraduate Degree, Graduate Diploma and Graduate Certificate",
            "Bachelor Degree",
            "Advanced Diploma and Diploma",
            "Certificate III and IV ",
            "Certificate I and II"
      };

      // Create String list to store all status
      String status[] = {
            "indigenous",
            "non-indigenous",
            "indigenous not stated"
      };

      // String list to store genders
      String sex[] = {
            "f",
            "m"
      };

      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();

            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into onSchoolCompletion21 VALUES ("
                     + lgaCode + ","
                     + "'" + status[indexStatus] + "'," + "'" + sex[indexSex] + "'," + "'" + category[indexCategory]
                     + "',"
                     + count + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
