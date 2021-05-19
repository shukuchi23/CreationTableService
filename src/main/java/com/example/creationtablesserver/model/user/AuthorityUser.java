package com.example.creationtablesserver.model.user;

import com.example.creationtablesserver.model.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "user_info", schema = "security_scheme")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuthorityUser implements Serializable {

    @Id
    @SequenceGenerator(name = "users_id_seq",
            schema = "security_scheme", sequenceName = "user_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long user_id;

    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 20, message = "Username to short")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    @NonNull
    private Role role = Role.USER;

    @Column(name = "create_date")
    @NonNull
    @JsonIgnore
    private Date create_date = new Date();

    @OneToMany(mappedBy = "owner",
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projectList;

    public void addProject (Project project) {
        projectList.add(project);
        project.setOwner(this);
    }
    public void removeProject (Project project) {
        projectList.remove(project);
        project.setOwner(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityUser that = (AuthorityUser) o;
        if (user_id == null)
            return false;
        else
            return (user_id.equals(that.user_id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }
    
    public AuthorityUser() {

    }
}
