package HRS;

import java.util.Date;

public class AttendanceRecord {
    private String employeeId;
    private Date date;
    private Date clockInTime;
    private Date clockOutTime;
    private boolean isOnLeave;
    private boolean isOvertime;

    public AttendanceRecord(String employeeId, Date date, Date clockInTime, Date clockOutTime) {
        this.employeeId = employeeId;
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.isOnLeave = false;
        this.isOvertime = false;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getDate() {
        return date;
    }

    public Date getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Date clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Date getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(Date clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public boolean isOnLeave() {
        return isOnLeave;
    }

    public void setOnLeave(boolean onLeave) {
        isOnLeave = onLeave;
    }

    public boolean isOvertime() {
        return isOvertime;
    }

    public void setOvertime(boolean overtime) {
        isOvertime = overtime;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "employeeId='" + employeeId + '\'' +
                ", date=" + date +
                ", clockInTime=" + clockInTime +
                ", clockOutTime=" + clockOutTime +
                ", isOnLeave=" + isOnLeave +
                ", isOvertime=" + isOvertime +
                '}';
    }
}
