package com.example.carefully.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
@Getter
@AllArgsConstructor
public class Admin extends User {
}
