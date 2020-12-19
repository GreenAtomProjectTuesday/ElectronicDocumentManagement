package electonic.document.management.controller.user;

import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.service.user.EmployeeService;
import electonic.document.management.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UserService userService;

    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestParam("user_id") User user,
                                         @RequestParam("full_name") String fullName,
                                         @RequestParam("phone") String phone) {
        boolean employeeExists = employeeService.employeeExistsCheck(user.getId());
        if (employeeExists) {
            return ResponseEntity.ok("Such employee already exists");
        }
        userService.addEmployee(user, fullName, phone);
        return ResponseEntity.ok("employee was registered");
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{employee_id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee_id") Employee employee) {
        employeeService.deleteEmployee(employee);
        return ResponseEntity.ok("");
    }

    @PatchMapping("{employee_id}")
    public ResponseEntity<?> editEmployee(@PathVariable("employee_id") Employee employeeFromDb,
                                          @RequestBody Employee employee) {
        employeeService.editEmployee(employee, employeeFromDb);
        return ResponseEntity.ok("");
    }
}
