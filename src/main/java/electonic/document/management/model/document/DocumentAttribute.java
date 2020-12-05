package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;

import javax.persistence.*;

@Entity
@Table(name = "document_attributes")
public class DocumentAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonView(Views.FullClass.class)
    private Document document;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    @JsonView(Views.FullClass.class)
    private AttributeValue attributeValue;

    @JsonView(Views.IdName.class)
    private String attributeName;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String value) {
        this.attributeName = value;
    }
}
