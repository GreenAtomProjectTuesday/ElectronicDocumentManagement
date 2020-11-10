package electonic.document.management.model;

public class Document {
    private Long id;
    private User owner;
    private Task task;
    private String fileName;
    private String fileType;
    private DocumentAttribute attribute;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

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

    public DocumentAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(DocumentAttribute attribute) {
        this.attribute = attribute;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
