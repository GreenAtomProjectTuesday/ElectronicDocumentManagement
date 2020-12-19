package electonic.document.management.service.user;

import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.user.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    UserService userService;

    @MockBean
    User user;

    @Test
    void employeeExistsCheck() {
        userService.addEmployee(user, "testName", "testNumber");

        boolean isEmployeeExists = employeeService.employeeExistsCheck(user.getId());

        Assert.assertTrue(isEmployeeExists);
    }

    @Test
    void getAllEmployees() {
        List<Employee> allEmployee = employeeRepository.findAll();

        Assert.assertNotNull(allEmployee);
    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee();

        employeeService.deleteEmployee(employee);

    }

    @Test
    void editEmployee() {
        Employee employee = new Employee();

        Employee employeeFromDb = new Employee();

        Employee updateEmployee = employeeService.editEmployee(employee, employeeFromDb);

        Assert.assertNotNull(updateEmployee);
    }
}