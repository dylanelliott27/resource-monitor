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

    public int getCpuValue() {
        return cpuValue;
    }

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

    public int getRamValue() {
        return ramValue;
    }

    public void setRamValue(int ramValue) {
        if(ramValue < 0){
            throw new IllegalArgumentException("RAM Usage must not be a negative number (less than 0)");
        }
        if(ramValue > 100){
            throw new IllegalArgumentException("RAM usage must not be greater than 100");
        }
        this.ramValue = ramValue;
    }

    public int getHddValue() {
        return hddValue;
    }

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
