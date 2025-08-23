package edu.remad.tutoring3.helper.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

public class JwtAuthenticationTokenHelper extends JwtAuthenticationToken {

	/** generated serial version UID */
	private static final long serialVersionUID = -7852449385736553021L;

	private String[] scopes;

	private List<String> realmAccessRoles;

	private List<String> tutoring3Roles;

	private List<String> accountRoles;

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
		return getToken().getClaimAsBoolean("email_verified").booleanValue();
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
		String[] defensiveScopeCopies = copyArray(new String[scopeLength], scopeLength);

		return defensiveScopeCopies;
	}

	private String[] copyArray(String[] defensiveScopeCopy, int scopeLength) {
		System.arraycopy(scopes, 0, defensiveScopeCopy, 0, scopeLength);

		return defensiveScopeCopy;
	}

	public List<String> getTutoring3Roles() {
		if (tutoring3Roles != null) {
			return new ArrayList<>(tutoring3Roles);
		}

		Map<String, Object> resourceAccess = getToken().getClaimAsMap("resource_access");
		Map<String, Object> tutoring3ResourceServer = (Map<String, Object>) resourceAccess.get("tutoring2-resource-server");
		List<String> roles2 = (List<String>) tutoring3ResourceServer.get("roles");

		tutoring3Roles = roles2.stream().map(role -> "ROLE_" + role).toList();

		return new ArrayList<String>(tutoring3Roles);
	}

	public List<String> getRealmAccessRoles() {
		if (realmAccessRoles != null) {
			return new ArrayList<String>(realmAccessRoles);
		}

		Map<String, Object> realmAccess = getToken().getClaimAsMap("realm_access");
		List<String> roles = (List<String>) realmAccess.get("roles");
		realmAccessRoles = roles.stream().map(role -> "ROLE_" + role).toList();

		return new ArrayList<String>(realmAccessRoles);
	}

	public List<String> getAccountRoles() {
		if (accountRoles != null) {
			return new ArrayList<>(accountRoles);
		}

		Map<String, Object> resourceAccess = getToken().getClaimAsMap("resource_access");
		Map<String, Object> account = (LinkedTreeMap<String, Object>) resourceAccess.get("account");
		List<String> accountRoles = (List<String>) account.get("roles");
		accountRoles = accountRoles.stream().map(role -> "ROLE_" + role).toList();

		return new ArrayList<>(accountRoles);
	}

}
