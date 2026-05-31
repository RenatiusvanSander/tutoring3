package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceImplTest {

    @Mock
    private UserEntityRepository userRepo;

    @InjectMocks
    private UserEntityServiceImpl service;

    @Test
    void saveUser_delegates() {
        UserEntity u = mock(UserEntity.class);
        when(userRepo.save(u)).thenReturn(u);

        UserEntity saved = service.saveUserEntity(u);

        assertSame(u, saved);
        verify(userRepo).save(u);
    }

    @Test
    void getUserById_delegates() {
        UserEntity u = mock(UserEntity.class);
        when(userRepo.findById(5L)).thenReturn(Optional.of(u));

        UserEntity loaded = service.getUserEntityById(5L);

        assertSame(u, loaded);
    }

    @Test
    void getUserBySub_delegates() {
        UserEntity u = mock(UserEntity.class);
        when(userRepo.findBySub("sub123")).thenReturn(u);

        UserEntity loaded = service.getUserEntityBySub("sub123");

        assertSame(u, loaded);
    }
}
