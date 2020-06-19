package com.example.creationtablesserver.model;

import com.example.creationtablesserver.model.DTO.ColumnDTO;
import com.example.creationtablesserver.model.DTO.ForeignKey;
import com.example.creationtablesserver.model.DTO.PrimaryKey;
import com.example.creationtablesserver.model.DTO.TableDTO;
import com.example.creationtablesserver.model.META.ColumnId;
import com.example.creationtablesserver.model.META.ColumnMeta;
import com.example.creationtablesserver.model.META.FkeyMeta;
import com.example.creationtablesserver.model.META.TableMeta;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TableMapper  {

        public static TableMeta dtoTableToMeta(TableDTO dto) {
                TableMeta meta = new TableMeta();
                meta.setTable_name(dto.getName());
                Set<ColumnMeta> columnMetas = new HashSet<>();
                for (ColumnDTO cDto : dto.getColumns()) {
                        ColumnMeta current = new ColumnMeta();
                        current.setColumn_type(cDto.getType());
                        current.setColumn_pk(new ColumnId(meta.getTable_name(), cDto.getColumnName()));
                        current.setSomeTable(meta);     // AYYYFFF
                        current.setPr_key(dto.getPrimaryKeys().contains(new PrimaryKey(cDto.getColumnName())));

                        FkeyMeta fkMeta = null;
                        ForeignKey fDto = dto.findFkeyByColumn(cDto.getColumnName());
                        if (fDto != null) {
                                fkMeta = new FkeyMeta();
                                fkMeta.setRefColumn(new ColumnId(fDto.getReferenceTable(), fDto.getKey()));
                                fkMeta.setFrom(current);
                        }
                        current.setFkey(fkMeta);
                        current.toCreate();

                        columnMetas.add(current);
                }

                meta.setColumns(columnMetas);
//                System.out.println(meta.toString());
                return meta;
        }
        public static TableDTO metaTabletoDto(TableMeta meta) {
                TableDTO dto =  new TableDTO();
                dto.setName(meta.getTable_name());
                dto.setColumns(metaColumnToDto(meta.getColumns()));
                /*конвертация Внешних ключей*/
                List<ForeignKey> fkeys = new LinkedList<>();
                meta.getColumns().forEach(mcol -> {
                        if (mcol.getFkey() != null) {
                                ForeignKey tmp = new ForeignKey();
                                tmp.setName(mcol.getColumn_pk().getColumn_name());
                                tmp.setReferenceTable(mcol.getFkey().getRefColumn().getTable_name());
                                tmp.setKey(mcol.getFkey().getRefColumn().getColumn_name());
                                fkeys.add(tmp);
                        }
                });
                dto.setForeignKeys(fkeys);

                List<PrimaryKey> pkeys = new LinkedList<>();
                meta.getColumns().forEach(mcol -> {
                        if (mcol.getPr_key()) {
                                pkeys.add(new PrimaryKey(mcol.getColumn_pk().getColumn_name()));
                        }
                });
                dto.setPrimaryKeys(pkeys);
                return dto;
        }
        private static List<ColumnDTO> metaColumnToDto (Set<ColumnMeta> mColumns) {
                System.out.println("-----------METAtoDTO-------------");
                List<ColumnDTO> dtoColumns = new LinkedList<>();
                mColumns.forEach(column ->{
                        ColumnDTO columnDto = new ColumnDTO();
                        columnDto.setColumnName(column.getColumn_pk().getColumn_name());
                        columnDto.setType(column.getColumn_type());
                        dtoColumns.add(columnDto);
                });
                System.out.printf("Meta columns size: %d\nDto columns size: %d\n", mColumns.size(), dtoColumns.size());
                return dtoColumns;
        }

}
