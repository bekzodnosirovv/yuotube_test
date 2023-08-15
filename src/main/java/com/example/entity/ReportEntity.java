package com.example.entity;

import com.example.entity.base.BaseIntegerEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportEntity extends BaseIntegerEntity {
}
