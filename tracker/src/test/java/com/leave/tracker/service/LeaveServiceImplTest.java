package com.leave.tracker.service;


import com.leave.tracker.dto.LeaveRequestDto;
import com.leave.tracker.dto.LeaveResponseDto;
import com.leave.tracker.entity.EmailDetails;
import com.leave.tracker.entity.LeaveRequest;
import com.leave.tracker.exceptions.ApiException;
import com.leave.tracker.repository.LeaveRequestRepository;
import com.leave.tracker.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceImplTest {

    @Mock
    private LeaveRequestRepository leaveRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private LeaveRequestDto leaveRequestDto;
    private LeaveRequest leaveRequestEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        leaveRequestDto = new LeaveRequestDto(
                "EMP001",
                "Amal",
                "SICK",
                LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 12),
                "Fever"
        );

        leaveRequestEntity = LeaveRequest.builder()
                .id(1L)
                .employeeId("EMP001")
                .employeeName("Amal")
                .leaveType("SICK")
                .startDate(LocalDate.of(2025, 11, 10))
                .endDate(LocalDate.of(2025, 11, 12))
                .reason("Fever")
                .status(LeaveRequest.LeaveStatus.PENDING)
                .build();
    }

    @Test
    void testSubmitLeave_Success() {
        when(leaveRepo.save(any(LeaveRequest.class))).thenReturn(leaveRequestEntity);

        ApiResponse<LeaveResponseDto> response = leaveService.submitLeave(leaveRequestDto);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("EMP001", response.getData().employeeId());
        verify(leaveRepo, times(1)).save(any(LeaveRequest.class));
    }

    @Test
    void testSubmitLeave_Exception() {
        when(leaveRepo.save(any(LeaveRequest.class))).thenThrow(new RuntimeException("DB Error"));
        assertThrows(ApiException.class, () -> leaveService.submitLeave(leaveRequestDto));
        verify(leaveRepo, times(1)).save(any(LeaveRequest.class));
    }

    @Test
    void testGetAllLeaves_Success() {
        when(leaveRepo.findAll()).thenReturn(List.of(leaveRequestEntity));

        ApiResponse<List<LeaveResponseDto>> response = leaveService.getAllLeaves();

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals("Amal", response.getData().get(0).employeeName());
        verify(leaveRepo, times(1)).findAll();
    }

    @Test
    void testGetAllLeaves_Empty() {
        when(leaveRepo.findAll()).thenReturn(Collections.emptyList());

        ApiResponse<List<LeaveResponseDto>> response = leaveService.getAllLeaves();

        assertFalse(response.isSuccess());
        assertEquals("No leave requests found", response.getMessage());
        verify(leaveRepo, times(1)).findAll();
    }

    @Test
    void testUpdateStatus_Success() {
        LeaveRequest.LeaveStatus newStatus = LeaveRequest.LeaveStatus.APPROVED;

        LeaveRequest updated = LeaveRequest.builder()
                .id(1L)
                .employeeId("EMP001")
                .employeeName("Amal")
                .leaveType("SICK")
                .startDate(LocalDate.of(2025, 11, 10))
                .endDate(LocalDate.of(2025, 11, 12))
                .reason("Fever")
                .status(newStatus)
                .approvedBy("authVinul")
                .build();

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(leaveRequestEntity));
        when(leaveRepo.save(any(LeaveRequest.class))).thenReturn(updated);

        ApiResponse<LeaveResponseDto> response = leaveService.updateStatus(1L, newStatus);

        assertTrue(response.isSuccess());
        assertEquals(newStatus, response.getData().status());
        assertEquals("authVinul", response.getData().approvedBy());
        verify(leaveRepo, times(1)).findById(1L);
        verify(leaveRepo, times(1)).save(any(LeaveRequest.class));
        verify(emailService, times(1)).sendMail(any(EmailDetails.class));
    }

    @Test
    void testUpdateStatus_NotFound() {
        when(leaveRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> leaveService.updateStatus(1L, LeaveRequest.LeaveStatus.APPROVED));
        verify(leaveRepo, times(1)).findById(1L);
        verify(emailService, never()).sendMail(any());
    }
}