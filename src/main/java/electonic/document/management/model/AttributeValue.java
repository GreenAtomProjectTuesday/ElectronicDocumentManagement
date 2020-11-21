package electonic.document.management.model;

import javax.persistence.*;

@Entity
@Table(name = "attribute_values")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String attributeName) {
        this.value = attributeName;
    }
}
