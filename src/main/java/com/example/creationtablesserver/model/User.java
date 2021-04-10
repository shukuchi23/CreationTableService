package com.example.creationtablesserver.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_info", schema = "security_scheme")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long user_id;
    @Column(name = "username")
    @NonNull
    private String username;
    @Column(name = "password")
    @NonNull
    private String password;

   /* @OneToMany()
    private Set<Database> usedDataBases;*/
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    @NonNull
    private Role role;

    @Column(name = "create_date")
    @NonNull
    private Date create_date;

    public User() {

    }
}
