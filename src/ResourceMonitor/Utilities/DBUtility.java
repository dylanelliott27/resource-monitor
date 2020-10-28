package ResourceMonitor.Utilities;

import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class DBUtility {
    /**
     * Main method to fetch all records in resourcehistory table. All of these methods may be moved/implemented differently as the assignment
     * moves on and the target is no longer just printing to console. FetchResourceHistory will have a custom query etc
     */
    public static ResultSet fetch(String statement) {
        Connection connection = connectToLocalDB();
        ResultSet results = null;

        try {
            PreparedStatement sqlStatement  = connection.prepareStatement(statement);
            results = sqlStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * This methods purpose is to split up all functionality for reusability, ex: for each db utility method that needs to call the database,
     * it can just call this method instead
     *
     * @return The connection object that is created from successful db connection
     */
    private static Connection connectToLocalDB() {
        Connection connection = null;
        try {
            Properties connSettings = new Properties();
            connSettings.put("user", "root"); // You may need to change this if your local setup is different
            connSettings.put("password", "root");
            connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/resourcemonitor", connSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returns a string of all the column names which gets printed in console in the order that the db results will be shown.
     *
     * @param queryResult - The ResultSet from successful db query
     * @return - A string containing all the column names. Purpose is just for console viewing.
     */
    private static String parseColumnNames(ResultSet queryResult) {
        String columnNames = "";
        try {
            ResultSetMetaData metadata = queryResult.getMetaData();

            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                columnNames += metadata.getColumnName(i) + " ";  // Doing it this way just so it shows best in console
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnNames;
    }

    /**
     * Concatenates all of the columns for each row into a string, then prints it to console in the same order as the above method.
     *
     * @param queryResult - The resultSet from DB query
     */
    private static void parseAndPrintRowData(ResultSet queryResult) {
        // Will change to return something in future so can be accessed in arrayList or something
        try {
            while (queryResult.next()) {
                String logID = queryResult.getString("logid");
                Date logDate = queryResult.getDate("logdate");
                int cpuUsage = queryResult.getInt("cpuusage");
                int hddSpace = queryResult.getInt("hddspace");
                int ramUsage = queryResult.getInt("ramusage");
                String operatingSystem = queryResult.getString("operatingsystem");
                System.out.println(logID + ' ' + logDate + ' ' + cpuUsage + ' ' + hddSpace + ' ' + ramUsage + ' ' + operatingSystem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
