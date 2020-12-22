package electonic.document.management.utils;

import electonic.document.management.model.Department;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentUtils {

    public static void printTreeToSystemOut(List<Department> departments) {
        StringBuilder buffer = new StringBuilder(50);
        Department rootDepartment = departments.stream()
                .filter(department -> department.getId().equals(1L)).findFirst().get();
        printTree(buffer, "", "", departments, rootDepartment);
        System.out.println(buffer.toString());
    }

    private static void printTree(StringBuilder buffer, String prefix, String childrenPrefix,
                                  List<Department> departments, Department department) {
        buffer.append(prefix);
        buffer.append(department.getName()).append(" ").append(department.getLeftKey()).append(" ").append(department.getRightKey());
        buffer.append('\n');

        List<Department> children = departments.stream()
                .filter(dep -> dep.getParentId().equals(department.getId()) && !dep.getId().equals(1L))
                .collect(Collectors.toList());

        for (Iterator<Department> it = children.iterator(); it.hasNext(); ) {
            Department next = it.next();
            if (it.hasNext()) {
                printTree(buffer, childrenPrefix + "|------", childrenPrefix + "|      ", departments, next);
            } else {
                printTree(buffer, childrenPrefix + "`------", childrenPrefix + "       ", departments, next);
            }
        }
    }
}
