package com.example.userservice.repository;

import com.example.userservice.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findById(String roleId);
    RoleEntity findByName(String roleName);
}
