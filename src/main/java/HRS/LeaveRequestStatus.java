package HRS;

public enum LeaveRequestStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String status;

    LeaveRequestStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
