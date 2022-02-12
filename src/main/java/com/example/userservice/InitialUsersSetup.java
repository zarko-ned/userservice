package com.example.userservice;

import com.example.userservice.model.entity.AuthorityEntity;
import com.example.userservice.model.entity.RoleEntity;
import com.example.userservice.repository.AuthorityRepository;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.shared.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Transactional
public class InitialUsersSetup {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final Utils utils;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
    }

    private AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity();
            authority.setName(name);
            authority.setAuthorityId(utils.generatePublicId(30));
            authorityRepository.save(authority);
        }
        return authority;
    }

    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorityEntities) {
        RoleEntity role = roleRepository.findByName(name);
        if(role == null) {
            role = new RoleEntity();
            role.setName(name);
            role.setRoleId(utils.generatePublicId(30));
            role.setAuthorities(authorityEntities);
            roleRepository.save(role);
        }
        return role;
    }
}
