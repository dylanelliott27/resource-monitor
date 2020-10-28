package ResourceMonitor.Utilities;

import java.time.LocalDate;

public class UsageRow {
    private LocalDate logDate;
    private int cpuUsage;
    private int ramUsage;
    private int hddUsage;

    public UsageRow(LocalDate logDate, int cpuUsage, int ramUsage, int hddUsage) {
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
}
