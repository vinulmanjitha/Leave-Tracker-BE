package com.leave.tracker.dto;

import java.time.LocalDate;

public record LeaveRequestDto(
        String employeeId,
        String employeeName,
        String leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason
) {

}
