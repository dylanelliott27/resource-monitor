import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class DBUtility {
    // TODO --- JAVADOC COMMENTS
    public static void fetchResourceHistory() {
        Connection connection = connectToLocalDB();
        PreparedStatement sqlStatement = null;
        try {
            String statement = "SELECT * from resourcehistory;";
            sqlStatement = connection.prepareStatement(statement);
            ResultSet results = sqlStatement.executeQuery();
            System.out.println(parseColumnNames(results));
            parseAndPrintRowData(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection connectToLocalDB() {
        Connection connection = null;
        try {
            Properties connSettings = new Properties();
            connSettings.put("user", "root");
            connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/resourcemonitor", connSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

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
