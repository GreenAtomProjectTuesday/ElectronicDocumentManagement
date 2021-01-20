package electonic.document.management.repository.user;

import electonic.document.management.model.Department;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, Long> {
    void deleteByEmployee_Id(Long employee_id);

    DepartmentEmployee findByDepartmentAndEmployee(Department department, Employee employee);
}
