package configuration;

import dto.UserDTO;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by steven on 05/10/16.
 */
public class UserObjectAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 400L;
    private final Object principal;
    private Object credentials;
    private UserDTO userObject;

    public UserObjectAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public UserObjectAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UserDTO userObject) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
        this.userObject = userObject;
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    public UserDTO getUserObject() {
        return userObject;
    }
}