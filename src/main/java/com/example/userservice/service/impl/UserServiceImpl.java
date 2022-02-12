package com.example.userservice.service.impl;

import com.example.userservice.shared.dto.UserDTO;
import com.example.userservice.shared.utils.Utils;
import com.example.userservice.shared.dto.AddressDTO;
import com.example.userservice.model.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO saveUser(UserDTO user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("Record already exists!");

        List<AddressDTO> addresses = new ArrayList<>(user.getAddresses());
        for (int i = 0; i < user.getAddresses().size(); i++) {
            AddressDTO address = addresses.get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.generatePublicId(30));
            addresses.set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity newUserEntity = modelMapper.map(user, UserEntity.class);

        newUserEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        String publicUserId = utils.generatePublicId(30);
        newUserEntity.setUserId(publicUserId);
        UserEntity storedUserDetails = userRepository.save(newUserEntity);

        UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);

        return returnValue;
    }

    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO returnValue = new UserDTO();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException(userId);

        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO user) {
        UserDTO returnValue = new UserDTO();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException(userId);


        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());


        UserEntity updatedUserEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserEntity, returnValue);

        return returnValue;
    }

    @Override
    public Boolean deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }

        userRepository.delete(userEntity);
        return true;
    }

    @Override
    public List<UserDTO> getUsers(int page, int limit) {
        List<UserDTO> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPages = userRepository.findAll(pageableRequest);

        List<UserEntity> userEntities = userPages.getContent();

        for (UserEntity userEntity : userEntities) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
       return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

    }
}
