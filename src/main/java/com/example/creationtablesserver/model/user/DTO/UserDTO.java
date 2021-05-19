package com.example.creationtablesserver.model.user.DTO;

import com.example.creationtablesserver.model.project.DTO.ShortProjectDTO;
import com.example.creationtablesserver.model.user.AuthorityUser;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    private Long user_id;
    private String login;
    private List<ShortProjectDTO> projectsList;

    public static UserDTO fromAuthorityUser(AuthorityUser authorityUser) {
        UserDTO user = new UserDTO();
        user.setUser_id(authorityUser.getUser_id());
        user.setLogin(authorityUser.getUsername());
        user.setProjectsList(
                authorityUser.getProjectList().stream()
                        .map(ShortProjectDTO::fromProject)
                        .collect(Collectors.toList()));
        return user;
    }
}
