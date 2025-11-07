package com.leave.tracker.controller;


import com.leave.tracker.dto.LeaveRequestDto;
import com.leave.tracker.dto.LeaveResponseDto;
import com.leave.tracker.entity.LeaveRequest;
import com.leave.tracker.service.LeaveService;
import com.leave.tracker.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LeaveControllerTest {

    @Mock
    private LeaveService leaveService;

    @InjectMocks
    private LeaveController leaveController;

    private LeaveRequestDto leaveRequestDto;
    private LeaveResponseDto leaveResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        leaveRequestDto = new LeaveRequestDto(
                "EMP123",
                "John Doe",
                "ANNUAL",
                LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 15),
                "Vacation"
        );

        leaveResponseDto = new LeaveResponseDto(
                1L,
                "John Doe",
                "EMP123",
                "ANNUAL",
                LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 15),
                "Vacation",
                "Manager",
                LeaveRequest.LeaveStatus.PENDING,
                LocalDate.now()
        );
    }

    @Test
    void testCreateLeave() {
        ApiResponse<LeaveResponseDto> expectedResponse = new ApiResponse<>(leaveResponseDto);
        when(leaveService.submitLeave(leaveRequestDto)).thenReturn(expectedResponse);

        ApiResponse<LeaveResponseDto> actualResponse = leaveController.createLeave(leaveRequestDto);

        assertTrue(actualResponse.isSuccess());
        assertNotNull(actualResponse.getData());
        assertEquals("EMP123", actualResponse.getData().employeeId());
        verify(leaveService, times(1)).submitLeave(leaveRequestDto);
    }

    @Test
    void testGetAllLeaves() {
        List<LeaveResponseDto> leaveList = List.of(leaveResponseDto);
        ApiResponse<List<LeaveResponseDto>> expectedResponse = new ApiResponse<>(leaveList);
        when(leaveService.getAllLeaves()).thenReturn(expectedResponse);

        ApiResponse<List<LeaveResponseDto>> actualResponse = leaveController.getAllLeaves();

        assertTrue(actualResponse.isSuccess());
        assertEquals(1, actualResponse.getData().size());
        assertEquals("John Doe", actualResponse.getData().get(0).employeeName());
        verify(leaveService, times(1)).getAllLeaves();
    }

    @Test
    void testUpdateStatus() {
        LeaveRequest.LeaveStatus newStatus = LeaveRequest.LeaveStatus.APPROVED;
        LeaveResponseDto updatedResponse = new LeaveResponseDto(
                1L,
                "John Doe",
                "EMP123",
                "ANNUAL",
                LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 15),
                "Vacation",
                "Manager",
                newStatus,
                LocalDate.now()
        );

        ApiResponse<LeaveResponseDto> expectedResponse = new ApiResponse<>(updatedResponse);
        when(leaveService.updateStatus(1L, newStatus)).thenReturn(expectedResponse);

        ApiResponse<LeaveResponseDto> actualResponse = leaveController.updateStatus(1L, newStatus);

        assertTrue(actualResponse.isSuccess());
        assertEquals(newStatus, actualResponse.getData().status());
        verify(leaveService, times(1)).updateStatus(1L, newStatus);
    }
}