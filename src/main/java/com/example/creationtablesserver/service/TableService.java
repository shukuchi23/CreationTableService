package com.example.creationtablesserver.service;

import com.example.creationtablesserver.model.DTO.TableDTO;
import com.example.creationtablesserver.model.META.TableMeta;
import com.example.creationtablesserver.model.TableMapper;
import com.example.creationtablesserver.repository.TableRepository;
import com.example.creationtablesserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

        @Autowired
        private TableRepository tableRepository;

        /*public TableService() {
        }

        @Autowired
        public void setTableRepository(TableRepository tableRepository) {
                this.tableRepository = tableRepository;
        }

        @Autowired
        public TableService(TableRepository repository) {
                this.tableRepository = repository;
        }*/

        @Transactional
        public List<TableDTO> getAllTables() {
                return tableRepository.findAll().stream().map(TableMapper::metaTabletoDto).collect(Collectors.toList());
        }

        @Transactional
        public void deleteTable(String name) {
                tableRepository.deleteById(name);
        }

        @Transactional
        public TableDTO getByName(String name) {
                return TableMapper.metaTabletoDto(tableRepository.findById(name).get());
        }

        @Transactional
        public void addTable(TableDTO table) {
                TableMeta meta = TableMapper.dtoTableToMeta(table);
                System.out.println(meta);
                tableRepository.saveAndFlush(meta);
        }

        @Transactional
        public void updateTable(TableDTO table) {
                 tableRepository.saveAndFlush(TableMapper.dtoTableToMeta(table));
        }
}
