package com.example.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = -7483708978251461467L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String roleId;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AuthorityEntity> authorities = new ArrayList<>();
}
