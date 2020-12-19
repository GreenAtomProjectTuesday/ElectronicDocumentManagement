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

    @MockBean
    TaskEmployee taskEmployee;

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

        taskEmployeeService.deleteTaskEmployee(taskEmployee);
    }

    @Test
    void setTaskEmployeePosition() {
        Duty duty = Duty.CURATOR;

        boolean taskEmployeePosition =
                taskEmployeeService.setTaskEmployeePosition(taskEmployee, duty);

        Assert.assertTrue(taskEmployeePosition);
    }

    @Test
    void deleteTaskEmployeePosition() {
        Duty duty = Duty.CURATOR;

        taskEmployee.setEmployee(new Employee());
        taskEmployee.setTask(new Task());

        boolean deleteTaskEmployeePosition =
                taskEmployeeService.deleteTaskEmployeePosition(taskEmployee, duty);

        Assert.assertTrue(deleteTaskEmployeePosition);

    }
}