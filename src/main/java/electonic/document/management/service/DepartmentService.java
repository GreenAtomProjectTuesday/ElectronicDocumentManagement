package electonic.document.management.service;

import electonic.document.management.model.Department;
import electonic.document.management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// TODO: 18.12.2020 tree movement?
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public boolean addDepartment(Department department) {
        Department departmentFromDb = departmentRepository.getDepartmentByName(department.getName());

        if (departmentFromDb != null) {
            return false;
        }

        // TODO: 18.12.2020 handle null in optional
        Department parentDepartment = departmentRepository.findById(department.getParentId()).get();

        updateTreeKeys(parentDepartment.getRightKey(), 2L);

        department.setLeftKey(parentDepartment.getRightKey());
        department.setRightKey(parentDepartment.getRightKey() + 1);
        department.setLevel(parentDepartment.getLevel() + 1);
        departmentRepository.save(department);

        updateParentBranch(department.getLeftKey(), 2L);
        return true;
    }

    @Transactional
    public void deleteDepartment(Department department) {
        List<Department> departmentsToDelete = departmentRepository
                .findAllByLeftKeyGreaterThanEqualAndRightKeyLessThanEqual(department.getLeftKey(), department.getRightKey());
        long differenceAfterDelete = -(department.getRightKey() - department.getLeftKey() + 1);
        updateTreeKeys(department.getRightKey(), differenceAfterDelete);
        departmentRepository.deleteInBatch(departmentsToDelete);

        updateParentBranch(department.getLeftKey(), differenceAfterDelete);
    }

    private void updateTreeKeys(Long rightKey, Long value) {
        List<Department> departmentsToUpdate = departmentRepository.findAllByLeftKeyGreaterThan(rightKey);
        for (Department department : departmentsToUpdate) {
            department.setLeftKey(department.getLeftKey() + value);
            department.setRightKey(department.getRightKey() + value);
            departmentRepository.updateDepartmentTree(department.getId(), department.getLeftKey(), department.getRightKey());
        }
    }

    private void updateParentBranch(Long leftKeyOfWorkingDepartment, Long value) {
        List<Department> departmentsToUpdate = departmentRepository
                .findAllByLeftKeyLessThanAndRightKeyGreaterThanEqual(leftKeyOfWorkingDepartment, leftKeyOfWorkingDepartment);

        for (Department department : departmentsToUpdate) {
            department.setRightKey(department.getRightKey() + value);
            departmentRepository.updateDepartmentTree(department.getId(), department.getLeftKey(), department.getRightKey());
        }
    }
}
