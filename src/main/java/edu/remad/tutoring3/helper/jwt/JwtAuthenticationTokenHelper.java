package edu.remad.tutoring3.helper.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtAuthenticationTokenHelper extends JwtAuthenticationToken {

	/** generated serial version UID */
	private static final long serialVersionUID = -7852449385736553021L;

	private String[] scopes;
	
	/**
	 * Constructs a {@code JwtAuthenticationTokenHelper} using the provided
	 * parameters.
	 * 
	 * @param jwt the JWT
	 */
	public JwtAuthenticationTokenHelper(Jwt jwt) {
		super(jwt);
	}

	/**
	 * Constructs a {@code JwtAuthenticationTokenHelper} using the provided
	 * parameters.
	 * 
	 * @param jwt         the JWT
	 * @param authorities the authorities assigned to the JWT
	 */
	public JwtAuthenticationTokenHelper(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
		super(jwt, authorities);
		this.setAuthenticated(true);
	}

	/**
	 * Constructs a {@code JwtAuthenticationTokenHelper} using the provided
	 * parameters.
	 * 
	 * @param jwt         the JWT
	 * @param authorities the authorities assigned to the JWT
	 * @param name        the principal name
	 */
	public JwtAuthenticationTokenHelper(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String name) {
		super(jwt, authorities);
		this.setAuthenticated(true);
	}

	public String getSub() {
		return getToken().getSubject();
	}

	public boolean isEmailVerified() {
		return getToken().getClaimAsBoolean("email_verified");
	}

	public String getPreferredUsername() {
		return getToken().getClaimAsString("preferred_username");
	}

	public String getGiven_name() {
		return getToken().getClaimAsString("given_name");
	}

	public String getFamily_name() {
		return getToken().getClaimAsString("family_name");
	}

	public String getEmail() {
		return getToken().getClaimAsString("email");
	}

	public String getFullName() {
		return getToken().getClaimAsString("name");
	}

	public String[] getScope() {
		if (scopes != null) {
			int scopeLength = scopes.length;

			return copyArray(new String[scopeLength], scopeLength);
		}

		scopes = getToken().getClaimAsString("scope").split(" ");
		int scopeLength = scopes.length;

		return copyArray(new String[scopeLength], scopeLength);
	}

	private String[] copyArray(String[] defensiveScopeCopy, int scopeLength) {
		System.arraycopy(scopes, 0, defensiveScopeCopy, 0, scopeLength);

		return defensiveScopeCopy;
	}
	
	public List<String> getTutoring3Roles() {
		List<String> tutoring3Roles = getToken().getClaimAsStringList("tutoring2-resource-server");
		
		return tutoring3Roles;
	}
	
	public List<String> getRealmAccessRoles() {
		List<String> realmAccessRoles = getToken().getClaimAsStringList("realm_access");
		
		return realmAccessRoles;
	}
	
	public List<String> getResourceAccessRoles() {
		List<String> resourceAccessRoles = getToken().getClaimAsStringList("resource_access");
		
		return resourceAccessRoles;
	}

}
