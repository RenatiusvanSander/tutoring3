package edu.remad.tutoring3.services.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.KeyCloakUserInfoService;
import edu.remad.tutoring3.services.UserEntityService;
import edu.remad.tutoring3.dto.UserInfo;

/**
 * Service retrieves user's info {@link UserInfo} from Keycloak and persist that
 * user info, when it is not persisted
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
public class KeyCloakUserInfoServiceImpl implements KeyCloakUserInfoService {

	private RestClient restClient;
	private MultiValueMap<String, String> multipleHeaders;
	private UserEntityService userService;

	public KeyCloakUserInfoServiceImpl(RestClient.Builder restClientBuilder, UserEntityService userEntityRepository) {
		this.restClient = restClientBuilder
				.baseUrl("https://keycloak.local:8443/realms/ConnectTrial/protocol/openid-connect").build();
		userService = userEntityRepository;
	}

	@Override
	@EventListener(classes = JwtAuthenticationSuccessEvent.class)
	public void listenJwtAuthenticationTokenEventAndProcess(JwtAuthenticationSuccessEvent event) {
		MultiValueMap<String, String> multiHeaders = getOrCreateMultipleHeaders(event);
		UserInfo userInfo = restClient.get().uri("/userinfo").headers(headers -> headers.addAll(multiHeaders))
				.retrieve().body(UserInfo.class);

		boolean isPersisted = findUserAndPersist(userInfo);
		System.out.println("User ist persisted: " + isPersisted);
	}

	private MultiValueMap<String, String> getOrCreateMultipleHeaders(JwtAuthenticationSuccessEvent event) {
		if (multipleHeaders != null) {
			return multipleHeaders;
		}

		multipleHeaders = new LinkedMultiValueMap<>();
		multipleHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		multipleHeaders.add(HttpHeaders.ACCEPT, "*/*");
		multipleHeaders.add(HttpHeaders.ACCEPT_CHARSET, "utf-8");
		multipleHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + event.getAccessToken());
		multipleHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
		multipleHeaders.add(HttpHeaders.CONNECTION, "keep-alive");

		return multipleHeaders;
	}

	private boolean findUserAndPersist(UserInfo userInfo) {
		if (userService.getUserEntityById(userInfo.getSub()) != null) {
			return true;
		}

		try {
			return userService.saveUserEntity(new UserEntity(userInfo)) != null;
		} catch (Exception e) {
			return false;
		}
	}

}
