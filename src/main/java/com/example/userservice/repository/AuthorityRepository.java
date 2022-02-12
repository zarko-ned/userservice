package com.example.userservice.repository;

import com.example.userservice.model.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
