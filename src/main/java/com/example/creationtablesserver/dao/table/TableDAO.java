package com.example.creationtablesserver.dao.table;

import com.example.creationtablesserver.model.table.DTO.TableDTO;

public interface TableDAO {
        boolean create (TableDTO table);
        boolean update (TableDTO table);
        boolean delete(String table);
}
