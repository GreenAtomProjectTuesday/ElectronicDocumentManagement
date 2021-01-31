package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.RequestParametersException;
import electonic.document.management.model.Views;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ObjectMapper objectMapper;

    public DepartmentController(DepartmentService departmentService, ObjectMapper objectMapper) {
        this.departmentService = departmentService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createDepartment(Department department) {
        try {
            departmentService.addDepartment(department);
        } catch (RequestParametersException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Department successfully created!");
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentService.getAllDepartments()));
    }

    @DeleteMapping("{department_id}")
    public ResponseEntity<?> deleteDepartments(@PathVariable("department_id") Department department) {
        departmentService.deleteDepartment(department);
        return ResponseEntity.ok("department was successfully deleted");
    }

    @PatchMapping("{department_id}/{new_parent_id}")
    public ResponseEntity<?> moveDepartmentInHierarchy(@PathVariable("department_id") Department department,
                                                       @PathVariable Long new_parent_id) {
        try {
            departmentService.moveDepartmentInHierarchy(department, new_parent_id);
        } catch (RequestParametersException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("department was successfully deleted");
    }

    // TODO: 09.01.2021 add search in tree
    @GetMapping("with_params")
    public ResponseEntity<?> findDepartments(
            @RequestParam(value = "name_contains", required = false) String subStringInName,
            @RequestParam(value = "department_employee_id", required = false) DepartmentEmployee departmentEmployee) throws JsonProcessingException {
        if (subStringInName == null && departmentEmployee == null)
            return ResponseEntity.ok("No parameters were specified");
        List<Department> departmentsWithParams = departmentService.findDepartmentsByExample(subStringInName, departmentEmployee);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }
}
