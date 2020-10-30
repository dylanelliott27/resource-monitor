package ResourceMonitor.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandUtility {
    private HashMap<String, Long> finalValues = new HashMap();

    public HashMap<String, Number> getAllValues(){
        String currentBlock = null;
        String currentLine = null;

        try{
            ProcessBuilder resourceFetch = new ProcessBuilder("cmd", "/c", "wmic", "computersystem", "get", "TotalPhysicalMemory", "&&", "wmic", "os", "get", "freephysicalmemory", "&&", "wmic", "cpu", "get", "loadpercentage", "&&", "wmic", "logicaldisk", "get", "freespace", "&&", "wmic", "logicaldisk", "get", "size");
            Process p = resourceFetch.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // This could definitely be improved, however it correctly maps each value and header into the hashmap
            // Was thinking about branching out each command into separate methods, however this will work for now
            while ( (currentLine = reader.readLine()) != null) {
                if(!currentLine.isEmpty()){ // "line" is sometimes an empty value
                    // If the line being parsed is a header line... set the current block, so we know that the next line will be a value..
                    if(currentLine.contains("TotalPhysicalMemory") || currentLine.contains("FreePhysicalMemory") || currentLine.contains("LoadPercentage") || currentLine.contains("FreeSpace") || currentLine.contains("Size")){
                        currentBlock = currentLine.trim();
                        continue; // Move to next iteration so the below if statement only runs on the next line.. the value
                    }
                    if(currentBlock != null){
                        // Once the header is set above, this statement gets run then gets put back to null.
                        // Reason being is so that only the FIRST value after the header is captured.
                        // In the event of the HDD command (it can print more than one value per hdd)
                        // We only need one for now...
                        finalValues.put(currentBlock, Long.valueOf(currentLine.trim()));
                        currentBlock = null;
                    }
                }
            }
            p.destroy(); // Kill process
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return parseValues();
    }

    public HashMap<String, Number> parseValues(){
        HashMap<String, Number> resourceValues = new HashMap();

        // All values from the CMD commands return in bytes, except for freephysicalmemory which is in kb...
        long usedMemory = (finalValues.get("TotalPhysicalMemory") - (finalValues.get("FreePhysicalMemory") * 1024));
        long usedDisk = (finalValues.get("Size") - (finalValues.get("FreeSpace")));
        double memoryPercentage = ((double) usedMemory / finalValues.get("TotalPhysicalMemory")) * 100;
        double cpuPercentage = finalValues.get("LoadPercentage");
        double diskPercentage = ((double) usedDisk / finalValues.get("Size")) * 100; // This is a representation of how full your HDD is
        // Cast to int as we don't need a decimal value.. atleast for now anyways
        resourceValues.put("Memory",(int) memoryPercentage);
        resourceValues.put("CPU",(int) cpuPercentage);
        resourceValues.put("HDD",(int) diskPercentage);

        return resourceValues;
    }
}
