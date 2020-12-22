package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.Views;
import electonic.document.management.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        if (!departmentService.addDepartment(department)) {
            return ResponseEntity.ok("Department already exists!");
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
        departmentService.moveDepartmentInHierarchy(department, new_parent_id);
        return ResponseEntity.ok("department was successfully deleted");
    }

}
