package electonic.document.management.service.user;

import electonic.document.management.model.Department;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.Position;
import electonic.document.management.repository.user.DepartmentEmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class DepartmentEmployeeServiceTest {
    @Autowired
    private DepartmentEmployeeService departmentEmployeeService;

    @MockBean
    private DepartmentEmployeeRepository departmentEmployeeRepository;

    @Test
    void addDepartmentEmployee() {
        Department department = new Department();

        Employee employee = new Employee();

        boolean isDepartmentEmployee = departmentEmployeeService.addDepartmentEmployee(department, employee);

        Assert.assertTrue(isDepartmentEmployee);
    }

    @Test
    void getAllDepartmentEmployees() {
        List<DepartmentEmployee> allDepartmentEmployees = departmentEmployeeService.getAllDepartmentEmployees();

        Assert.assertNotNull(allDepartmentEmployees);
    }

    @Test
    void deleteDepartmentEmployee() {
        Employee employee = new Employee();
        Department department = new Department();

        DepartmentEmployee byDepartmentAndEmployee =
                departmentEmployeeRepository.findByDepartmentAndEmployee(department, employee);

        departmentEmployeeService.deleteDepartmentEmployee(byDepartmentAndEmployee);
    }

    @Test
    void deleteDepartmentEmployeePosition() {
        Position position = Position.JUNIOR;

        Set<Position> positionSet = new HashSet<>();
        positionSet.add(position);
        
        DepartmentEmployee employee = new DepartmentEmployee();
        employee.setPosition(positionSet);

        boolean isDeleteDepartmentEmployeePosition =
                departmentEmployeeService.deleteDepartmentEmployeePosition(employee, position);

        Assert.assertTrue(isDeleteDepartmentEmployeePosition);
    }

    @Test
    void setDepartmentEmployeePosition() {
        Position position = Position.JUNIOR;

        DepartmentEmployee departmentEmployee = new DepartmentEmployee();

        departmentEmployeeService.setDepartmentEmployeePosition(departmentEmployee, position);
    }
}