package ResourceMonitor.Utilities;

import java.sql.*;
import java.util.Properties;
import javax.sql.rowset.*;


public class DBUtility {
    /**
     * This method uses a Connection created from the ConnectToDB method to execute the SQL statement
     * passed in as a parameter. It transfers all the rows into a CachedRowSet so that it can be returned
     * to the caller and parsed (after closing the connection). On success or exception, all the connections get closed
     * @param statement = The SQL statement (that is not a update or insert statement) to be executed
     * @return = CachedRowSet of all the rows returned from the query
     */
    public static CachedRowSet fetch(String statement) {
        // https://docs.oracle.com/javase/7/docs/api/javax/sql/rowset/CachedRowSet.html
        // Realized I was originally returning a resultset, and because of that, I couldn't close the connection after executing,
        // or my return would be null. CachedRowSet seemed to be the solution. Basically a "disconnected" rowset data structure.
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

    /**
     * Similiar concept to the fetch method, uses a open connection to the DB to execute a update or insert statement.
     * Prints to console on success (to be changed soon)
     * Closes all connections after success or exception
     * @param statement = The SQL statement (update or insert) to be executed
     */
    public static void update(String statement) {
        Connection connection = connectToDB();
        PreparedStatement sqlStatement = null;

        try {
            sqlStatement  = connection.prepareStatement(statement);
            sqlStatement.executeUpdate();
            System.out.println("Successfully inserted!");
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
    }

    /**
     * Opens up a connection to the DB hosted on my server with the correct credentials, and returns the connection instance
     * to the caller.
     * @return = The connection instance to my server DB.
     */
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
