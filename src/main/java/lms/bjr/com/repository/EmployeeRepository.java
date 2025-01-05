package lms.bjr.com.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lms.bjr.com.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	 Optional<Employee> findById(Long id); 
}

