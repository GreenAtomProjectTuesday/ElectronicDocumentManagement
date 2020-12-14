package electonic.document.management.controller.user;

import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.service.user.EmployeeService;
import electonic.document.management.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{employee_id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee_id") Employee employee) {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }

    @PatchMapping("{employee_id}")
    public ResponseEntity<?> editEmployee(@PathVariable("employee_id") Employee employee,
                                          @RequestParam(name = "full_name", required = false) String newFullName,
                                          @RequestParam(name = "phone", required = false) String phone) {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }
}
