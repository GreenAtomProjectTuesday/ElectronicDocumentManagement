package electonic.document.management.service.user;

import electonic.document.management.model.user.Role;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.user.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private Cookie cookie;

    @MockBean
    private HttpServletResponse response;

    @Test
    void loadUserByUsername() {
        Assert.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("SomeUser"));
    }

    @Test
    void addUser() {
        User user = new User();

        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
    }

    @Test
    void getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        Assert.assertNotNull(allUsers);
    }

    @Test
    void getUsersByIds() {

    }

    @Test
    void setUserRole() {
        Role role = Role.ADMIN;
        User user = new User();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);

        user.setRole(roleSet);

        boolean isUserRoleSet = userService.setUserRole(role, user);
        Assert.assertTrue(isUserRoleSet);
    }

    @Test
    void deleteUser() {
        User user = new User();

        boolean isDeleteUser = userService.deleteUser(user, response, user);

        Assert.assertTrue(isDeleteUser);
    }

    @Test
    void addEmployee() {
        User user = new User();

        userService.addEmployee(user, "Smith", "8800");

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    void getUser() {

    }
}