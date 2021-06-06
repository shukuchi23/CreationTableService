package com.example.creationtablesserver.dao.table;

import com.example.creationtablesserver.model.table.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

@Component
public class PostgresDAO implements TableDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(OldTableDTO table) {
        StringBuilder str = new StringBuilder("create table if not exists ");
        str.append(table.getName()).append(" (");
        List<ColumnDTO> columns = table.getColumns();
        for (ColumnDTO column : columns) {
            str.append(String.format(" %s %s,", column.getColumnName(), column.getColumnType()));
        }

        if (table.getPrimaryKeys() != null)
            if (!table.getPrimaryKeys().isEmpty())
                str.append(" ").append(pkeyToSql(table.getPrimaryKeys().iterator()));

        if (table.getForeignKeys() != null)
            if (!table.getForeignKeys().isEmpty())
                str.append(", ").append(fkeyToSql(table.getForeignKeys()));

        str.append(");");
        jdbcTemplate.execute(str.toString());
    }

    private StringBuilder fkeyToSql(List<OldForeignKey> fkeys) {

        StringBuilder str = new StringBuilder();

        /*Встречаемые referenceTable  в списке ForeignKey*/
        Set<String> nameSet = new HashSet<>();

        Iterator<OldForeignKey> fkeyIter = fkeys.iterator();
        while (fkeyIter.hasNext()) {
            OldForeignKey fkey = fkeyIter.next();
            if (!nameSet.contains(fkey.getReferenceTable())) {
                /*Если уже есть запись в nameSet, значит внешних ключей несколько*/
                if (!nameSet.isEmpty())
                    str.append(", ");
                nameSet.add(fkey.getReferenceTable());
                StringBuilder prefix = new StringBuilder().append("FOREIGN KEY (");
                StringBuilder suffix = new StringBuilder().append("REFERENCES ").append(fkey.getReferenceTable())
                        .append(" (");
                /*--- Внешний ключ может быть композитный ---*/
                /*Столбцы ссылающиеся на таблицу*/
                List<OldForeignKey> referenceTableColumn = getAllFkeysByRefTable(fkey.getReferenceTable(), fkeys.iterator());
                Iterator<OldForeignKey> rtc_iter = referenceTableColumn.iterator();

                OldForeignKey fk = rtc_iter.next();
                prefix.append(fk.getName());
                suffix.append(fk.getKey());
                while (rtc_iter.hasNext()) {
                    fk = rtc_iter.next();
                    prefix.append(", ").append(fk.getName());
                    suffix.append(", ").append(fk.getKey());
                }
                prefix.append(") ");
                suffix.append(")");

                str.append(prefix).append(suffix);
            }
        }
        return str;
    }

    /*Возвращает все объекты Foreign Key с полем
            'referenceTable' равным refTable */
    private List<OldForeignKey> getAllFkeysByRefTable(String refTable, Iterator<OldForeignKey> fkey_iter) {
        List<OldForeignKey> keys = new LinkedList<>();
        OldForeignKey tmp;
        while (fkey_iter.hasNext()) {
            tmp = fkey_iter.next();
            if (tmp.getReferenceTable().equals(refTable))
                keys.add(tmp);
        }
        return keys;
    }

    private StringBuilder pkeyToSql(Iterator<PrimaryKey> pkey_iter) {
        StringBuilder str = new StringBuilder("primary key ( ");
        PrimaryKey pkey = pkey_iter.next();
        str.append(pkey.getKey());
        while (pkey_iter.hasNext()) {
            pkey = pkey_iter.next();
            str.append(", ").append(pkey.getKey());
        }
        str.append(')');
        return str;
    }

    @Override
    public boolean update(TableDTO table) {
        return false;
    }

    @Override
    public void delete(String table) {
        jdbcTemplate.execute(String.format("drop table if exists %s cascade;", table));
    }
}
