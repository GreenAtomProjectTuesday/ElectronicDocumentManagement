package electonic.document.management.service.user;

import electonic.document.management.model.Task;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Duty;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.repository.user.TaskEmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskEmployeeService {

    private final TaskEmployeeRepository taskEmployeeRepository;

    public TaskEmployeeService(TaskEmployeeRepository taskEmployeeRepository) {
        this.taskEmployeeRepository = taskEmployeeRepository;
    }

    public boolean addTaskEmployee(Task task, Employee employee) {
        TaskEmployee taskEmployeeFromDb =
                taskEmployeeRepository.findByTaskAndEmployee(task, employee);
        if (taskEmployeeFromDb != null) {
            return false;
        }
        TaskEmployee taskEmployee = new TaskEmployee();
        taskEmployee.setTask(task);
        taskEmployee.setEmployee(employee);
        taskEmployee.setAssignmentDate(LocalDateTime.now());

        taskEmployeeRepository.save(taskEmployee);
        return true;
    }

    public List<TaskEmployee> getAllTaskEmployees() {
        return taskEmployeeRepository.findAll();
    }

    public void deleteTaskEmployee(TaskEmployee employee) {
        taskEmployeeRepository.delete(employee);
    }

    public boolean setTaskEmployeePosition(TaskEmployee employee, Duty duty) {
        if (employee == null) {
            return false;
        }

        employee.getDuty().add(duty);

        taskEmployeeRepository.save(employee);

        return true;
    }

    public boolean deleteTaskEmployeePosition(TaskEmployee employee, Duty duty) {
        TaskEmployee taskEmployeeFromDb =
                taskEmployeeRepository.findByTaskAndEmployee(employee.getTask(), employee.getEmployee());

        if (taskEmployeeFromDb == null) {
            return false;
        }

        taskEmployeeFromDb.getDuty().remove(duty);

        return true;
    }
}
