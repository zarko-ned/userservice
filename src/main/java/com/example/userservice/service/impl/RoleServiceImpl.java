package com.example.userservice.service.impl;

import com.example.userservice.model.entity.RoleEntity;
import com.example.userservice.service.RoleService;
import com.example.userservice.shared.dto.RoleDTO;
import com.example.userservice.shared.utils.Utils;
import com.example.userservice.model.entity.UserEntity;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public RoleDTO saveRole(RoleDTO role) {
        if (roleRepository.findByName(role.getName()) != null) throw new RuntimeException("Record already exists!");

        RoleEntity newRoleEntity = new RoleEntity();
        BeanUtils.copyProperties(role, newRoleEntity);

        String publicRoleId = utils.generatePublicId(30);
        newRoleEntity.setRoleId(publicRoleId);
        RoleEntity storedRoleDetails = roleRepository.save(newRoleEntity);

        RoleDTO returnValue = new RoleDTO();
        BeanUtils.copyProperties(storedRoleDetails, returnValue);

        return returnValue;
    }

    @Override
    public RoleDTO getRoleByRoleId(String roleId) {
        RoleDTO returnValue = new RoleDTO();
        RoleEntity roleEntity = roleRepository.findById(roleId);

        if(roleEntity == null)
            return returnValue;

        BeanUtils.copyProperties(roleEntity, returnValue);

        return returnValue;

    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        UserEntity user = userRepository.findByEmail(email);
        RoleEntity role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }
}
