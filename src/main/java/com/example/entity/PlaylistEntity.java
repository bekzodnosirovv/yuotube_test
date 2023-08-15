package com.example.entity;

import com.example.entity.base.BaseIntegerEntity;
import com.example.entity.base.BaseStringEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity extends BaseStringEntity {
}
