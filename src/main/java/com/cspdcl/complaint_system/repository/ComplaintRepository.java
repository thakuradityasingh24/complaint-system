package com.cspdcl.complaint_system.repository;

import com.cspdcl.complaint_system.entity.Complaint;
import com.cspdcl.complaint_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByCitizen(User citizen);
    List<Complaint> findByAssignedTo(User staff);
    List<Complaint> findByStatus(Complaint.Status status);
    Long countByStatus(Complaint.Status status);
}