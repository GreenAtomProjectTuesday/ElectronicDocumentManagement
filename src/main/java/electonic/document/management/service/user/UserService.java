package electonic.document.management.service.user;

import electonic.document.management.config.filter.FilterConstant;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.Role;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByIds(Long[] user_ids) {
        return userRepository.findAllById(Arrays.asList(user_ids));
    }

    public boolean setUserRole(Role role, User user) {
        if (user == null) {
            return false;
        }

        user.getRole().add(role);
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(User user, HttpServletResponse response, User currentUser) {
        if (user.equals(currentUser)) {
            userRepository.delete(user);
            Cookie cookie = new Cookie(FilterConstant.COOKIE_NAME, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        }

        return false;
    }

    public void addEmployee(User user, String fullName, String phone) {
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setFullName(fullName);
        employee.setPhone(phone);
        user.setEmployee(employee);

        userRepository.save(user);
    }

    //todo handle null optional
    public User getUser(User user) {
        Optional<User> userFromDb = userRepository.findById(user.getId());
        return userFromDb.get();
    }
}
