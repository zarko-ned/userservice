package com.example.userservice.repository;

import com.example.userservice.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    @Query("SELECT u FROM UserEntity u WHERE u.emailVerificationStatus = :boolParam")
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest, @Param("boolParam") Boolean boolParam);

    @Query("SELECT u FROM UserEntity u WHERE u.firstName = :firstNameParam")
    List<UserEntity> findUserByFirstName(@Param("firstNameParam") String firstName);

}
