package electonic.document.management.service.user;

import electonic.document.management.repository.user.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public boolean employeeExistsCheck(Long id) {
        return employeeRepository.findById(id).isPresent();
    }
}
