package com.example.creationtablesserver.dao;

import com.example.creationtablesserver.model.DTO.TableDTO;

public interface TableDAO {
        boolean create (TableDTO table);
        boolean update (TableDTO table);
        boolean delete(String table);
}
