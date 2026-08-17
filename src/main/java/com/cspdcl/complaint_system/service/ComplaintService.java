package com.cspdcl.complaint_system.service;

import com.cspdcl.complaint_system.dto.*;
import com.cspdcl.complaint_system.entity.Complaint;
import com.cspdcl.complaint_system.entity.ComplaintHistory;
import com.cspdcl.complaint_system.entity.User;
import com.cspdcl.complaint_system.repository.ComplaintHistoryRepository;
import com.cspdcl.complaint_system.repository.ComplaintRepository;
import com.cspdcl.complaint_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.cspdcl.complaint_system.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintHistoryRepository complaintHistoryRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    // FILE A NEW COMPLAINT
    public ComplaintResponse fileComplaint(ComplaintRequest request, String email) {
        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setAddress(request.getAddress());
        complaint.setCitizen(citizen);
        complaint.setComplaintNumber(generateComplaintNumber());

        // Call Python AI module
        try {
            String aiUrl = "http://localhost:5000/analyze";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("description", request.getDescription());
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.exchange(aiUrl, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map aiResult = response.getBody();
                complaint.setCategory(Complaint.Category.valueOf(aiResult.get("category").toString()));
                complaint.setPriority(Complaint.Priority.valueOf(aiResult.get("priority").toString()));
                complaint.setAiCategorized(true);
            }
        } catch (Exception e) {
            // If Python AI is down, set defaults
            complaint.setCategory(Complaint.Category.OTHER);
            complaint.setPriority(Complaint.Priority.MEDIUM);
            complaint.setAiCategorized(false);
        }

        Complaint saved = complaintRepository.save(complaint);
        return mapToResponse(saved);
    }

    // GET COMPLAINTS FOR CITIZEN
    public List<ComplaintResponse> getMyComplaints(String email) {
        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return complaintRepository.findByCitizen(citizen)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // GET COMPLAINTS ASSIGNED TO STAFF
    public List<ComplaintResponse> getAssignedComplaints(String email) {
        User staff = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return complaintRepository.findByAssignedTo(staff)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // GET ALL COMPLAINTS (ADMIN)
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // UPDATE COMPLAINT STATUS (STAFF)
    public ComplaintResponse updateStatus(Integer complaintId, StatusUpdateRequest request, String email) {
        User staff = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        String oldStatus = complaint.getStatus().name();
        complaint.setStatus(request.getStatus());
        complaint.setUpdatedAt(LocalDateTime.now());

        ComplaintHistory history = new ComplaintHistory();
        history.setComplaint(complaint);
        history.setChangedBy(staff);
        history.setOldStatus(oldStatus);
        history.setNewStatus(request.getStatus().name());
        history.setRemark(request.getRemark());

        complaintHistoryRepository.save(history);
        return mapToResponse(complaintRepository.save(complaint));
    }

    // ASSIGN COMPLAINT TO STAFF (ADMIN)
    public ComplaintResponse assignComplaint(Integer complaintId, AssignRequest request) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        User staff = userRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        complaint.setAssignedTo(staff);
        complaint.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(complaintRepository.save(complaint));
    }

    // GET ALL STAFF (ADMIN)
    public List<StaffResponse> getAllStaff() {
        return userRepository.findByRole(User.Role.STAFF)
                .stream()
                .map(u -> new StaffResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }

    // DASHBOARD STATS (ADMIN)
    public DashboardResponse getDashboardStats() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalComplaints(complaintRepository.count());
        response.setPendingComplaints(complaintRepository.countByStatus(Complaint.Status.PENDING));
        response.setInProgressComplaints(complaintRepository.countByStatus(Complaint.Status.IN_PROGRESS));
        response.setResolvedComplaints(complaintRepository.countByStatus(Complaint.Status.RESOLVED));
        response.setClosedComplaints(complaintRepository.countByStatus(Complaint.Status.CLOSED));
        return response;
    }

    // GET COMPLAINT HISTORY
    public List<ComplaintHistory> getComplaintHistory(Integer complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        return complaintHistoryRepository.findByComplaintOrderByChangedAtAsc(complaint);
    }

    // HELPER - Generate unique complaint number
    private String generateComplaintNumber() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "CSPDCL-" + timestamp;
    }

    // HELPER - Map entity to response DTO
    private ComplaintResponse mapToResponse(Complaint complaint) {
        ComplaintResponse response = new ComplaintResponse();
        response.setId(complaint.getId());
        response.setComplaintNumber(complaint.getComplaintNumber());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setCategory(complaint.getCategory().name());
        response.setPriority(complaint.getPriority().name());
        response.setStatus(complaint.getStatus().name());
        response.setAddress(complaint.getAddress());
        response.setCitizenName(complaint.getCitizen().getName());
        response.setAssignedToName(
                complaint.getAssignedTo() != null ? complaint.getAssignedTo().getName() : "Unassigned"
        );
        response.setAiCategorized(complaint.getAiCategorized());
        response.setCreatedAt(complaint.getCreatedAt());
        response.setUpdatedAt(complaint.getUpdatedAt());
        return response;
    }
}