package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document_attributes")
@Getter
@Setter
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
}
