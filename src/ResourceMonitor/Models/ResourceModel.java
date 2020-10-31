package ResourceMonitor.Models;

public class ResourceModel {
    private int cpuValue;
    private int ramValue;
    private int hddValue;

    public ResourceModel(int cpuValue, int ramValue, int hddValue) {
        setCpuValue(cpuValue);
        setRamValue(ramValue);
        setHddValue(hddValue);
    }

    /**
     * @return = The current CPU utilization % for the OS
     */
    public int getCpuValue() {
        return cpuValue;
    }

    /**
     * Validates the CPU value is not less than 0 or greater than 100
     * @param cpuValue = The current CPU % for the OS
     */
    public void setCpuValue(int cpuValue) {
        // Validation is same as used in the AverageUsage model for all three instance variables
        if(cpuValue < 0){
            throw new IllegalArgumentException("CPU Usage must not be a negative number (less than 0)");
        }
        if(cpuValue > 100){
            throw new IllegalArgumentException("CPU usage must not be greater than 100");
        }
        this.cpuValue = cpuValue;
    }

    /**
     * @return = the current RAM utilization %
     */
    public int getRamValue() {
        return ramValue;
    }

    /**
     * Validates and sets the ram utilization %. Check if ramValue is less than 0 or greater than 100
     * @param ramValue = the current ram utilization % on your OS
     */
    public void setRamValue(int ramValue) {
        if(ramValue < 0){
            throw new IllegalArgumentException("RAM Usage must not be a negative number (less than 0)");
        }
        if(ramValue > 100){
            throw new IllegalArgumentException("RAM usage must not be greater than 100");
        }
        this.ramValue = ramValue;
    }

    /**
     * @return = the current HDD "fullness" percentage. (ex: 50gb of 100gb = 50%);
     */
    public int getHddValue() {
        return hddValue;
    }

    /**
     * Validates the hddVAlue is not less than 0 or greater than 100, then sets it.
     * @param hddValue = the current HDD "fullness" percentage. (ex: 50gb of 100gb = 50gb);
     */
    public void setHddValue(int hddValue) {
        if(hddValue < 0){
            throw new IllegalArgumentException("HDD Usage must not be a negative number (less than 0)");
        }
        if(hddValue > 100){
            throw new IllegalArgumentException("HDD usage must not be greater than 100");
        }
        this.hddValue = hddValue;
    }
}
