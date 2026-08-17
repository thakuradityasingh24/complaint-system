package com.cspdcl.complaint_system.controller;

import com.cspdcl.complaint_system.dto.ComplaintResponse;
import com.cspdcl.complaint_system.dto.StatusUpdateRequest;
import com.cspdcl.complaint_system.entity.ComplaintHistory;
import com.cspdcl.complaint_system.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final ComplaintService complaintService;

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAssignedComplaints(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                complaintService.getAssignedComplaints(userDetails.getUsername()));
    }

    @PutMapping("/complaints/{id}/status")
    public ResponseEntity<ComplaintResponse> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody StatusUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                complaintService.updateStatus(id, request, userDetails.getUsername()));
    }

    @GetMapping("/complaints/{id}/history")
    public ResponseEntity<List<ComplaintHistory>> getHistory(@PathVariable Integer id) {
        return ResponseEntity.ok(complaintService.getComplaintHistory(id));
    }
}