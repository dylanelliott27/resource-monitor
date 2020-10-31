package ResourceMonitor.Utilities;

import java.sql.*;
import java.util.Properties;
import javax.sql.rowset.*;


public class DBUtility {

    public static CachedRowSet fetch(String statement) {
        // https://docs.oracle.com/javase/7/docs/api/javax/sql/rowset/CachedRowSet.html
        // Realized I was originally returning a resultset, and because of that, I couldn't close the connection after executing,
        // or my return would be null. CachedRowSet seemed to be the solution. Basically a "disconnected" rowset data structure
        RowSetFactory factory = null;
        CachedRowSet finalResults = null;
        Connection connection = connectToDB();
        ResultSet results = null;
        PreparedStatement sqlStatement = null;

        try {
            factory = RowSetProvider.newFactory();
            finalResults = factory.createCachedRowSet();
            sqlStatement  = connection.prepareStatement(statement);
            results = sqlStatement.executeQuery();
            finalResults.populate(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(connection != null){
                    connection.close();
                }
                if(sqlStatement != null){
                    sqlStatement.close();
                }
                if(results != null){
                    results.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return finalResults;
    }

    public static int update(String statement) {
        Connection connection = connectToDB();
        int result = 0;
        PreparedStatement sqlStatement = null;

        try {
            sqlStatement  = connection.prepareStatement(statement);
            result = sqlStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
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
        }
        return result;
    }

    private static Connection connectToDB() {
        Connection connection = null;
        try {
            Properties connSettings = new Properties();
            connSettings.put("user", "dylan");
            connSettings.put("password", "assignment1");
            connection = DriverManager.getConnection("JDBC:mysql://134.122.18.58:3306/resourcemonitor", connSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
