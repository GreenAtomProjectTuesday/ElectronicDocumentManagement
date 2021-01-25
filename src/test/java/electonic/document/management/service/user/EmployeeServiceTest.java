package electonic.document.management.service.user;

import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.user.EmployeeRepository;
import electonic.document.management.repository.user.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void employeeExistsCheck() {

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
        employee.setFullName("William Henry Gates III");

        Employee employeeFromDb = new Employee();

        employeeService.editEmployee(employee, employeeFromDb);

        Assert.assertEquals(employeeFromDb.getFullName(), employeeFromDb.getFullName());
    }
}