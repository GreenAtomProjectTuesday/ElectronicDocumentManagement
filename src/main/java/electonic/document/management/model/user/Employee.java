package electonic.document.management.model.user;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "user_id")
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String fullName;

    @JsonView(Views.FullProfile.class)
    private String phone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
