package com.leave.tracker.dto;

import com.leave.tracker.entity.LeaveRequest;
import java.time.LocalDate;

public record LeaveResponseDto(
        Long id,
        String employeeName,
        String employeeId,
        String leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        String approvedBy,
        LeaveRequest.LeaveStatus status,
        LocalDate createdAt
) {
    public static LeaveResponseDto fromEntity(LeaveRequest entity) {
        return new LeaveResponseDto(
                entity.getId(),
                entity.getEmployeeName(),
                entity.getEmployeeId(),
                entity.getLeaveType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getReason(),
                entity.getApprovedBy(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
