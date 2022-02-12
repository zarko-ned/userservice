package com.example.userservice.service;

import com.example.userservice.shared.dto.RoleDTO;


public interface RoleService {
    RoleDTO saveRole(RoleDTO role);
    RoleDTO getRoleByRoleId(String roleId);
    void addRoleToUser(String email, String roleName);
}
