package com.example.userservice.controller;

import com.example.userservice.model.request.RoleDetailsRequestModel;
import com.example.userservice.model.response.RoleRest;
import com.example.userservice.shared.dto.RoleDTO;
import com.example.userservice.model.request.RoleToUserModel;
import com.example.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping(path = "/{id}")
    public RoleRest getRole(@PathVariable String id) {
        RoleDTO roleDto = roleService.getRoleByRoleId(id);
        return new ModelMapper().map(roleDto, RoleRest.class);
    }

    @PostMapping
    public RoleRest saveRole(@RequestBody RoleDetailsRequestModel roleDetailsRequestModel) {
        RoleRest returnValue = new RoleRest();

        RoleDTO roleDto = new RoleDTO();
        BeanUtils.copyProperties(roleDetailsRequestModel, roleDto);

        RoleDTO createdRole = roleService.saveRole(roleDto);
        BeanUtils.copyProperties(createdRole, returnValue);

        return returnValue;
    }

    @PostMapping("/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserModel form){
        roleService.addRoleToUser(form.getUserEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
