import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBUtility {
    private Connection connection;
    private PreparedStatement sqlStatement;

    public String fetchResourceHistory(){
        try{
        connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/resourcemonitor");
        String statement = "SELECT * from resourcehistory;";
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
