package electonic.document.management.repository.projections;

import java.time.LocalDateTime;

public interface DocumentDetailsChooseI {
    public Long getId();

    public String getName();

    public String getFileType();

    public LocalDateTime getCreationDate();

    //public UserDetailesChooseI getOwner();
}
