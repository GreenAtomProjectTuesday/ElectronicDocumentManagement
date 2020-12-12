package electonic.document.management.repository;

import electonic.document.management.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department getDepartmentByName(String departmentName);
}
