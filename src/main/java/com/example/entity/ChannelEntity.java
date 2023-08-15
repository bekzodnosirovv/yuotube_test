package com.example.entity;

import com.example.entity.base.BaseStringEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity extends BaseStringEntity {
}
