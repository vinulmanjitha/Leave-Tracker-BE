package com.leave.tracker.service;

import com.leave.tracker.dto.LeaveRequestDto;
import com.leave.tracker.dto.LeaveResponseDto;
import com.leave.tracker.entity.LeaveRequest;
import com.leave.tracker.exceptions.ApiException;
import com.leave.tracker.repository.LeaveRequestRepository;
import com.leave.tracker.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRepo;

    public LeaveServiceImpl(LeaveRequestRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    @Override
    public ApiResponse<LeaveResponseDto> submitLeave(LeaveRequestDto dto) {
        log.info("START SERVICE_LAYER submitLeave - request: {}", dto);
        try {
            LeaveRequest entity = LeaveRequest.builder()
                    .employeeName(dto.employeeName())
                    .leaveType(dto.leaveType())
                    .startDate(dto.startDate())
                    .endDate(dto.endDate())
                    .reason(dto.reason())
                    .status(LeaveRequest.LeaveStatus.PENDING)
                    .build();

            LeaveRequest saved = leaveRepo.save(entity);
            log.info("END SERVICE_LAYER submitLeave - savedRequest: {}", saved);

            return new ApiResponse<>(LeaveResponseDto.fromEntity(saved));

        } catch (Exception e) {
            log.error("ERROR SERVICE_LAYER submitLeave - message: {}", e.getMessage(), e);
            throw new ApiException("Failed to submit leave request", e);
        }
    }

    @Override
    public ApiResponse<List<LeaveResponseDto>> getAllLeaves() {
        log.info("START SERVICE_LAYER getAllLeaves");
        try {
            List<LeaveRequest> leaves = leaveRepo.findAll();

            if (leaves.isEmpty()) {
                log.info("END SERVICE_LAYER getAllLeaves - no leave requests found");
                return new ApiResponse<>("No leave requests found");
            }

            List<LeaveResponseDto> dtos = leaves.stream()
                    .map(LeaveResponseDto::fromEntity)
                    .collect(Collectors.toList());

            log.info("END SERVICE_LAYER getAllLeaves - totalLeaves: {}", leaves.size());
            return new ApiResponse<>(dtos);

        } catch (Exception e) {
            log.error("ERROR SERVICE_LAYER getAllLeaves - message: {}", e.getMessage(), e);
            throw new ApiException("Failed to fetch leave requests", e);
        }
    }

    @Override
    public ApiResponse<LeaveResponseDto> updateStatus(Long id, LeaveRequest.LeaveStatus status) {
        log.info("START SERVICE_LAYER updateStatus - id: {}, newStatus: {}", id, status);
        try {
            LeaveRequest req = leaveRepo.findById(id)
                    .orElseThrow(() -> new ApiException("Leave request not found with id: " + id));

            req.setStatus(status);
            LeaveRequest updated = leaveRepo.save(req);

            log.info("END SERVICE_LAYER updateStatus - updatedLeave: {}", updated);
            return new ApiResponse<>(LeaveResponseDto.fromEntity(updated));

        } catch (ApiException e) {
            log.warn("WARN SERVICE_LAYER updateStatus - {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("ERROR SERVICE_LAYER updateStatus - message: {}", e.getMessage(), e);
            throw new ApiException("Failed to update leave status", e);
        }
    }
}
