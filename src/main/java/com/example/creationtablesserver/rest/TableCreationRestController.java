package com.example.creationtablesserver.rest;

import com.example.creationtablesserver.dao.table.TableDAO;
import com.example.creationtablesserver.model.DTO.TableDTO;
import com.example.creationtablesserver.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@EnableAutoConfiguration
@Transactional
//@RequestMapping("/projects")
@PreAuthorize("hasAuthority('user:crud')")
public class TableCreationRestController {
        @Autowired
        private TableDAO tableDAO;
        @Autowired
        private TableService tableService;

        @Autowired
        public void setTableService(TableService tableService) {this.tableService = tableService;}

        @Autowired
        public void setTableDAO(TableDAO tableDAO) {
                this.tableDAO = tableDAO;
        }

        @GetMapping("/table/{tableName}")
        public ResponseEntity<TableDTO> getTableByName(@PathVariable String tableName) {
                return new ResponseEntity<>(tableService.getByName(tableName), HttpStatus.OK);
        }

        @GetMapping("/tables")
        public ResponseEntity<List<TableDTO>> getAllTables() {
//                System.out.printf("%s[%d]\n", authentication.getName(), ();
                return new ResponseEntity<>(tableService.getAllTables(), HttpStatus.OK);
        }

        @PostMapping("/create")
        @PreAuthorize("hasAuthority('user:crud')")
        public ResponseEntity<String> create(@RequestBody List<TableDTO> tables) {

                for (TableDTO table : tables) {
                        tableDAO.create(table);
                        System.out.println("DAO succes" + table.getName());
                        tableService.addTable(table);
                }

                return new ResponseEntity<>("creation successful", HttpStatus.CREATED);
        }

        @DeleteMapping("/drop/{tableName}")
        public ResponseEntity<String> dropTable(@PathVariable String tableName) {
                tableService.deleteTable(tableName);
                tableDAO.delete(tableName);
                return new ResponseEntity<>("dropping successful", HttpStatus.OK);
        }

        //        TODO:
        @PutMapping("/edit/{tableName}")
        public ResponseEntity<String> editTable(@PathVariable String tableName, @RequestBody TableDTO table) {
//                tableDAO.update()
//                tableService.updateTable(table);
                return new ResponseEntity<>("editing successful (DO NOTHING)", HttpStatus.OK);
        }

}