package ResourceMonitor.Models;

import java.time.LocalDate;

public class AverageUsageModel {
    private LocalDate logDate;
    private int cpuUsage;
    private int ramUsage;
    private int hddUsage;


    public AverageUsageModel(LocalDate logDate, int cpuUsage, int ramUsage, int hddUsage) {
        setLogDate(logDate);
        setCpuUsage(cpuUsage);
        setRamUsage(ramUsage);
        setHddUsage(hddUsage);
    }

    /**
     *
     * @return = The logDate for the resource instance.
     */
    public LocalDate getLogDate() {
        return logDate;
    }

    /**
     * Validates the date for if it is before the first day of 2020, or later than 2030.
     * @param logDate = The date for when the resource values were logged
     */
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
    /**
     * @return = The average CPU % for the date.
     */
    public int getCpuUsage() {
        return cpuUsage;
    }

    /**
     * Validates the average CPU % by checking if its less than 0, or greater than 100
     * @param cpuUsage = The average CPU % for this date
     */
    public void setCpuUsage(int cpuUsage) {
        // Will always be less than 100 and not negative
        if(cpuUsage < 0){
            throw new IllegalArgumentException("CPU Usage must not be a negative number (less than 0)");
        }
        if(cpuUsage > 100){
            throw new IllegalArgumentException("CPU usage must not be greater than 100");
        }
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return = The average ram % for this date
     */
    public int getRamUsage() {
        return ramUsage;
    }

    /**
     * Validates the average ram usage % by checking if its less than 0, or greater than 100
     * @param ramUsage
     */
    public void setRamUsage(int ramUsage) {
        // Same as CPU, neither negative or greater than 0
        if(ramUsage < 0){
            throw new IllegalArgumentException("RAM Usage must not be a negative number (less than 0)");
        }
        if(ramUsage > 100){
            throw new IllegalArgumentException("RAM usage must not be greater than 100");
        }
        this.ramUsage = ramUsage;
    }

    /**
     * @return = The average percentage of how FULL the HDD is (ex: 50gb of 100gb = 50%)
     */
    public int getHddUsage() {
        return hddUsage;
    }

    /**
     * Validates the average HDD fullness percentage by checking if its less than 0, or greater than 100
     * @param hddUsage
     */
    public void setHddUsage(int hddUsage) {
        // This is referred to as "the percentage of the HDD that is full". Same validation as cpu and ram
        // Also, it is only the FIRST hdd that shows up in my OS query that gets logged
        if(hddUsage < 0){
            throw new IllegalArgumentException("HDD Usage must not be a negative number (less than 0)");
        }
        if(hddUsage > 100){
            throw new IllegalArgumentException("HDD usage must not be greater than 100");
        }
        this.hddUsage = hddUsage;
    }

    /**
     * @return = the logdate for this instance as a string
     */
    @Override
    public String toString() {
        return logDate.toString();
    }
}
