package electonic.document.management.model.user;

import com.fasterxml.jackson.annotation.JsonView;
import electonic.document.management.model.Views;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String fullName;

    @JsonView(Views.FullProfile.class)
    private String telephoneNumber;
}
