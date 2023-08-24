package HRS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveManager {
    private List<LeaveRequest> leaveRequests;

    public LeaveManager() {
        leaveRequests = new ArrayList<>();
        // Initialize leave requests from a database or other data source if needed
    }

    // Add a leave request to the list
    public void addLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequests.add(leaveRequest);
        // You can also save the leave request to a database or other data source here
    }

    // Get a list of all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequests;
    }

    // Find leave requests for a specific employee
    public List<LeaveRequest> findLeaveRequestsByEmployeeId(String employeeId) {
        List<LeaveRequest> employeeLeaveRequests = new ArrayList<>();
        for (LeaveRequest leaveRequest : leaveRequests) {
            if (leaveRequest.getEmployeeId().equals(employeeId)) {
                employeeLeaveRequests.add(leaveRequest);
            }
        }
        return employeeLeaveRequests;
    }

    // Find leave requests for a specific date range
    public List<LeaveRequest> findLeaveRequestsByDateRange(Date startDate, Date endDate) {
        List<LeaveRequest> dateRangeLeaveRequests = new ArrayList<>();
        for (LeaveRequest leaveRequest : leaveRequests) {
            Date leaveStartDate = leaveRequest.getStartDate();
            Date leaveEndDate = leaveRequest.getEndDate();
            if ((leaveStartDate.compareTo(startDate) >= 0 && leaveStartDate.compareTo(endDate) <= 0) ||
                    (leaveEndDate.compareTo(startDate) >= 0 && leaveEndDate.compareTo(endDate) <= 0) ||
                    (leaveStartDate.compareTo(startDate) <= 0 && leaveEndDate.compareTo(endDate) >= 0)) {
                dateRangeLeaveRequests.add(leaveRequest);
            }
        }
        return dateRangeLeaveRequests;
    }

    public LeaveRequest getPendingLeaveRequest() {
        return null;
    }

    public Object approveLeave(LeaveRequest leaveRequest) {
        return null;
    }
}

// Other methods for managing leave requests can be added as needed

// Print a list of all leave requests
