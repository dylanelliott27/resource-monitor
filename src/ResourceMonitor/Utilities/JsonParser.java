package ResourceMonitor.Utilities;

import ResourceMonitor.Models.AverageUsageModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonParser {
    public static void parse(){
        String currentLine = null;
        String[] parts = null;
        HashMap<String, String> jsonValues = new HashMap();
        ArrayList objs = new ArrayList();

        try{
            HttpURLConnection jsonAPI = (HttpURLConnection) new URL("http://localhost:3000").openConnection();

            if(jsonAPI.getResponseCode() == 200){
                BufferedReader input = new BufferedReader(new InputStreamReader(jsonAPI.getInputStream()));

                while((currentLine = input.readLine()) != null){
                    parts = currentLine.split(",");
                }

                for(int i = 0; i < parts.length; i++){
                    if(parts[i].contains("{")){
                        parts[i] = parts[i].replace("{", "");
                    }
                    if(parts[i].contains("}")){
                        parts[i] = parts[i].replace("}", "");
                    }
                    if(parts[i].contains("\"")){
                        parts[i] = parts[i].replace("\"", "");
                    }
                    String[] keyValue = parts[i].split(":");
                    jsonValues.put(keyValue[0], keyValue[1]);
                }

                LocalDate logDate = LocalDate.parse(jsonValues.get("logdate"));
                int cpuUsage = Integer.parseInt(jsonValues.get("cpuusage"));
                int ramUsage = Integer.parseInt(jsonValues.get("ramusage"));
                int hddUsage = Integer.parseInt(jsonValues.get("hddusage"));

                constructSQL(new AverageUsageModel(logDate, cpuUsage, ramUsage, hddUsage));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void constructSQL(AverageUsageModel model){

    }
}
