package electonic.document.management.model.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.user.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
public  class Report {
    private Long id;//id of task

    private String name; //name of task

    private String taskDescription; //name of task

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;

    private boolean readyToReview;

    private List<Employee> Employees;

    private List<Document> documents;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", taskName='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
