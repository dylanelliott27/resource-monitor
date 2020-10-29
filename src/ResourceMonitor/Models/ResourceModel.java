package ResourceMonitor.Models;

public class ResourceModel {
    private int cpuValue;
    private int ramValue;
    private int hddValue;

    public ResourceModel(int cpuValue, int ramValue, int hddValue) {
        this.cpuValue = cpuValue;
        this.ramValue = ramValue;
        this.hddValue = hddValue;
    }

    public int getCpuValue() {
        return cpuValue;
    }

    public void setCpuValue(int cpuValue) {
        this.cpuValue = cpuValue;
    }

    public int getRamValue() {
        return ramValue;
    }

    public void setRamValue(int ramValue) {
        this.ramValue = ramValue;
    }

    public int getHddValue() {
        return hddValue;
    }

    public void setHddValue(int hddValue) {
        this.hddValue = hddValue;
    }
}
