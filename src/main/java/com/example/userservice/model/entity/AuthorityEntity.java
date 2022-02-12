package com.example.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity implements Serializable {
    private static final long serialVersionUID = -5162835040185855039L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String authorityId;

    @Column(nullable = false, length = 50)
    private String name;

}
