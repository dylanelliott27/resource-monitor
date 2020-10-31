package ResourceMonitor.Models;


import java.time.LocalDate;

public class TableViewModel {
    private LocalDate logDate;
    private int cpuUsage;
    private int ramUsage;
    private int hddUsage;

    public TableViewModel(LocalDate logDate, int cpuUsage, int ramUsage, int hddUsage) {
        setLogDate(logDate);
        setCpuUsage(cpuUsage);
        setRamUsage(ramUsage);
        setHddUsage(hddUsage);
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        LocalDate minDate = LocalDate.parse("2020-01-01");
        LocalDate maxDate = LocalDate.parse("2030-01-01"); // As if this app will be around for 10 years

        if(logDate.isBefore(minDate)){
            throw new IllegalArgumentException("Date must be after 2020-01-01");
        }

        if(logDate.isAfter(maxDate)){
            throw new IllegalArgumentException("Date must be after 2030-01-01");
        }
        this.logDate = logDate;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(int cpuUsage) {
        if(cpuUsage < 0){
            throw new IllegalArgumentException("CPU Usage must not be a negative number (less than 0)");
        }
        if(cpuUsage > 100){
            throw new IllegalArgumentException("CPU usage must not be greater than 100");
        }
        this.cpuUsage = cpuUsage;
    }

    public int getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(int ramUsage) {
        if(ramUsage < 0){
            throw new IllegalArgumentException("RAM Usage must not be a negative number (less than 0)");
        }
        if(ramUsage > 100){
            throw new IllegalArgumentException("RAM usage must not be greater than 100");
        }
        this.ramUsage = ramUsage;
    }

    public int getHddUsage() {
        return hddUsage;
    }

    public void setHddUsage(int hddUsage) {
        if(hddUsage < 0){
            throw new IllegalArgumentException("HDD Usage must not be a negative number (less than 0)");
        }
        if(hddUsage > 100){
            throw new IllegalArgumentException("HDD usage must not be greater than 100");
        }
        this.hddUsage = hddUsage;
    }
}
