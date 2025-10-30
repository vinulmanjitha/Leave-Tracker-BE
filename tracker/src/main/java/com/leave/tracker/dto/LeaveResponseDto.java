package com.leave.tracker.dto;

import com.leave.tracker.entity.LeaveRequest;
import java.time.LocalDate;

public record LeaveResponseDto(
        Long id,
        String employeeName,
        String leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveRequest.LeaveStatus status,
        LocalDate createdAt
) {
    // Static mapper from entity → DTO
    public static LeaveResponseDto fromEntity(LeaveRequest entity) {
        return new LeaveResponseDto(
                entity.getId(),
                entity.getEmployeeName(),
                entity.getLeaveType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getReason(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
