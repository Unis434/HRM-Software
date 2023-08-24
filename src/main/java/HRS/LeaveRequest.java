package HRS;

import java.util.Date;

public class LeaveRequest {
    private int id;
    private String employeeId;
    private Date startDate;
    private Date endDate;
    private LeaveRequestStatus status;

    public LeaveRequest(int id, String employeeId, Date startDate, Date endDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveRequestStatus.PENDING; // Default status is PENDING
    }

    public LeaveRequest(Employee employee1, String paidLeave, int i) {
                }

    public LeaveRequest(Employee employee1, LeaveType paidLeave, int i) {
    }

    public int getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public LeaveRequestStatus getStatus() {
        return status;
    }

    public void approve() {
        status = LeaveRequestStatus.APPROVED;
    }

    public void reject() {
        status = LeaveRequestStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "LeaveRequest{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }

    public Employee getEmployee() {
        return null;
    }
}
