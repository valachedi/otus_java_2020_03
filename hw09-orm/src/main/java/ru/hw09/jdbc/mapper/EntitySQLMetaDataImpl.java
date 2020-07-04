package hw09.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.TreeSet;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
  public EntityClassMetaData metadata;

  public EntitySQLMetaDataImpl(EntityClassMetaData metadata) {
    this.metadata = metadata;
  }

  public String getSelectAllSql() {
    return "SELECT * FROM " + getTableName();
  }

  public String getSelectByIdSql() {
    return "SELECT * FROM " + getTableName() + " WHERE " + metadata.getIdField().getName() + " = ?";
  }

  public String getInsertSql() {
    String fieldNamesSql = "";
    String placeholdersSql = "";

    for(Field field : (TreeSet<Field>)metadata.getFieldsWithoutId()) {
      if(!fieldNamesSql.isEmpty()) {
        fieldNamesSql += ',';
      }

      if(!placeholdersSql.isEmpty()) {
        placeholdersSql += ',';
      }

      fieldNamesSql += field.getName();
      placeholdersSql += '?';
    }

    return "INSERT INTO " + getTableName() +
            " (" + fieldNamesSql + ") " +
            " VALUES (" + placeholdersSql + ");";
  }

  public String getUpdateSql() {
    String sqlSet = "";

    for(Field field : (TreeSet<Field>)metadata.getFieldsWithoutId()) {
      if(!sqlSet.isEmpty()) {
        sqlSet += ',';
      }

      sqlSet += field.getName() + " = ?";
    }

    return "UPDATE " + getTableName() + " SET " + sqlSet + " WHERE " + metadata.getIdField().getName() + " = ?;";
  }

  private String getTableName() {
    return metadata.getName();
  }
}
