package com.example.creationtablesserver.rest;

import com.example.creationtablesserver.dao.table.TableDAO;
import com.example.creationtablesserver.model.project.DTO.ProjectDTO;
import com.example.creationtablesserver.model.project.Project;
import com.example.creationtablesserver.model.project.ProjectMapper;
import com.example.creationtablesserver.model.table.DTO.OldForeignKey;
import com.example.creationtablesserver.model.table.DTO.OldTableDTO;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import com.example.creationtablesserver.model.user.AuthorityUser;
import com.example.creationtablesserver.model.user.DTO.UserDTO;
import com.example.creationtablesserver.service.ProjectService;
import com.example.creationtablesserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private TableDAO postgresDAO;

    /* TODO: добавить доступ админу*/
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(UserDTO::fromAuthorityUser)
                .collect(Collectors.toList());
    }

    @GetMapping("/id{id}/projects")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public UserDTO getById(@PathVariable Long id) {
        AuthorityUser user = userService.getById(id);
        return UserDTO.fromAuthorityUser(user);
    }

    @PostMapping("/id{id}/projects")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public ResponseEntity<String> createProjectForUser(@PathVariable Long id,
                                                       @RequestBody ProjectDTO project) {
        AuthorityUser user = userService.getById(id);
        List<TableDTO> tables = project.getTables();
        // физическое создание таблиц по DTO
        for (TableDTO table : tables) {
            OldTableDTO old = new OldTableDTO();
            old.setName(table.getName());
            old.setColumns(table.getColumns());
            old.setPrimaryKeys(table.getPrimaryKeys());
            if (table.getForeignKeys() != null)
                old.setForeignKeys(table.getForeignKeys().stream()
                        .map(fk -> new OldForeignKey(
                                fk.getName(),
                                fk.getKey(),
                                tables.stream()
                                        .filter(t -> t.getTableId().equals(fk.getReferenceTable()))
                                        .findFirst()
                                        .get().getName())
                        ).collect(Collectors.toList()));
            postgresDAO.create(old);
        }
        // сохранение метаинформации
        user.addProject(ProjectMapper.MetaFromDto(project));
        return new ResponseEntity<>("creation successful", HttpStatus.OK);
    }

    @GetMapping("/id{id}/projects/{p_id}")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public ResponseEntity<?> getUserProject(@PathVariable Long id,
                                            @PathVariable Long p_id) {
        Optional<Project> projectById = userService.getById(id).getProjectById(p_id);
        if (!projectById.isPresent()) {
            return new ResponseEntity<>("the project does not exist", HttpStatus.NOT_FOUND);
        }
        ProjectDTO dto = ProjectDTO.fromEntity(projectById.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/id{id}/projects/{p_id}")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public ResponseEntity<String> dropProjectFromUser(@PathVariable Long id,
                                                      @PathVariable Long p_id) {
        AuthorityUser user = userService.getById(id);
        Optional<Project> target = user.getProjectById(p_id);
        if (!target.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        user.removeProject(target.get());
        return new ResponseEntity<>("project was dropped", HttpStatus.OK);
    }

    @PutMapping("/id{id}/projects/{p_id}")
    @PreAuthorize("@securityChecker.checkUserId(#id)")
    public ResponseEntity<String> editUserProject(@PathVariable Long id,
                                                  @PathVariable Long p_id,
                                                  @RequestBody ProjectDTO project) {
        // TODO: эта часть не работает
        AuthorityUser user = userService.getById(id);
        Optional<Project> target = user.getProjectById(p_id);
        if (!target.isPresent()) {
            return new ResponseEntity<>("project", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("project was modified", HttpStatus.OK);
    }
}
