package lms.bjr.com.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms.bjr.com.entity.Leave;
import lms.bjr.com.repository.LeaveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Optional<Leave> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    public Leave saveLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public void updateLeaveStatus(Long id, String status) {
        System.out.println("Received request to update leave ID: " + id + " to status: " + status);
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(status);
        leaveRepository.save(leave);
        System.out.println("Status updated successfully for leave ID: " + id);
    }


    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}

