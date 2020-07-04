package hw09.jdbc.mapper;

import java.util.Optional;

public interface JdbcMapper<T> {
    int insert(T objectData);
    int update(T objectData);
    int insertOrUpdate(T objectData);
    Optional<T> findById(int id, Class<T> clazz);
}
