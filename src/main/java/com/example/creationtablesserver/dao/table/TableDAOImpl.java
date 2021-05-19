package com.example.creationtablesserver.dao.table;

import com.example.creationtablesserver.model.table.DTO.ColumnDTO;
import com.example.creationtablesserver.model.table.DTO.ForeignKey;
import com.example.creationtablesserver.model.table.DTO.PrimaryKey;
import com.example.creationtablesserver.model.table.DTO.TableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

@Component
public class TableDAOImpl implements TableDAO {
        JdbcTemplate jdbcTemplate;

        @Autowired
        public TableDAOImpl(DataSource dataSource) {
                this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        @Override
        public boolean create(TableDTO table) {

                StringBuilder str = new StringBuilder("create table if not exists ");
                str.append(table.getName()).append(" ( ");
                str.append(columnToSql(table.getColumns().iterator()));

                if (table.getPrimaryKeys() != null)
                        if (!table.getPrimaryKeys().isEmpty())
                                str.append(", ").append(pkeyToSql(table.getPrimaryKeys().iterator()));

                if (table.getForeignKeys() != null)
                        if (!table.getForeignKeys().isEmpty())
                                str.append(", ").append(fkeyToSql(table.getForeignKeys()));

                str.append(");");
                jdbcTemplate.execute(str.toString());

                return true;
        }
        private StringBuilder columnToSql(Iterator<ColumnDTO> col_iter) {
                StringBuilder str = new StringBuilder();
                ColumnDTO column = col_iter.next();

                str.append(column.getColumnName()).append(' ').append(column.getType());
                while (col_iter.hasNext()) {
                        column = col_iter.next();
                        str.append(", ").append(column.getColumnName()).append(' ').append(column.getType());
                }
                return str;
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
//                primary key (key1, key2, ...)
        }
        private StringBuilder fkeyToSql(List <ForeignKey> fkeys) {
                StringBuilder str = new StringBuilder();
                
                /*Встречаемые referenceTable  в списке ForeignKey*/
                Set<String > nameSet = new HashSet<>();

                Iterator<ForeignKey> fkey_iter = fkeys.iterator();
                while (fkey_iter.hasNext()) {
                        ForeignKey fkey = fkey_iter.next();
                        if (!nameSet.contains(fkey.getReferenceTable())) {
                                /*Если уже есть запись в nameSet, значит внешних ключей несколько*/
                                if (!nameSet.isEmpty())
                                                str.append(", ");
                                nameSet.add(fkey.getReferenceTable().toString());
                                StringBuilder prefix = new StringBuilder().append("FOREIGN KEY (");
                                StringBuilder suffix = new StringBuilder().append("REFERENCES ").append(fkey.getReferenceTable())
                                                                                .append(" (");
                                /*--- Внешний ключ может быть композитный ---*/
                                /*Столбцы ссылающиеся на таблицу*/
                                List<ForeignKey> referenceTableColumn = getAllFkeysByRefTable(fkey.getReferenceTable().toString(), fkeys.iterator());
                                Iterator<ForeignKey> rtc_iter = referenceTableColumn.iterator();

                                ForeignKey fk = rtc_iter.next();
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
        private List<ForeignKey> getAllFkeysByRefTable(String refTable, Iterator<ForeignKey> fkey_iter ) {
                List<ForeignKey> keys = new LinkedList<>();
                ForeignKey tmp;
                while (fkey_iter.hasNext()) {
                        tmp = fkey_iter.next();
                        if (tmp.getReferenceTable().equals(refTable))
                                keys.add(tmp);
                }
                return keys;
        }

        @Override
        public boolean update(TableDTO table) {

                return false;
        }

        @Override
        public boolean delete(String tableName) {
                jdbcTemplate.execute(new Formatter().format("drop table if exists %s ;", tableName).toString());
                return true;
        }
}
