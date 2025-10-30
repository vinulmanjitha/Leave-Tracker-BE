package com.leave.tracker.service;

import com.leave.tracker.dto.LeaveRequestDto;
import com.leave.tracker.dto.LeaveResponseDto;
import com.leave.tracker.entity.LeaveRequest;
import com.leave.tracker.util.ApiResponse;

import java.util.List;

public interface LeaveService {
    ApiResponse<LeaveResponseDto> submitLeave(LeaveRequestDto request);
    ApiResponse<List<LeaveResponseDto>> getAllLeaves();
    ApiResponse<LeaveResponseDto> updateStatus(Long id, LeaveRequest.LeaveStatus status);
}
