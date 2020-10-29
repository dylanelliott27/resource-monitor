package ResourceMonitor.Models;

import ResourceMonitor.Utilities.DBUtility;

import java.sql.Array;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AverageUsageModel {
    private HashMap<LocalDate, ArrayList> avgValues = new HashMap<>();

    public HashMap<LocalDate, ArrayList> fetchAverageValues(){
        ResultSet result = DBUtility.fetch("SELECT logdate, AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory group by logdate;");

        try{
            while (result.next()) {
                ArrayList values = new ArrayList();

                LocalDate logDate = result.getDate("logdate").toLocalDate();
                int cpuUsage = result.getInt("CPU");
                int ramUsage = result.getInt("RAM");
                int hddUsage = result.getInt("HDD");
                values.add(cpuUsage);
                values.add(ramUsage);
                values.add(hddUsage);

                avgValues.put(logDate, values);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return avgValues;
    }

    public ArrayList<Number> fetchUsageForDate(LocalDate newDate){
        System.out.println("fetch data");
        ResultSet result = DBUtility.fetch("SELECT AVG(cpuusage) as CPU, AVG(hddspace) as HDD, AVG(ramusage) as RAM from resourcehistory  where logdate = '" + newDate + "' group by logdate;");
        ArrayList<Number> values = new ArrayList<>();
        try{
            while(result.next()){
                int cpuUsage = result.getInt("CPU");
                int ramUsage = result.getInt("RAM");
                int hddUsage = result.getInt("HDD");
                values.add(cpuUsage);
                values.add(ramUsage);
                values.add(hddUsage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return values;
    }

    public ArrayList getUsageForDate(LocalDate newDate){
        // If the date is in the past, and not today, meaning the values won't change,
        // we can check our "cache" instead for the values, and if for some reason not there, query db
        if(!newDate.isEqual(LocalDate.now())){
            System.out.println("check cache");
            if(avgValues.containsKey(newDate)){
                return avgValues.get(newDate);
            }
        }
        // I may change this in future..
        return fetchUsageForDate(newDate);
    }

}
