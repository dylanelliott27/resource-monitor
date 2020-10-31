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
        Connection connection = connectToLocalDB();
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
        Connection connection = connectToLocalDB();
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
