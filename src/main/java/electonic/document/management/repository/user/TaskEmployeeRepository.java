package electonic.document.management.repository.user;

import electonic.document.management.model.Task;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskEmployeeRepository extends JpaRepository<TaskEmployee, Long> {
    void deleteByEmployee_Id(Long employee_id);

    TaskEmployee findByTaskAndEmployee(Task task, Employee employee);
}
