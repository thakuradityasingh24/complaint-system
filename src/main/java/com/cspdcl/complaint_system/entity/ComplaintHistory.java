package com.cspdcl.complaint_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaint_history")
public class ComplaintHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @Column(name = "old_status")
    private String oldStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    private String remark;

    @Column(name = "changed_at")
    private LocalDateTime changedAt = LocalDateTime.now();
}