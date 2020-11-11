package electonic.document.management.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // TODO FetchType Lazy?
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    //    private Task task;
    private String fileName;
    private String fileType;
    private Long size;

    @OneToMany(mappedBy = "document")
    private List<DocumentAttribute> attribute;
    private byte[] content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

//    public Task getTask() {
//        return task;
//    }
//
//    public void setTask(Task task) {
//        this.task = task;
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<DocumentAttribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<DocumentAttribute> attribute) {
        this.attribute = attribute;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
