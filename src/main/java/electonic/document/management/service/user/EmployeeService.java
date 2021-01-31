package electonic.document.management.service.user;

import electonic.document.management.model.RequestParametersException;
import electonic.document.management.model.user.Employee;
import electonic.document.management.repository.user.EmployeeRepository;
import org.springframework.stereotype.Service;

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

    public Employee getEmployeeByUserId(Long id) throws RequestParametersException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new RequestParametersException("This user is not an Employee!");
        }
        return optionalEmployee.get();
    }
}
