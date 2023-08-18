package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;
    @Column(name = "origin_name")
    private String originName;
    @Column(name = "size")
    private long size;
    @Column(name = "type")
    private String type;
    @Column(name = "path")
    private String path;
    @Column(name = "duration")
    private long duration;
}
