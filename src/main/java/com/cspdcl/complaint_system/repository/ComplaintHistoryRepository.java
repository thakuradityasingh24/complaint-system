package com.cspdcl.complaint_system.repository;

import com.cspdcl.complaint_system.entity.Complaint;
import com.cspdcl.complaint_system.entity.ComplaintHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintHistoryRepository extends JpaRepository<ComplaintHistory, Integer> {
    List<ComplaintHistory> findByComplaintOrderByChangedAtAsc(Complaint complaint);
}