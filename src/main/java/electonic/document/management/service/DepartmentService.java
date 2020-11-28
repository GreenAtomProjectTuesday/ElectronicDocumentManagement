package electonic.document.management.service;

import electonic.document.management.model.Department;
import electonic.document.management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public boolean addDepartment(Department department) {
        Department departmentFromDb = departmentRepository.getDepartmentByDepartmentName(department.getDepartmentName());

        if (departmentFromDb != null) {
            return false;
        }

        departmentRepository.save(department);

        return true;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void deleteDepartment(Department department_id) {
        departmentRepository.delete(department_id);
    }
}
