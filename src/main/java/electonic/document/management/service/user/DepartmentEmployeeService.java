package electonic.document.management.service.user;

import electonic.document.management.model.Department;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.Position;
import electonic.document.management.repository.user.DepartmentEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentEmployeeService {
    private final DepartmentEmployeeRepository departmentEmployeeRepository;

    @Autowired
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

    public List<DepartmentEmployee> getAllDepartmentEmployees() {
        return departmentEmployeeRepository.findAll();
    }

    public void deleteDepartmentEmployee(DepartmentEmployee employee) {
        departmentEmployeeRepository.delete(employee);
    }

    public boolean deleteDepartmentEmployeePosition(DepartmentEmployee employee, Position position) {

        if (employee == null) {
            return false;
        }

        Set<Position> employeePositions = employee.getPosition();

        employeePositions.remove(position);

        departmentEmployeeRepository.save(employee);
        return true;
    }

    public void setDepartmentEmployeePosition(DepartmentEmployee employee, Position position) {

        if (employee == null) {
            return;
        }

        employee.getPosition().add(position);

        departmentEmployeeRepository.save(employee);
    }
}
