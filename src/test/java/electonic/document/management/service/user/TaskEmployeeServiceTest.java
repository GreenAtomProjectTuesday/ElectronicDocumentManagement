package electonic.document.management.service.user;

import electonic.document.management.model.Task;
import electonic.document.management.model.user.Duty;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.repository.user.TaskEmployeeRepository;
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
class TaskEmployeeServiceTest {
    @Autowired
    TaskEmployeeService taskEmployeeService;

    @MockBean
    TaskEmployeeRepository taskEmployeeRepository;

    @Test
    void addTaskEmployee() {
        Task task = new Task();

        Employee employee = new Employee();

        boolean addTaskEmployee = taskEmployeeService.addTaskEmployee(task, employee);

        Assert.assertTrue(addTaskEmployee);
    }

    @Test
    void getAllTaskEmployees() {
        List<TaskEmployee> allTaskEmployees = taskEmployeeService.getAllTaskEmployees();

        Assert.assertNotNull(allTaskEmployees);
    }

    @Test
    void deleteTaskEmployee() {
        TaskEmployee taskEmployee = new TaskEmployee();

        taskEmployeeService.deleteTaskEmployee(taskEmployee);
    }

    @Test
    void setTaskEmployeePosition() {
        Duty duty = Duty.CURATOR;
        TaskEmployee taskEmployee = new TaskEmployee();

        boolean taskEmployeePosition =
                taskEmployeeService.setTaskEmployeePosition(taskEmployee, duty);

        Assert.assertTrue(taskEmployeePosition);
    }

    @Test
    void deleteTaskEmployeePosition() {
        TaskEmployee taskEmployee = new TaskEmployee();

        taskEmployeeService.addTaskEmployee(taskEmployee.getTask(), taskEmployee.getEmployee());

        boolean deleteTaskEmployeePosition =
                taskEmployeeService.deleteTaskEmployeePosition(taskEmployee, Duty.CURATOR);

        Assert.assertTrue(deleteTaskEmployeePosition);

    }
}