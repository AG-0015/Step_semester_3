@'
        import java.time.LocalDate;

abstract class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate
    );
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return !endDate.isBefore(startDate);
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate
    ) {
        long days = endDate.toEpochDay() - startDate.toEpochDay() + 1;
        return days <= 3;
    }
}

class Contractor extends Employee {

    public Contractor(String name) {
        super(name);
    }

    @Override
    public boolean isLeaveAllowed(
            LocalDate startDate,
            LocalDate endDate
    ) {
        long days = endDate.toEpochDay() - startDate.toEpochDay() + 1;
        return days <= 2;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates are required.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date."
            );
        }

        if (!employee.isLeaveAllowed(startDate, endDate)) {
            throw new IllegalArgumentException(
                    "Leave policy does not allow this request."
            );
        }

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void approve() {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending requests can be approved."
            );
        }

        status = LeaveStatus.APPROVED;
    }

    public void reject() {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending requests can be rejected."
            );
        }

        status = LeaveStatus.REJECTED;
    }

    public void changeStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Cannot change leave request status from "
                            + status
                            + " to "
                            + newStatus
                            + "."
            );
        }

        if (newStatus == LeaveStatus.PENDING) {
            status = LeaveStatus.PENDING;
        } else if (newStatus == LeaveStatus.APPROVED) {
            approve();
        } else if (newStatus == LeaveStatus.REJECTED) {
            reject();
        }
    }
}

class LeaveService {

    public LeaveRequest submitLeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate
    ) {
        LeaveRequest request =
                new LeaveRequest(employee, startDate, endDate);

        System.out.println(
                "Leave request submitted for "
                        + employee.getName()
                        + " ("
                        + startDate
                        + " to "
                        + endDate
                        + ")."
        );

        System.out.println("Status: " + request.getStatus());

        return request;
    }

    public void approveLeave(
            LeaveRequest request,
            String reviewer
    ) {
        request.approve();

        System.out.println(
                request.getEmployee().getName()
                        + "'s leave request ("
                        + request.getStartDate()
                        + " to "
                        + request.getEndDate()
                        + ") approved by "
                        + reviewer
                        + "."
        );

        System.out.println("Status: " + request.getStatus());
    }

    public void rejectLeave(
            LeaveRequest request,
            String reviewer
    ) {
        request.reject();

        System.out.println(
                request.getEmployee().getName()
                        + "'s leave request ("
                        + request.getStartDate()
                        + " to "
                        + request.getEndDate()
                        + ") rejected by "
                        + reviewer
                        + "."
        );

        System.out.println("Status: " + request.getStatus());
    }
}

public class EmployeeLeaveRequestWorkflow {

    public static void main(String[] args) {
        LeaveService service = new LeaveService();

        FullTimeEmployee john =
                new FullTimeEmployee("John");

        PartTimeEmployee jane =
                new PartTimeEmployee("Jane");

        LeaveRequest johnRequest =
                service.submitLeaveRequest(
                        john,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 5)
                );

        service.approveLeave(johnRequest, "Alice");

        LeaveRequest janeRequest =
                service.submitLeaveRequest(
                        jane,
                        LocalDate.of(2026, 2, 10),
                        LocalDate.of(2026, 2, 11)
                );

        service.rejectLeave(janeRequest, "Bob");

        try {
            johnRequest.changeStatus(LeaveStatus.PENDING);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
'@ | Set-Content "WEEK8\string\assigment_problems\EmployeeLeaveRequestWorkflow.java"