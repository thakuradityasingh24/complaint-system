package com.cspdcl.complaint_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "complaint_number", unique = true, nullable = false)
    private String complaintNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(name = "ai_categorized")
    private Boolean aiCategorized = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum Category {
        POWER_OUTAGE, HIGH_BILL, METER_ISSUE, NEW_CONNECTION, OTHER
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    public enum Status {
        PENDING, IN_PROGRESS, RESOLVED, CLOSED
    }
}