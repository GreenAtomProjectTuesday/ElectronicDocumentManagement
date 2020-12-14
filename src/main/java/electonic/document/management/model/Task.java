package electonic.document.management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "task")
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.FullClass.class)
    private String taskDescription;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullClass.class)
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullClass.class)
    private LocalDateTime expiryDate;

    @JsonView(Views.FullClass.class)
    private boolean readyToReview = false;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonView(Views.FullClass.class)
    private List<TaskEmployee> taskEmployees;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(Views.FullClass.class)
    private List<Document> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
