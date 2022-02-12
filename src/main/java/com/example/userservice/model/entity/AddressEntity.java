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
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = -5584187458864659625L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String addressId;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 20)
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userDetails;
}
