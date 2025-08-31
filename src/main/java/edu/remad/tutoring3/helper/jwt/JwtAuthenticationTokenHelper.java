package edu.remad.tutoring3.helper.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

/**
 * JSON Authentication Token Helper supports to get all roles and user claims.
 * 
 * @author edu.remad
 * @since 2025
 */
public class JwtAuthenticationTokenHelper extends JwtAuthenticationToken {

	/** generated serial version UID */
	private static final long serialVersionUID = -7852449385736553021L;

	private List<GrantedAuthority> scopes;

	private List<GrantedAuthority> realmAccessRoles;

	private List<GrantedAuthority> tutoring3Roles;

	private List<GrantedAuthority> accountRoles;

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

	/**
	 * Gets scope
	 * 
	 * @return list of scope roles
	 */
	public List<GrantedAuthority> getScope() {
		if (scopes != null) {
			return new ArrayList<>(scopes);
		}

		String[] claimScopes = getToken().getClaimAsString("scope").split(" ");
		scopes = Arrays.asList(claimScopes).stream().map(scope -> new SimpleGrantedAuthority("ROLE_" + scope))
				.map(GrantedAuthority.class::cast).toList();

		return new ArrayList<>(scopes);
	}

	/**
	 * Gets list of tutoring 3 roles
	 * 
	 * @return list of tutoring 3 roles
	 */
	public List<GrantedAuthority> getTutoring3Roles() {
		if (tutoring3Roles != null) {
			return new ArrayList<>(tutoring3Roles);
		}

		Map<String, Object> resourceAccess = getToken().getClaimAsMap("resource_access");
		Map<String, Object> tutoring3ResourceServer = (Map<String, Object>) resourceAccess
				.get("tutoring2-resource-server");
		List<String> listRoles = (List<String>) tutoring3ResourceServer.get("roles");

		tutoring3Roles = listRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.map(GrantedAuthority.class::cast).toList();

		return new ArrayList<>(tutoring3Roles);
	}

	/**
	 * Gets Realm Access roles
	 * 
	 * @return list of realm access roles
	 */
	public List<GrantedAuthority> getRealmAccessRoles() {
		if (realmAccessRoles != null) {
			return new ArrayList<>(realmAccessRoles);
		}

		Map<String, Object> realmAccess = getToken().getClaimAsMap("realm_access");
		List<String> roles = (List<String>) realmAccess.get("roles");
		realmAccessRoles = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.map(GrantedAuthority.class::cast).toList();

		return new ArrayList<>(realmAccessRoles);
	}

	/**
	 * Gets Account roles
	 * 
	 * @return list of account roles
	 */
	public List<GrantedAuthority> getAccountRoles() {
		if (accountRoles != null) {
			return new ArrayList<>(accountRoles);
		}

		Map<String, Object> resourceAccess = getToken().getClaimAsMap("resource_access");
		Map<String, Object> account = (LinkedTreeMap<String, Object>) resourceAccess.get("account");
		List<String> textAccountRoles = (List<String>) account.get("roles");
		accountRoles = textAccountRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.map(GrantedAuthority.class::cast).toList();

		return new ArrayList<>(accountRoles);
	}

}
