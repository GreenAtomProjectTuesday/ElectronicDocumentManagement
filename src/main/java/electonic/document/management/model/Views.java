package electonic.document.management.model;

public class Views {

    public interface Id {
    }

    public interface IdName extends Id {
    }

    public interface IdNameRoles extends Id {
    }

    public interface FullProfile extends IdNameRoles {
    }

    public interface DocumentParameters extends IdName {
    }

    public interface FullDocument extends DocumentParameters {
    }

    public interface FullTask extends IdName {
    }
}
