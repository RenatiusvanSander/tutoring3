package edu.remad.tutoring3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityOAuth2ResourceServer {

	@Bean
	@Order(1)
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/api/**").csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain unauthroizedFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/**")
				.sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(true))
				.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).build();
	}
}
