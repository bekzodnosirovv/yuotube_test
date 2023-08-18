package com.example.entity;

import com.example.entity.base.BaseIntegerEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseIntegerEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "image_id")
    private String image_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private ProfileRole role;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ProfileStatus status;
}
