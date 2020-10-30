package ResourceMonitor.Models;

import ResourceMonitor.Utilities.DBUtility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
// todo: SETTER VALIDATION
public class AverageUsageModel {
    private LocalDate logDate;
    private int cpuUsage;
    private int ramUsage;
    private int hddUsage;


    public AverageUsageModel(LocalDate logDate, int cpuUsage, int ramUsage, int hddUsage) {
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

    public static ArrayList fetchAverageValues(){
        ResultSet result = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");
        ArrayList values = new ArrayList();

        try{
            while (result.next()) {

                LocalDate logDate = result.getDate("logdate").toLocalDate();
                int cpuUsage = result.getInt("CPU");
                int ramUsage = result.getInt("RAM");
                int hddUsage = result.getInt("HDD");

                values.add(new AverageUsageModel(logDate, cpuUsage, ramUsage, hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public String toString() {
        return logDate.toString();
    }
}
