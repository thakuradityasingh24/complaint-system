package com.cspdcl.complaint_system.controller;

import com.cspdcl.complaint_system.dto.ComplaintRequest;
import com.cspdcl.complaint_system.dto.ComplaintResponse;
import com.cspdcl.complaint_system.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
@RequiredArgsConstructor
public class CitizenController {

    private final ComplaintService complaintService;

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponse> fileComplaint(
            @Valid @RequestBody ComplaintRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                complaintService.fileComplaint(request, userDetails.getUsername()));
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                complaintService.getMyComplaints(userDetails.getUsername()));
    }
}