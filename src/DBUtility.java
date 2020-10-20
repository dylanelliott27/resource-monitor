import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBUtility {
    private Connection connection;
    private PreparedStatement sqlStatement;

    public void fetchResourceHistory(){
        try{
        Properties connSettings = new Properties();
        connSettings.put("user", "root");
        connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/resourcemonitor", connSettings);
        String statement = "SELECT * from resourcehistory;";

        sqlStatement = connection.prepareStatement(statement);

        ResultSet results = sqlStatement.executeQuery();

            while (results.next()) {
                // Read values using column name
                String logID = results.getString("logid");
                int cpuUsage = results.getInt("cpuusage");
                int hddSpace = results.getInt("hddspace");
                int ramUsage = results.getInt("ramusage");
                String operatingSystem = results.getString("operatingsystem");

                System.out.printf(logID + ' ' + cpuUsage + ' ' + hddSpace + ' ' + ramUsage + ' ' + operatingSystem);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
