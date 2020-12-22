package electonic.document.management.service;

import electonic.document.management.model.Department;
import electonic.document.management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
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

    // TODO: 19.12.2020 prohibit movement to heir
    @Transactional
    public void moveDepartmentInHierarchy(Department department, Long newParentId) {
        // TODO: 19.12.2020 handle null in optional
        Department newParentDepartment = departmentRepository.findById(newParentId).get();
        Department oldParentDepartment = departmentRepository.findById(department.getParentId()).get();
        List<Department> departmentsToMove = departmentRepository
                .findAllByLeftKeyGreaterThanEqualAndRightKeyLessThanEqual(department.getLeftKey(), department.getRightKey());

        if (newParentDepartment.getLeftKey() < oldParentDepartment.getLeftKey()) {
            moveDepartmentUp(department, newParentDepartment, departmentsToMove);
        } else {
            moveDepartmentDown(department, newParentDepartment, departmentsToMove);
        }
    }

    private void moveDepartmentUp(Department department, Department newParentDepartment, List<Department> departmentsToMove) {
        long changeInKeys = department.getRightKey() - department.getLeftKey() + 1;
        updateTreeKeysForMovement(department.getLeftKey(), newParentDepartment.getRightKey(), changeInKeys);

        Long changeInKeysForMovingDepartments = -(department.getLeftKey() - newParentDepartment.getRightKey());
        for (Department dep : departmentsToMove) {
            departmentRepository.updateDepartmentTree(dep.getId(), dep.getLeftKey() + changeInKeysForMovingDepartments,
                    dep.getRightKey() + changeInKeysForMovingDepartments);
        }
        departmentRepository.updateDepartmentParent(department.getId(), newParentDepartment.getId());
        departmentRepository.updateDepartmentTree(newParentDepartment.getId(), newParentDepartment.getLeftKey(),
                newParentDepartment.getRightKey() + department.getRightKey() - department.getLeftKey() + 1);
    }

    private void moveDepartmentDown(Department department, Department newParentDepartment, List<Department> departmentsToMove) {
        long changeInKeys = department.getRightKey() - department.getLeftKey() + 1;
        updateTreeKeysForMovement(newParentDepartment.getLeftKey(), department.getRightKey(), -changeInKeys);

        Long changeInKeysForMovingDepartments = -(department.getRightKey() - newParentDepartment.getLeftKey() + 1);

        for (Department dep : departmentsToMove) {
            departmentRepository.updateDepartmentTree(dep.getId(), dep.getLeftKey() + changeInKeysForMovingDepartments,
                    dep.getRightKey() + changeInKeysForMovingDepartments);
        }

        departmentRepository.updateDepartmentParent(department.getId(), newParentDepartment.getId());
        departmentRepository.updateDepartmentTree(newParentDepartment.getId(),
                newParentDepartment.getLeftKey() - department.getRightKey() + department.getLeftKey() - 1,
                newParentDepartment.getRightKey());
    }

    private void updateTreeKeysForMovement(Long firstKey, Long secondKey, Long changeInKeys) {
        List<Department> changeLeftValues = departmentRepository
                .findAllByLeftKeyLessThanAndLeftKeyGreaterThan(firstKey, secondKey);
        List<Department> changeRightValues = departmentRepository
                .findAllByRightKeyGreaterThanAndRightKeyLessThan(secondKey, firstKey);

        Set<Department> departmentSet = Stream.concat(changeLeftValues.stream(), changeRightValues.stream())
                .collect(Collectors.toSet());
        for (Department department : departmentSet) {
            Long containsInLeft = changeLeftValues.contains(department) ? 1L : 0L;
            Long containsInRight = changeRightValues.contains(department) ? 1L : 0L;
            departmentRepository.updateDepartmentTree(department.getId(),
                    department.getLeftKey() + containsInLeft * changeInKeys,
                    department.getRightKey() + containsInRight * changeInKeys);
        }

    }

    private void updateTreeKeys(Long rightKey, Long value) {
        List<Department> departmentsToUpdate = departmentRepository.findAllByLeftKeyGreaterThan(rightKey);
        for (Department department : departmentsToUpdate) {
            departmentRepository.updateDepartmentTree(department.getId(),
                    department.getLeftKey() + value, department.getRightKey() + value);
        }
    }

    private void updateParentBranch(Long leftKeyOfWorkingDepartment, Long value) {
        List<Department> departmentsToUpdate = departmentRepository
                .findAllByLeftKeyLessThanAndRightKeyGreaterThanEqual(leftKeyOfWorkingDepartment, leftKeyOfWorkingDepartment);

        for (Department department : departmentsToUpdate) {
            departmentRepository.updateDepartmentTree(department.getId(), department.getLeftKey(), department.getRightKey() + value);
        }
    }
}
