package com.leave.tracker.controller;

import com.leave.tracker.dto.LeaveRequestDto;
import com.leave.tracker.dto.LeaveResponseDto;
import com.leave.tracker.entity.LeaveRequest;
import com.leave.tracker.service.LeaveService;
import com.leave.tracker.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
@Slf4j
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ApiResponse<LeaveResponseDto> createLeave(@RequestBody LeaveRequestDto dto) {

        log.info("START CONTROLLER_LAYER createLeave - request DTO: {}", dto);
        ApiResponse<LeaveResponseDto> response = leaveService.submitLeave(dto);
        log.info("END CONTROLLER_LAYER createLeave - response DTO: {}", response.getData());
        return response;
    }

    @GetMapping
    public ApiResponse<List<LeaveResponseDto>> getAllLeaves() {

        log.info("START CONTROLLER_LAYER getAllLeaves");
        ApiResponse<List<LeaveResponseDto>> response = leaveService.getAllLeaves();
        log.info("END CONTROLLER_LAYER getAllLeaves - totalLeaves: {}",
                response.getData() != null ? response.getData().size() : 0);
        return response;
    }

    @PutMapping("/{id}/status")
    public ApiResponse<LeaveResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestParam LeaveRequest.LeaveStatus status) {

        log.info("START CONTROLLER_LAYER updateStatus - id: {}, newStatus: {}", id, status);
        ApiResponse<LeaveResponseDto> response = leaveService.updateStatus(id, status);
        log.info("END CONTROLLER_LAYER updateStatus - updatedLeave DTO: {}", response.getData());
        return response;
    }
}
