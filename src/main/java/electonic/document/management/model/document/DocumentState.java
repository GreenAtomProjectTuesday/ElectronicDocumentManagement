package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.User;
import electonic.document.management.model.Views;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_states")
public class DocumentState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @ManyToOne
    @JsonView(Views.FullClass.class)
    private Document document;

    @JsonView(Views.IdName.class)
    private String commitMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.IdName.class)
    private LocalDateTime commitDate;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonView(Views.FullClass.class)
    private User creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }


    public void setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}