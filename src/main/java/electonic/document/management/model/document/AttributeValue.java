package electonic.document.management.model.document;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attribute_values")
@Getter
@Setter
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String value;
}
