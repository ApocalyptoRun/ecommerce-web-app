package com.ecommerce.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long userId;

    @Column(name = "first_name")
    private  String firstName;

    @Column(name = "last_name")
    private  String lastName;

    @Column(name = "email")
    private  String email;

    @Column(name = "password")
    private  String password;


}
