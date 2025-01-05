package lms.bjr.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import lms.bjr.com.entity.Employee;
import lms.bjr.com.entity.Leave;
import lms.bjr.com.service.EmployeeService;
import lms.bjr.com.service.LeaveService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public String listEmployees() {
        return "main";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/home")
    public String login(@RequestParam Long id,
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest request) {

        Employee user = employeeService.findById(id);

        if (user != null && user.getPassword().equals(password)) {
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin";
            } else if ("USER".equals(user.getRole())) {
                return "redirect:/userDashboard";
            }
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @PostMapping("/update")
    public String updatePassword(@RequestParam("id") Long id, 
                                 @RequestParam("password") String password, 
                                 Model model) {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setPassword(password);
            employeeService.saveEmployee(employee);
            model.addAttribute("message", "Password updated successfully!");
        } else {
            model.addAttribute("error", "Employee ID not found.");
        }

        return "register";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/admin/employeeReport")
    public String showEmployeeReport(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("report", employees);
        return "employeeReport";
    }

    @GetMapping("/admin/addEmployee")
    public String showRegisterPage(Model model) {
        model.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin";
    }

    @GetMapping("/admin/leaveReport")
    public String leaveReport(Model model) {
        List<Leave> Reports = leaveService.getAllLeaves();
        model.addAttribute("leaveReports", Reports);
        return "leaveReport";
    }

    @PostMapping("/admin/leave/updateLeaveStatus")
    public String updateLeaveStatus(@RequestParam Long id, @RequestParam String status) {
        leaveService.updateLeaveStatus(id, status);
        return "redirect:/admin";
    }

    @GetMapping("/userDashboard")
    public String showUserDashboard(Model model) {
        return "userDashboard";
    }

    @GetMapping("/userDashboard/leave-record")
    public String getLeaveReport(Model model) {
        List<Leave> Reports = leaveService.getAllLeaves();
        model.addAttribute("leaveReports", Reports);
        return "leave-record";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin";
    }

    @GetMapping("/userDashboard/leave-form")
    public String leaveform(Model model) {
        return "leave-form";
    }

    @PostMapping("/leave/apply")
    public String applyForLeave(@RequestParam String name,
                                @RequestParam String leaveType,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                @RequestParam String reason) {

        Leave leave = new Leave();
        leave.setName(name);
        leave.setLeaveType(leaveType);
        leave.setFromdate(fromDate);
        leave.setTodate(toDate);
        leave.setReason(reason);
        leave.setStatus("Pending");

        leaveService.saveLeave(leave);

        return "redirect:/userDashboard";
    }
}
