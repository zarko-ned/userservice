package com.example.userservice.service;

import com.example.userservice.shared.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String email);
    UserDTO getUserByUserId(String userId);
    UserDTO updateUser(String userId, UserDTO user);
    Boolean deleteUser(String userId);
    List<UserDTO> getUsers(int page, int limit);
}
