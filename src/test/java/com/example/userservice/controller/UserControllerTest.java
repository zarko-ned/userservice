package com.example.userservice.controller;

import com.example.userservice.service.impl.UserServiceImpl;
import com.example.userservice.shared.dto.UserDTO;
import com.example.userservice.model.response.UserRest;
import com.example.userservice.shared.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserServiceImpl userService;

    private UserDTO userDTO;

    public final String USER_ID = "dfsd838r";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        initUserDTO();
    }

    private void initUserDTO() {
        this.userDTO = new UserDTO();
        this.userDTO.setFirstName("Zarko");
        this.userDTO.setLastName("Nedeljkovic");
        this.userDTO.setEmail("test@test.com");
        this.userDTO.setEmailVerificationStatus(Boolean.FALSE);
        this.userDTO.setEmailVerificationToken(null);
        this.userDTO.setAddresses(getAddressDTOS());
        this.userDTO.setUserId(USER_ID);
        this.userDTO.setEncryptedPassword("xc123321");
    }

    @Test
    void testGetUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(this.userDTO);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDTO.getFirstName(), userRest.getFirstName());
        assertEquals(userDTO.getLastName(), userRest.getLastName());
        assertTrue(userDTO.getAddresses().size() == userRest.getAddresses().size());
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
}