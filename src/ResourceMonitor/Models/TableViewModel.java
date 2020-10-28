package ResourceMonitor.Models;

import ResourceMonitor.Utilities.DBUtility;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class TableViewModel {

    public ObservableList fetchAllAverageUsage(){
        ResultSet results = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");

        try{
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
