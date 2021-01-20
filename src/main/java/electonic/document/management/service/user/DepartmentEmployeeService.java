package electonic.document.management.service.user;

import electonic.document.management.model.Department;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import electonic.document.management.repository.user.DepartmentEmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepartmentEmployeeService {
    private final DepartmentEmployeeRepository departmentEmployeeRepository;

    public DepartmentEmployeeService(DepartmentEmployeeRepository departmentEmployeeRepository) {
        this.departmentEmployeeRepository = departmentEmployeeRepository;
    }

    public boolean addDepartmentEmployee(Department department, Employee employee) {
        DepartmentEmployee departmentEmployeeFromDb =
                departmentEmployeeRepository.findByDepartmentAndEmployee(department, employee);
        if (departmentEmployeeFromDb != null) {
            return false;
        }
        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        departmentEmployee.setDepartment(department);
        departmentEmployee.setEmployee(employee);
        departmentEmployee.setDesignationDate(LocalDateTime.now());

        departmentEmployeeRepository.save(departmentEmployee);
        return true;
    }

    public void deleteByEmployeeId(Long id) {
        departmentEmployeeRepository.deleteByEmployee_Id(id);
    }
}
