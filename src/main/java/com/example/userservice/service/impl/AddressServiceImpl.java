package com.example.userservice.service.impl;

import com.example.userservice.model.entity.UserEntity;
import com.example.userservice.repository.AddressRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AddressService;
import com.example.userservice.model.entity.AddressEntity;
import com.example.userservice.shared.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null)
            return returnValue;

        Iterable<AddressEntity> addressEntities = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity address : addressEntities){
            returnValue.add(modelMapper.map(address, AddressDTO.class));
        }

        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if(addressEntity != null)
            returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);

        return returnValue;
    }
}
