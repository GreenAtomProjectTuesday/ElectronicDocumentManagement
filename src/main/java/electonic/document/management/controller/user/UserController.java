package electonic.document.management.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Task;
import electonic.document.management.model.Views;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.Role;
import electonic.document.management.model.user.User;
import electonic.document.management.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

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

    //TODO search by roles
    @GetMapping("with_params")
    public ResponseEntity<?> findMessages(
            @RequestParam(value = "username_contains", required = false) String subStringInUsername,
            @RequestParam(value = "password_contains", required = false) String subStringInPassword,
            @RequestParam(value = "email_contains", required = false) String subStringInEmail,
            @RequestParam(value = "registration_date", required = false) LocalDateTime registrationDate) throws JsonProcessingException {
        if (subStringInUsername == null && subStringInPassword == null && subStringInEmail == null
                && registrationDate == null)
            return ResponseEntity.ok("No parameters were specified");
        List<User> departmentsWithParams = userService
                .findTasksByExample(subStringInUsername, subStringInPassword, subStringInEmail, registrationDate);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }
}
