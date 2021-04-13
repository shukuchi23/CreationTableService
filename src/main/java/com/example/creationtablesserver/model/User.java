package com.example.creationtablesserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "user_info", schema = "security_scheme")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq", schema = "security_scheme", sequenceName = "user_id_seq",
                        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long user_id;

    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 20, message = "Username to short")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    @NonNull
    private Role role = Role.USER;

    @Column(name = "create_date")
    @NonNull
    private Date create_date = new Date();

    public User() {
    }
}
