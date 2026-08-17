package com.cspdcl.complaint_system.controller;

import com.cspdcl.complaint_system.dto.AssignRequest;
import com.cspdcl.complaint_system.dto.ComplaintResponse;
import com.cspdcl.complaint_system.dto.DashboardResponse;
import com.cspdcl.complaint_system.dto.StaffResponse;
import com.cspdcl.complaint_system.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.cspdcl.complaint_system.dto.StaffResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ComplaintService complaintService;

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/complaints/{id}/assign")
    public ResponseEntity<ComplaintResponse> assignComplaint(
            @PathVariable Integer id,
            @Valid @RequestBody AssignRequest request) {
        return ResponseEntity.ok(complaintService.assignComplaint(id, request));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(complaintService.getDashboardStats());
    }

    @GetMapping("/staff")
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return ResponseEntity.ok(complaintService.getAllStaff());
    }
}