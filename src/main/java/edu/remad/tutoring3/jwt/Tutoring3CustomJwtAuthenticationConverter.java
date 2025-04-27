package edu.remad.tutoring3.jwt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class Tutoring3CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

	/**
	 * Default Constructor
	 */
	public Tutoring3CustomJwtAuthenticationConverter() {
		jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		System.out.println("######################## created Tutoring3CustomJwtAuthenticationConverter");
	}

	@Override
	public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
		System.out.println("##### do convert JWT 1");
		
		Map<String,Object>[] claims = new HashMap[] {(HashMap) jwt.getClaims()};
		for(Map<String,Object> claim : claims) {
			for(Map.Entry<String, Object> claimEntry : claim.entrySet()) {
				if(claimEntry.getValue() instanceof String) {
					System.out.println("[JwtConverter]: " + claimEntry.getKey() + ": " + claimEntry.getValue());
				}
			}
		}
		
		Collection<GrantedAuthority> authorities = Stream
				.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractJwtResourceRoles(jwt).stream())
				.collect(Collectors.toSet());

		return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
	}

	private Collection<? extends GrantedAuthority> extractJwtResourceRoles(Jwt jwt) {
		System.out.println("##### do convert JWT 2");
		if (jwt.getClaimAsMap("resource_access") == null) {
			return Set.of();
		}

		Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
		if (resourceAccess.get("tutoring2-resource-server") == null) {
			return Set.of();
		}

		if (resourceAccess.get("tutoring2-resource-server") == null) {
			return Set.of();
		}

		Map<String, Object> resource = (Map<String, Object>) resourceAccess.get("tutoring2-resource-server");
		Collection<String> resourceRoles = (Collection<String>) resource.get("roles");

		return resourceRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toSet());
	}

	private String getPrincipalClaimName(Jwt jwt) {
		System.out.println("##### do convert JWT 3");
		String claimName = JwtClaimNames.SUB;
		
		if ("preferred_username" != null) {
			claimName = "preferred_username";
		}

		return jwt.getClaim(claimName);
	}
}
