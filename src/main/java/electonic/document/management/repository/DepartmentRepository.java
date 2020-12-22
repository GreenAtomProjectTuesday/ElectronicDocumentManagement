package electonic.document.management.repository;

import electonic.document.management.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department getDepartmentByName(String departmentName);

    List<Department> findAllByLeftKeyGreaterThan(Long rightKey);

    List<Department> findAllByLeftKeyGreaterThanEqualAndRightKeyLessThanEqual(Long fromKey, Long toKey);

    List<Department> findAllByLeftKeyLessThanAndRightKeyGreaterThanEqual(Long leftLessThan, Long rightGreaterThan);

    List<Department> findAllByLeftKeyLessThanAndLeftKeyGreaterThan(Long lessThen, Long greaterThan);

    List<Department> findAllByRightKeyGreaterThanAndRightKeyLessThan(Long greaterThan, Long lessThen);

    @Modifying
    @Query("UPDATE Department d SET d.leftKey = :leftKey, d.rightKey = :rightKey where d.id = :departmentId")
    void updateDepartmentTree(@Param("departmentId") Long departmentId, @Param("leftKey") Long leftKey, @Param("rightKey") Long rightKey);

    @Modifying
    @Query("UPDATE Department d SET d.parentId = :newParentId where d.id = :departmentId")
    void updateDepartmentParent(@Param("departmentId") Long departmentId, @Param("newParentId") Long newParentId);
}
