package com.example.creationtablesserver.rest;

import com.example.creationtablesserver.model.project.DTO.ProjectDTO;
import com.example.creationtablesserver.model.utils.ProjectMapper;
import com.example.creationtablesserver.model.user.AuthorityUser;
import com.example.creationtablesserver.model.user.DTO.UserDTO;
import com.example.creationtablesserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class UserRestController {
    @Autowired
    private UserService userService;

    /* TODO: добавить доступ админу*/
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(UserDTO::fromAuthorityUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/id{id}")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public UserDTO getById(@PathVariable Long id) {
        AuthorityUser user = userService.getById(id);
        return UserDTO.fromAuthorityUser(user);
    }

    @PostMapping("/id{id}/create")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public ResponseEntity<String> createProject(@PathVariable Long id , @RequestBody ProjectDTO project){
        AuthorityUser user = userService.getById(id);
        user.addProject(ProjectMapper.MetaFromDto(project, id));
        userService.updateUser(user);
        return new ResponseEntity<>("nice", HttpStatus.OK);
    }

}
