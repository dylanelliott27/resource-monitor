package ResourceMonitor.Utilities;

import ResourceMonitor.Models.AverageUsageModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * This is an extremely basic raw json string parser. It does not support anything else other than the json format sent from the API.js in utilities,
 * which means no arrays of objects, objects as the value, arrays as the value, or anything like that, just basic key value parsing of one object
 * I wasn't sure if we were allowed to use a third party json parsing library, so I made this instead, it was very quick to code this up anyways
 * If you want to test this, you just need to uncomment the code from Main.java to call the parser and run, as I have the API set up on my server
 */
public class JsonParser {

    /**
     * This is the core of the basic parser. It simply breaks the string up into an array of parts, wherever a comma is located
     * We now have each key value as a string in each index of the array, but there will still be the curly brackets and quotations and commas
     * We then check each array index if they contain either a curly bracket, quotation or comma, and replace it with nothing (remove it).
     * Finally, we break the key value up one more time into another array based on the ":" character,
     * so the first index will be the key, and second will be value. it is put into the hashmap then a AverageUsageModel is constructed
     */
    public static void parseAndInsert(){
        String jsonString = null;
        String[] parts = null;
        HashMap<String, String> jsonValues = new HashMap();

        try{
            HttpURLConnection jsonAPI = (HttpURLConnection) new URL("http://134.122.18.58:8000").openConnection();

            if(jsonAPI.getResponseCode() == 200){
                BufferedReader input = new BufferedReader(new InputStreamReader(jsonAPI.getInputStream()));

                while((jsonString = input.readLine()) != null){
                    System.out.println("Received from my API: " + jsonString);
                    parts = jsonString.split(",");
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
                System.out.println("The JSON values in hashmap: " + jsonValues);
                // Since everything is stored as a string, we need to parse it to it's correct type
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

    /**
     * Constructs a basic SQL insert statement using the values from the model instance
     * @param model = The model constructed from the parser method
     */
    public static void constructSQL(AverageUsageModel model){
        String sql = "INSERT into resourcehistory(logdate, cpuusage, hddspace, ramusage) values ('" + model.getLogDate() + "'," + model.getCpuUsage() + "," + model.getHddUsage() + "," + model.getRamUsage() + ");";
        System.out.println("The JSON as a valid SQL insert: " + sql);
        DBUtility.update(sql);
    }
}
