package com.example.userservice.repository;

import com.example.userservice.model.entity.AddressEntity;
import com.example.userservice.model.entity.UserEntity;
import com.example.userservice.shared.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() throws Exception {
        initUserEntity();
        userRepository.save(userEntity);
    }

    @Test
    void testFindAllUsersWithConfirmedEmailAddress() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest, Boolean.TRUE);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);
    }

    @Test
    void testFindUserByFirstName() {
        List<UserEntity> userEntities = userRepository.findUserByFirstName("Zarko");
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 3);

        UserEntity userEntity = userEntities.get(0);
        assertTrue(userEntity.getFirstName().equals("Zarko"));
    }


    private void initUserEntity() {
        this.userEntity = new UserEntity();
        this.userEntity.setId(1L);
        this.userEntity.setFirstName("Zarko");
        this.userEntity.setLastName("Nedeljkovic");
        this.userEntity.setUserId("123");
        this.userEntity.setEncryptedPassword("aa11");
        this.userEntity.setEmail("test@test.com");
        this.userEntity.setEmailVerificationToken("aaadasds");
        this.userEntity.setEmailVerificationStatus(true);
        this.userEntity.setAddresses(getAddressEntities());
    }

    private List<AddressDTO> getAddressDTOS() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setAddressId("aaads3fd");
        addressDTO.setCity("Belgrade");
        addressDTO.setCountry("Serbia");
        addressDTO.setPostalCode("21000");
        addressDTO.setStreetName("Zmaj Jovina");


        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        return addresses;
    }

    private List<AddressEntity> getAddressEntities() {
        List<AddressDTO> addressDTOS = getAddressDTOS();

        Type listType = new TypeToken<List<AddressEntity>>() {
        }.getType();
        return new ModelMapper().map(addressDTOS, listType);
    }
}