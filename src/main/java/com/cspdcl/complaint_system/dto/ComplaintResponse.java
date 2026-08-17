package com.cspdcl.complaint_system.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComplaintResponse {
    private Integer id;
    private String complaintNumber;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;
    private String address;
    private String citizenName;
    private String assignedToName;
    private Boolean aiCategorized;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}