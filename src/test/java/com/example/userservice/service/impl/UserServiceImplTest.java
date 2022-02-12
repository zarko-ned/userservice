package com.example.userservice.service.impl;

import com.example.userservice.model.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.shared.dto.UserDTO;
import com.example.userservice.shared.utils.Utils;
import com.example.userservice.model.entity.AddressEntity;
import com.example.userservice.shared.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Utils utils;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String userId = "aaabbbccc";
    private String encryptedPassword = "123aaa321";
    private UserEntity userEntity;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        initUserEntity();
    }

    private void initUserEntity() {
        this.userEntity = new UserEntity();
        this.userEntity.setId(1L);
        this.userEntity.setFirstName("Zarko");
        this.userEntity.setUserId(userId);
        this.userEntity.setEncryptedPassword(encryptedPassword);
        this.userEntity.setEmail("test@test.com");
        this.userEntity.setEmailVerificationToken("aaadasds");
        this.userEntity.setAddresses(getAddressEntities());
    }

    @Test
    void testGetUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDTO userDTO = userService.getUser("test@test.com");

        assertNotNull(userDTO);
        assertEquals("Zarko", userDTO.getFirstName());
    }

    @Test
    void testGetUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser("test@test.com");
                }
        );
    }

    @Test
    void testCreateUser_RuntimeException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        assertThrows(RuntimeException.class,
                () -> {
                    userService.saveUser(generateUserDTO());
                }
        );
    }

    @Test
    void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generatePublicId(anyInt())).thenReturn("f343fedfd");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO userDTO = generateUserDTO();
        UserDTO savedUser = userService.saveUser(userDTO);
        assertNotNull(savedUser);
        assertEquals(userEntity.getFirstName(), savedUser.getFirstName());
        assertEquals(userEntity.getLastName(), savedUser.getLastName());
        assertNotNull(savedUser.getUserId());
        assertEquals(savedUser.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(3)).generatePublicId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("1234689");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    private UserDTO generateUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddresses(getAddressDTOS());
        userDTO.setFirstName("Zarko");
        userDTO.setLastName("Nedeljkovic");
        userDTO.setPassword("1234689");
        userDTO.setEmail("test@test.com");
        return userDTO;
    }

    private List<AddressDTO> getAddressDTOS() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setCity("Belgrade");
        addressDTO.setCountry("Serbia");
        addressDTO.setPostalCode("21000");
        addressDTO.setStreetName("Zmaj Jovina");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setType("billing");
        billingAddressDTO.setCity("Belgrade");
        billingAddressDTO.setCountry("Serbia");
        billingAddressDTO.setPostalCode("21000");
        billingAddressDTO.setStreetName("Zmaj Jovina");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        addresses.add(billingAddressDTO);
        return addresses;
    }

    private List<AddressEntity> getAddressEntities() {
        List<AddressDTO> addressDTOS = getAddressDTOS();

        Type listType = new TypeToken<List<AddressEntity>>() {
        }.getType();
        return new ModelMapper().map(addressDTOS, listType);
    }
}