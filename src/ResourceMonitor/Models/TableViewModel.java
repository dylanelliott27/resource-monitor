package ResourceMonitor.Models;

import ResourceMonitor.Utilities.DBUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.time.LocalDate;
// TODO VALIDATION and move method to controller
public class TableViewModel {
    private LocalDate logDate;
    private int cpuUsage;
    private int ramUsage;
    private int hddUsage;

    public TableViewModel(LocalDate logDate, int cpuUsage, int ramUsage, int hddUsage) {
        this.logDate = logDate;
        this.cpuUsage = cpuUsage;
        this.ramUsage = ramUsage;
        this.hddUsage = hddUsage;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(int cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(int ramUsage) {
        this.ramUsage = ramUsage;
    }

    public int getHddUsage() {
        return hddUsage;
    }

    public void setHddUsage(int hddUsage) {
        this.hddUsage = hddUsage;
    }

    public static ObservableList fetchAllAverageUsage(){
        ResultSet results = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ObservableList data = FXCollections.observableArrayList();

        try{
            while(results.next()){
                LocalDate logDate = results.getDate("logdate").toLocalDate();
                int cpuUsage = results.getInt("CPU");
                int ramUsage = results.getInt("HDD");
                int hddUsage = results.getInt("RAM");

                data.add(new TableViewModel(logDate, cpuUsage, ramUsage, hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
