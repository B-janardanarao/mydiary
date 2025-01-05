package lms.bjr.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lms.bjr.com.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    
}

