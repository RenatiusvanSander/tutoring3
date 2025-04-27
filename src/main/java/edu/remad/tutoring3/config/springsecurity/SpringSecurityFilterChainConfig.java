package edu.remad.tutoring3.config.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import edu.remad.tutoring3.jwt.Tutoring3CustomJwtAuthenticationConverter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityFilterChainConfig {

	/**
	 * 
	 * @param http similar to spring security xml config for filtering request
	 * @return created security filter chain, {@link SecurityFilterChain}
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain oauth2rescourceserverSecurityFilterChain(HttpSecurity http,
			Tutoring3CustomJwtAuthenticationConverter wtAuthenticationConverter) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/").permitAll()
						.requestMatchers(HttpMethod.GET, "/v2/demo").permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
						.jwt(jwt -> jwt.jwtAuthenticationConverter(wtAuthenticationConverter)))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
