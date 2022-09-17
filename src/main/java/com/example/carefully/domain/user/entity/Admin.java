package com.example.carefully.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("Admin")
@Getter
@AllArgsConstructor
public class Admin extends CommonUser{
}
