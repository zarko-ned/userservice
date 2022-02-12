package com.example.userservice.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 807677867283837455L;
    private long id;
    private String roleId;
    private String name;

}
