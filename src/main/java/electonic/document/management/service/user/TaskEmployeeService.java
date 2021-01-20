package electonic.document.management.service.user;

import electonic.document.management.model.Task;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.repository.user.TaskEmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void deleteByEmployeeId(Long id) {
        taskEmployeeRepository.deleteByEmployee_Id(id);
    }
}
