package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;

import javax.persistence.*;

@Entity
@Table(name = "attribute_values")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String attributeName) {
        this.value = attributeName;
    }
}
