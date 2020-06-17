package com.example.creationtablesserver.controller;

import com.example.creationtablesserver.dao.TableDAO;
import com.example.creationtablesserver.model.DTO.TableDTO;
import com.example.creationtablesserver.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
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
                return new ResponseEntity<>(tableService.getAllTables(), HttpStatus.OK);
        }

        @PostMapping("/create")
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