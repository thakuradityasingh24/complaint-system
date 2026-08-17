package com.cspdcl.complaint_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffResponse {
    private Integer id;
    private String name;
    private String email;
}