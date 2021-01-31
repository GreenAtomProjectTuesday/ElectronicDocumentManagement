package electonic.document.management.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Task;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task_employee")
@Getter
@Setter
public class TaskEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @ElementCollection(targetClass = Duty.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "task_employee_duty", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(Views.IdNameRoles.class)
    private Set<Duty> duty;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime assignmentDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonView(Views.FullProfile.class)
    private Employee employee;

    @ManyToOne
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaskEmployee that = (TaskEmployee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "TaskEmployee{" +
                "id=" + id +
                '}';
    }
}
