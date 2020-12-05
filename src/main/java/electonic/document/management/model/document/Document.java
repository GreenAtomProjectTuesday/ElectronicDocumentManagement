package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import electonic.document.management.model.Task;
import electonic.document.management.model.User;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "documents")
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String fileName;

    @JsonView(Views.IdName.class)
    private String fileType;

    @JsonView(Views.DocumentParameters.class)
    private Long size;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.DocumentParameters.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonView(Views.DocumentParameters.class)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.DocumentParameters.class)
    private User owner;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    @OrderColumn
    @JsonView(Views.DocumentParameters.class)
    private List<DocumentState> documentStates;

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    @JsonView(Views.DocumentParameters.class)
    private List<DocumentAttribute> attribute;

    @JsonView(Views.FullDocument.class)
    private byte[] content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
