package com.example.userservice.controller;

import com.example.userservice.model.response.status.RequestOperationName;
import com.example.userservice.service.UserService;
import com.example.userservice.shared.dto.UserDTO;
import com.example.userservice.model.response.AddressRest;
import com.example.userservice.service.AddressService;
import com.example.userservice.shared.dto.AddressDTO;
import com.example.userservice.model.request.UserDetailsRequestModel;
import com.example.userservice.model.response.status.OperationStatusModel;
import com.example.userservice.model.response.UserRest;
import com.example.userservice.model.response.status.RequestOperationStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserDTO userDto = userService.getUserByUserId(id);
        return new ModelMapper().map(userDto, UserRest.class);
    }

    @PostMapping
    public UserRest saveUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserRest returnValue = new UserRest();

        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDto = modelMapper.map(userDetailsRequestModel, UserDTO.class);

        UserDTO createdUser = userService.saveUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserRest returnValue = new UserRest();

        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDTO updatedUser = userService.updateUser(id, userDto);

        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        if (userService.deleteUser(id))
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        else
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header")
    })
    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDTO> users = userService.getUsers(page, limit);

        for (UserDTO usersDto : users) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(usersDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}/addresses")
    public List<AddressRest> getUserAddresses(@PathVariable String id) {
        List<AddressRest> returnValue = new ArrayList<>();

        List<AddressDTO> addressesDTO = addressService.getAddresses(id);

        if (addressesDTO != null && !addressesDTO.isEmpty()) {
            Type listType = new TypeToken<List<AddressRest>>() {
            }.getType();
            returnValue = new ModelMapper().map(addressesDTO, listType);
        }

        return returnValue;
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}")
    public AddressRest getUserAddress(@PathVariable String addressId) {

        AddressDTO addressesDTO = addressService.getAddress(addressId);

        return new ModelMapper().map(addressesDTO, AddressRest.class);

    }
}
