package electonic.document.management.service.user;

import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.user.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean employeeExistsCheck(Long id) {
        return employeeRepository.findById(id).isPresent();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    public Employee editEmployee(Employee employee, Employee employeeFromDb) {

        BeanUtils.copyProperties(employee, employeeFromDb, "id");

        Employee updatedEmployee = employeeRepository.save(employeeFromDb);

        return updatedEmployee;
    }
}
