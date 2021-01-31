package electonic.document.management.controller.user;

import electonic.document.management.model.Department;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.Position;
import electonic.document.management.service.user.DepartmentEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("department_employees")
public class DepartmentEmployeeController {

    private final DepartmentEmployeeService departmentEmployeeService;

    public DepartmentEmployeeController(DepartmentEmployeeService departmentEmployeeService) {
        this.departmentEmployeeService = departmentEmployeeService;
    }

    @PostMapping
    // TODO: 14.12.2020 add default position after registration?
    public ResponseEntity<?> addDepartmentEmployee(@RequestParam("employee_id") Employee employee,
                                                   @RequestParam("department_id") Department department) {
        if (!departmentEmployeeService.addDepartmentEmployee(department, employee)) {
            return ResponseEntity.ok("This employee has already been added to this department." +
                    " Do you want to add a position?");
        }
        return ResponseEntity.ok("Department employee was registered");
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartmentEmployees() {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{department_employee_id}")
    public ResponseEntity<?> deleteDepartmentEmployee(@PathVariable("department_employee_id") DepartmentEmployee employee) {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }

    @PostMapping("{department_employee_id}/positions")
    public ResponseEntity<?> setDepartmentEmployeePosition(@PathVariable("department_employee_id") DepartmentEmployee employee,
                                                           @RequestParam("duty") Position position) {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{department_employee_id}/positions/{position}")
    public ResponseEntity<?> deleteDepartmentEmployeePosition(@PathVariable("department_employee_id") DepartmentEmployee employee,
                                                              @PathVariable("position") Position position) {
        // TODO: 14.12.2020 not implemented
        return ResponseEntity.ok("");
    }
}
