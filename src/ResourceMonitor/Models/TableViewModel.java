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

    /**
     * @return = the logged date for this set of values
     */
    public LocalDate getLogDate() {
        return logDate;
    }

    /**
     * verifies the date is not before the first day of 2020, and not later than 2030, then sets it.
     * @param logDate = the date these resource values were logged
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
     * @return = The average CPU utilization % for this day
     */
    public int getCpuUsage() {
        return cpuUsage;
    }

    /**
     * Validates the average CPU % by making sure it's not less than 0 or greater than 100, then sets it.
     * @param cpuUsage = the average CPU utilization % for this date
     */
    public void setCpuUsage(int cpuUsage) {
        if(cpuUsage < 0){
            throw new IllegalArgumentException("CPU Usage must not be a negative number (less than 0)");
        }
        if(cpuUsage > 100){
            throw new IllegalArgumentException("CPU usage must not be greater than 100");
        }
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return = The average RAM % usage for this day
     */
    public int getRamUsage() {
        return ramUsage;
    }

    /**
     * Validates the ram usage % by making sure its not less than 0 or greater than 100, then sets it.
     * @param ramUsage
     */
    public void setRamUsage(int ramUsage) {
        if(ramUsage < 0){
            throw new IllegalArgumentException("RAM Usage must not be a negative number (less than 0)");
        }
        if(ramUsage > 100){
            throw new IllegalArgumentException("RAM usage must not be greater than 100");
        }
        this.ramUsage = ramUsage;
    }

    /**
     * @return = the HDD "Fullness" percentage, (Ex: 50gb of 100gb = 50%);
     */
    public int getHddUsage() {
        return hddUsage;
    }

    /**
     * validates the HDD percentage by making sure its not less than 0 or greater than 100, then sets it.
     * @param hddUsage = the HDD average "fullness" percentage (ex: 50gb of 100gb = 50%);
     */
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
