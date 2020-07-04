package hw09.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import hw09.core.sessionmanager.SessionManager;
import hw09.jdbc.sessionmanager.SessionManagerJdbc;
import hw09.jdbc.DbExecutor;
import hw09.jdbc.DbExecutorImpl;

public class JdbcMapperImpl<T extends Object> implements JdbcMapper<T> {
  private static final Map<Class, EntityClassMetaDataImpl> metadataMap = new HashMap<Class, EntityClassMetaDataImpl>();
  private static final Map<Class, EntitySQLMetaDataImpl> metasqlMap = new HashMap<Class, EntitySQLMetaDataImpl>();

  private static final int[] SQL_TYPES_TO_INT = new int[]{
    Types.BIT,
    Types.TINYINT,
    Types.SMALLINT,
    Types.INTEGER,
    Types.BIGINT
  };

  private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;

  public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutorImpl<T> dbExecutor) {
    this.dbExecutor = dbExecutor;
    this.sessionManager = sessionManager;
  }

  @Override
  public int insertOrUpdate(T objectData) {
    var meta = getMetaDataByObject(objectData.getClass());
    var sql = getMetaSqlByObject(objectData.getClass());

    try {
      return (int)meta.getIdField().get(objectData) == 0
              ? insert(objectData)
              : update(objectData);
    } catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(T objectData) {
    var meta = getMetaDataByObject(objectData.getClass());
    var sql = getMetaSqlByObject(objectData.getClass());

    List<Object> fieldValues = new ArrayList<Object>(3);

    for(Field field : (TreeSet<Field>)meta.getFieldsWithoutId()) {
      field.setAccessible(true);

      try {
        fieldValues.add(field.get((T)objectData));
      } catch(IllegalAccessException e) {
        logger.error(e.getMessage());
        throw new RuntimeException(e);
      }
    }

    try {
      fieldValues.add(meta.getIdField().get(objectData));
    } catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    try {
      return dbExecutor.executeInsert(getConnection(),
                                      sql.getUpdateSql(),
                                      fieldValues);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public int insert(T objectData) {
    var meta = getMetaDataByObject(objectData.getClass());
    var sql = getMetaSqlByObject(objectData.getClass());

    List<Object> fieldValues = new ArrayList<Object>(3);

    for(Field field : (TreeSet<Field>)meta.getFieldsWithoutId()) {
      field.setAccessible(true);

      try {
        fieldValues.add(field.get((T)objectData));
      } catch(IllegalAccessException e) {
        logger.error(e.getMessage());
        throw new RuntimeException(e);
      }
    }

    try {
      return dbExecutor.executeInsert(getConnection(),
                                      sql.getInsertSql(),
                                      fieldValues);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Optional<T> findById(int id, Class<T> clazz) {
    var meta = getMetaDataByObject(clazz);
    var sql = getMetaSqlByObject(clazz);

    try {
        return dbExecutor.executeSelect(
                getConnection(),
                sql.getSelectByIdSql(),
                id,
                rs -> {
                  try {
                    if (rs.next()) {
                      ArrayList<Object> initParams = new ArrayList<Object>(3);
                      ResultSetMetaData rsmd = rs.getMetaData();

                      for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                        if(isSqlIntType(rsmd.getColumnType(i))) {
                          initParams.add(rs.getInt(i));
                        } else {
                          initParams.add(rs.getString(i));
                        }
                      }

                      return (T)meta.getConstructor().newInstance(initParams.toArray());
                    }
                  } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                  } catch(Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                  }

                  return null;
                });
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }

    return Optional.empty();
  }

  private Connection getConnection() {
      return sessionManager.getCurrentSession().getConnection();
  }

  private boolean isSqlIntType(int columnTypeId) {
    for (final int i : SQL_TYPES_TO_INT) {
        if (i == columnTypeId) {
            return true;
        }
    }

    return false;
  }

  private EntityClassMetaDataImpl getMetaDataByObject(Class clazz) {
    if(!metadataMap.containsKey(clazz)) {
      metadataMap.put(clazz, new EntityClassMetaDataImpl(clazz));
    }

    return metadataMap.get(clazz);
  }

  private EntitySQLMetaDataImpl getMetaSqlByObject(Class clazz) {
    if(!metasqlMap.containsKey(clazz)) {
      metasqlMap.put(clazz, new EntitySQLMetaDataImpl(getMetaDataByObject(clazz)));
    }

    return metasqlMap.get(clazz);
  }
}
