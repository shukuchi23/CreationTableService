package com.example.creationtablesserver.dao.table;

import com.example.creationtablesserver.model.table.DTO.OldTableDTO;
import com.example.creationtablesserver.model.table.DTO.TableDTO;

public interface TableDAO {
    //        boolean create (TableDTO table);
    void create(OldTableDTO table);

    /*TableMeta getMetaTablesFromScheme(String scheme) throws SQLException;
    void getMeta() throws SQLException;*/
    boolean update(TableDTO table);

    void delete(String table);
}
