package com.cspdcl.complaint_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.cspdcl.complaint_system.entity.Complaint;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private Complaint.Status status;

    private String remark;
}