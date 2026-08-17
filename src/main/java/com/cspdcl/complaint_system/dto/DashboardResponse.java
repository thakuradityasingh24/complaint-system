package com.cspdcl.complaint_system.dto;

import lombok.Data;

@Data
public class DashboardResponse {
    private Long totalComplaints;
    private Long pendingComplaints;
    private Long inProgressComplaints;
    private Long resolvedComplaints;
    private Long closedComplaints;
}