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
        PreparedStatement sqlStatement = null;

        try {
            sqlStatement  = connection.prepareStatement(statement);
            results = sqlStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*finally {
            try{
                if(connection != null){
                    connection.close();
                }
                if(sqlStatement != null){
                    sqlStatement.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }*/
        return results;
    }

    public static int update(String statement) {
        Connection connection = connectToLocalDB();
        int result = 0;
        PreparedStatement sqlStatement = null;

        try {
            sqlStatement  = connection.prepareStatement(statement);
            result = sqlStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*finally {
            try{
                if(connection != null){
                    connection.close();
                }
                if(sqlStatement != null){
                    sqlStatement.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }*/
        return result;
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

}
