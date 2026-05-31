package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.AddressEntityRepository;
import edu.remad.tutoring3.services.UserEntityService;

@ExtendWith(MockitoExtension.class)
class AddressEntityServiceImplTest {

    @Mock
    private AddressEntityRepository addressRepository;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private AddressEntityServiceImpl service;

    @Test
    void findByAddressId_found() {
        AddressEntity addr = new AddressEntity();
        addr.setId(1L);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(addr));

        AddressEntity result = service.findByAddressId(1L);

        assertSame(addr, result);
    }

    @Test
    void findByAddressId_notFound() {
        when(addressRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findByAddressId(42L));
    }

    @Test
    void saveAddress_delegatesToRepository() {
        AddressEntity addr = new AddressEntity();
        addr.setId(2L);

        when(addressRepository.save(addr)).thenReturn(addr);

        AddressEntity saved = service.saveAddress(addr);

        assertSame(addr, saved);
    }

    @Test
    void findByAdressIds_returnsListCopy() {
        List<Long> ids = Arrays.asList(1L, 2L);
        AddressEntity a1 = new AddressEntity();
        a1.setId(1L);
        AddressEntity a2 = new AddressEntity();
        a2.setId(2L);

        when(addressRepository.findAllById(ids)).thenReturn(Arrays.asList(a1, a2));

        List<AddressEntity> result = service.findByAdressIds(ids);

        assertEquals(2, result.size());
        // ensure a new list instance is returned
        assertNotSame(result, Arrays.asList(a1, a2));
    }

    @Test
    void patchAddress_updatesFieldsAndSaves() {
        AddressEntity stored = new AddressEntity();
        stored.setId(1L);
        stored.setAddressStreet("OldStreet");
        stored.setAddressHouseNo("1A");
        stored.setAddressZipCode(1000);
        stored.setPlace("OldPlace");

        AddressEntity patch = new AddressEntity();
        patch.setId(1L);
        patch.setAddressStreet("NewStreet");
        patch.setAddressHouseNo("2B");
        patch.setAddressZipCode(2000);
        patch.setPlace("NewPlace");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(stored));
        when(addressRepository.save(any(AddressEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        AddressEntity result = service.patchAddress(patch);

        // stored object should have been updated
        assertEquals("NewStreet", stored.getAddressStreet());
        assertEquals("2B", stored.getAddressHouseNo());
        assertEquals(2000, stored.getAddressZipCode());
        assertEquals("NewPlace", stored.getPlace());

        // the returned object should be the saved stored entity
        assertSame(stored, result);
        verify(addressRepository).save(eq(stored));
    }

    @Test
    void findAddressesByUserId_delegatesToRepository() {
        UserEntity user = new UserEntity();
        user.setUserId(5L);
        AddressEntity addr = new AddressEntity();
        addr.setId(7L);

        when(userEntityService.getUserEntityById(5L)).thenReturn(user);
        when(addressRepository.findByUser(user)).thenReturn(Collections.singletonList(addr));

        List<AddressEntity> result = service.findAddressesByUserId(5L);

        assertEquals(1, result.size());
        assertEquals(7L, result.get(0).getId());
    }

    @Test
    void deleteAddressById_delegatesToRepository() {
        service.deleteAddressById(3L);

        verify(addressRepository).deleteById(3L);
    }
}
