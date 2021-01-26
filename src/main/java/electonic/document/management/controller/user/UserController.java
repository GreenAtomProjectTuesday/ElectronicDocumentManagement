package electonic.document.management.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Views;
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
    public ResponseEntity<?> addUser(User user) {

        if (!userService.addUser(user)) {
            return ResponseEntity.ok("user already exists!");
        }
        return ResponseEntity.ok("user was registered");
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) throws JsonProcessingException {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(user));
    }

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
                .writerWithView(Views.FullProfile.class)
                .writeValueAsString(userService.getAllUsers()));
    }

    // TODO: 18.01.2021 test it
    @DeleteMapping
    public ResponseEntity<?> deleteUser(HttpServletResponse response,
                                        @AuthenticationPrincipal User currentUser) {
        userService.deleteUser(response, currentUser);
        return ResponseEntity.ok("Your user account was successfully deleted");
    }

    //TODO search by roles
    @GetMapping("with_params")
    public ResponseEntity<?> findMessages(
            @RequestParam(value = "username_contains", required = false) String subStringInUsername,
            @RequestParam(value = "password_contains", required = false) String subStringInPassword,
            @RequestParam(value = "email_contains", required = false) String subStringInEmail) throws JsonProcessingException {
        if (subStringInUsername == null && subStringInPassword == null && subStringInEmail == null)
            return ResponseEntity.ok("No parameters were specified");
        List<User> departmentsWithParams = userService
                .findTasksByExample(subStringInUsername, subStringInPassword, subStringInEmail);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(departmentsWithParams));
    }
}
