package electonic.document.management.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, LEAD;

    @Override
    public String getAuthority() {
        return name();
    }
}
