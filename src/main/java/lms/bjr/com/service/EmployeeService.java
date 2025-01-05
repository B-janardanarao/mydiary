package lms.bjr.com.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms.bjr.com.entity.Employee;
import lms.bjr.com.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    
    public Employee findById(Long id) {
        return employeeRepository.findById(id)  
                .orElse(null); 
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
