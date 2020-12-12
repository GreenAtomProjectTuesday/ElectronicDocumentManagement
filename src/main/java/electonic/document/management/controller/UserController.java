package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.user.Role;
import electonic.document.management.model.user.User;
import electonic.document.management.model.Views;
import electonic.document.management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    //TODO check for field on this level or frontend?
    public ResponseEntity<?> addUser(User user) {

        if (!userService.addUser(user)) {
            return ResponseEntity.ok("user already exists!");
        }
        return ResponseEntity.ok("user was registered");
    }

    // TODO consider replacing role with roleSet
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{user_id}/roles")
    public ResponseEntity<?> setRole(@RequestParam("role") Role role,
                                          @PathVariable("user_id") User user) {
        if (!userService.setUserRole(role, user)) {
            return ResponseEntity.ok("User with such id is not exists!");
        }
        return ResponseEntity.ok("User role was successfully added!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{user_id}/departments/{department_id}")
    public ResponseEntity<?> addUserToDepartment(@PathVariable("department_id") Department department,
                                                      @PathVariable("user_id") User user) {
        userService.addUserToDepartment(department, user);
        return ResponseEntity.ok("User was successfully added to department!");
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.IdName.class)
                .writeValueAsString(userService.getAllUsers()));
    }

    //TODO can't delete user because of creator id in task
    //TODO think about logic for delete user
    @DeleteMapping("{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") User user,
                                             HttpServletResponse response,
                                             @AuthenticationPrincipal User currentUser) {
        if (!userService.deleteUser(user, response, currentUser))
            return ResponseEntity.ok("U can't delete another user");
        return ResponseEntity.ok("User was successfully deleted");
    }
}
