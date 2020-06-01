package com.example.creationtablesserver.model;

import com.example.creationtablesserver.model.DTO.ColumnDTO;
import com.example.creationtablesserver.model.DTO.ForeignKey;
import com.example.creationtablesserver.model.DTO.PrimaryKey;
import com.example.creationtablesserver.model.DTO.TableDTO;
import com.example.creationtablesserver.model.META.ColumnMeta;
import com.example.creationtablesserver.model.META.TableMeta;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TableMapper  {


        public static TableMeta dtoTableToMeta(TableDTO dto) {
                TableMeta meta = new TableMeta();
                meta.setTable_name(dto.getName());
                // TODO
                meta.setColumns(new HashSet(dto.getColumns()));
                dto.getForeignKeys();
                dto.getPrimaryKeys();
                return meta;
        }
        public static TableDTO metaTabletoDto(TableMeta meta) {
                TableDTO dto =  new TableDTO();
                dto.setName(meta.getTable_name());
                dto.setColumns(metaColumnToDto(meta.getColumns()));
                /*конвертация Внешних ключей*/
                List<ForeignKey> fkeys = new LinkedList<>();
                meta.getColumns().forEach(mcol ->{
                        if (mcol.getForeignKey() != null) {
                                ForeignKey tmp = new ForeignKey();
                                tmp.setName(mcol.getColumn_name());
                                tmp.setKey(mcol.getForeignKey().getColumn_name());
                                tmp.setReferenceTable(mcol.getTable().getTable_name());
                        }
                });
                dto.setForeignKeys(fkeys);
                List<PrimaryKey> pkeys = new LinkedList<>();
                meta.getColumns().forEach(mcol ->{
                        if (mcol.getPr_key()) {
                                pkeys.add(new PrimaryKey(mcol.getColumn_name()));
                        } });
                dto.setPrimaryKeys(pkeys);
                return dto;
        }
        private static List<ColumnDTO> metaColumnToDto (Set<ColumnMeta> mColumns) {
                System.out.println("-----------METAtoDTO-------------");
                List<ColumnDTO> dtoColumns = new LinkedList<>();
                mColumns.forEach(column ->{
                        ColumnDTO columnDto = new ColumnDTO();
                        columnDto.setColumnName(column.getColumn_name());
                        columnDto.setType(column.getColumn_type());
                        dtoColumns.add(columnDto);
                });
                System.out.printf("Meta columns size: %d\nDto columns size: %d\n", mColumns.size(), dtoColumns.size());
                return dtoColumns;
        }
        private static Set<ColumnMeta>  dtoColumnToMeta (TableDTO dtoTable) {
                System.out.println("-----------DTOtoMETA-------------");
                List<ColumnDTO>dtoColumns = dtoTable.getColumns();
                Set<ColumnMeta> mColumns = new HashSet<>();
                dtoColumns.forEach(column ->{
                        ColumnMeta columnMeta = new ColumnMeta();
                        columnMeta.setColumn_name(column.getColumnName());
                        columnMeta.setColumn_type(column.getType());
                        columnMeta.setTable(dtoTableToMeta(dtoTable));
                        columnMeta.setPr_key(dtoTable.getPrimaryKeys().contains(columnMeta.getColumn_name()));

                        /*
                        "name": "BandMember",
        "columns": [
            {
                "columnName": "MusicianId",
                "type": "int"
            },
            {
                "columnName": "BandId",
                "type": "int"
            }
        ],
        "foreignKeys": [
            {
                "name": "BandId",
                "key": "BandId",
                "referenceTable": "Band"
            },
            {
                "name": "MusicianId",
                "key": "MusicianId",
                "referenceTable": "Musician"
            }
        ]
                        * */
                        columnMeta.setForeignKey();
                        System.out.println(columnMeta.toString());
                });
                System.out.printf("Meta columns size: %d\nDto columns size: %d\n");
                return mColumns;
        }

}
