package electonic.document.management.model;

import com.fasterxml.jackson.annotation.*;
import electonic.document.management.model.user.DepartmentEmployee;
import electonic.document.management.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private List<DepartmentEmployee> departmentStaff;

    @JsonView(Views.FullClass.class)
    private Long level;

    @Column(nullable = false)
    @JsonView(Views.FullClass.class)
    private Long parentId;

    @JsonView(Views.FullClass.class)
    private Long leftKey;

    @JsonView(Views.FullClass.class)
    private Long rightKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + name + '\'' +
                ", departmentStaff=" + departmentStaff +
                '}';
    }
}


