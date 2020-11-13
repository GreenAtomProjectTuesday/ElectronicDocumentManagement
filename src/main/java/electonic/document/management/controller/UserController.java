package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.Role;
import electonic.document.management.model.User;
import electonic.document.management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("registration")
    //TODO check for field on this level or frontend?
    public ResponseEntity<String> addUser(User user) {

        if (!userService.addUser(user)) {
            return ResponseEntity.ok("user already exists!");
        }
        return ResponseEntity.ok("user was registered");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("setRole")
    public ResponseEntity<String> setRole(@RequestParam("role") Role role,
                                          @RequestParam("user_id") User user) {
        userService.setUserRole(role, user);
        return ResponseEntity.ok("User role was successfully added!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("setDepartment")
    public ResponseEntity<String> addUserToDepartment(@RequestParam("department_id") Department department,
                                                      @RequestParam("user_id") User user) {
        userService.addUserToDepartment(department, user);
        return ResponseEntity.ok("User was successfully added to department!");
    }

    @GetMapping("phonebook")
    public ResponseEntity<String> getAllUsers() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(userService.getAllUsers()));
    }
}
