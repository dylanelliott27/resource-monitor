package ResourceMonitor.Models;

import ResourceMonitor.Utilities.DBUtility;
import ResourceMonitor.Utilities.UsageRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDate;

public class TableViewModel {

    public ObservableList fetchAllAverageUsage(){
        ResultSet results = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ObservableList data = FXCollections.observableArrayList();

        try{
            while(results.next()){
                LocalDate logDate = results.getDate("logdate").toLocalDate();
                int cpuUsage = results.getInt("CPU");
                int ramUsage = results.getInt("HDD");
                int hddUsage = results.getInt("RAM");

                data.add(new UsageRow(logDate, cpuUsage, ramUsage, hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
