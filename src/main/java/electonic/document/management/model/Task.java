package electonic.document.management.model;

import java.util.List;

public class Task {
    private Long id;
    private User creator;
    private List<User> curators;
    private List<User> performers;
    private List<Document> documents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getCurators() {
        return curators;
    }

    public void setCurators(List<User> curators) {
        this.curators = curators;
    }

    public List<User> getPerformers() {
        return performers;
    }

    public void setPerformers(List<User> performers) {
        this.performers = performers;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
