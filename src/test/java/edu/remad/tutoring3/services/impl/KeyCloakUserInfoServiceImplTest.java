package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.UserEntityService;
import edu.remad.tutoring3.helper.jwt.JwtAuthenticationTokenHelper;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class KeyCloakUserInfoServiceImplTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private UserEntityService userService;

    // we'll construct the service manually in each test after stubbing the builder
    private KeyCloakUserInfoServiceImpl service;

    @Test
    void isUserFound_returnsTrueWhenFound() throws Exception {
        JwtAuthenticationTokenHelper jwt = mock(JwtAuthenticationTokenHelper.class);
        when(jwt.getSub()).thenReturn("sub123");

        UserEntity u = new UserEntity();
        u.setSub("sub123");

        when(userService.getUserEntityBySub("sub123")).thenReturn(u);

        // make builder behave so constructor succeeds
        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        service = new KeyCloakUserInfoServiceImpl(restClientBuilder, userService);

        // call private method isUserFound via reflection
        Method m = KeyCloakUserInfoServiceImpl.class.getDeclaredMethod("isUserFound", JwtAuthenticationTokenHelper.class);
        m.setAccessible(true);

        boolean result = (boolean) m.invoke(service, jwt);

        assertTrue(result);
    }

    @Test
    void isUserFound_returnsFalseOnException() throws Exception {
        JwtAuthenticationTokenHelper jwt = mock(JwtAuthenticationTokenHelper.class);
        when(jwt.getSub()).thenReturn("subX");

        when(userService.getUserEntityBySub("subX")).thenThrow(new NullPointerException());

        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        service = new KeyCloakUserInfoServiceImpl(restClientBuilder, userService);

        Method m = KeyCloakUserInfoServiceImpl.class.getDeclaredMethod("isUserFound", JwtAuthenticationTokenHelper.class);
        m.setAccessible(true);

        boolean result = (boolean) m.invoke(service, jwt);

        assertFalse(result);
    }
}
