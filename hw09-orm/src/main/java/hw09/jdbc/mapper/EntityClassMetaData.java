package hw09.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.TreeSet;

public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor();

    Field getIdField();

    TreeSet<Field> getAllFields();

    TreeSet<Field> getFieldsWithoutId();
}
