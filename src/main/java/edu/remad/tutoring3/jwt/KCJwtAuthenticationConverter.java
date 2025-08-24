package edu.remad.tutoring3.jwt;

import java.util.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import edu.remad.tutoring3.helper.jwt.JwtAuthenticationTokenHelper;

@Component
public class KCJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

	private String principalClaimName = "resource_access";

	@Override
	public final AbstractAuthenticationToken convert(Jwt jwt) {
		JwtAuthenticationTokenHelper jwtHelper = new JwtAuthenticationTokenHelper(jwt);
		String principalClaimValue = jwt.getClaimAsString(principalClaimName);
		Collection<GrantedAuthority> authorities = jwtHelper.getTutoring3Roles();

		JwtAuthenticationTokenHelper jwtAuthenticationToken = new JwtAuthenticationTokenHelper(jwt, authorities,
				principalClaimValue);

		return jwtAuthenticationToken;
	}

	/**
	 * Sets the {@link Converter Converter&lt;Jwt,
	 * Collection&lt;GrantedAuthority&gt;&gt;} to use. Defaults to
	 * {@link JwtGrantedAuthoritiesConverter}.
	 * 
	 * @param jwtGrantedAuthoritiesConverter The converter
	 * @since 5.2
	 * @see JwtGrantedAuthoritiesConverter
	 */
	public void setJwtGrantedAuthoritiesConverter(
			Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
		Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
		this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
	}

	/**
	 * Sets the principal claim name. Defaults to {@link JwtClaimNames#SUB}.
	 * 
	 * @param principalClaimName The principal claim name
	 * @since 5.4
	 */
	public void setPrincipalClaimName(String principalClaimName) {
		Assert.hasText(principalClaimName, "principalClaimName cannot be empty");
		this.principalClaimName = principalClaimName;
	}
}
