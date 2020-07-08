package hw09.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.TreeSet;

import hw09.annotation.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private static Class clazz;
    private Constructor constructor = null;
    private Field idField = null;
    private TreeSet<Field> allFields = null;
    private TreeSet<Field> fieldsWithoutId = null;

    public EntityClassMetaDataImpl(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            Constructor[] constructors = clazz.getConstructors();

            if (constructors.length != 1) {
                throw new RuntimeException("one single constructor required for ORM model, check " + getName() + " model");
            }

            constructor = constructors[0];
        }

        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    idField = field;
                }
            }

            if (idField == null) {
                throw new RuntimeException("unable to get @Id field for " + getName());
            }
        }

        return idField;
    }

    @Override
    public TreeSet<Field> getAllFields() {
        if (allFields == null) {
            allFields = new TreeSet<Field>(new FieldComparator());

            for (Field field : clazz.getDeclaredFields()) {
                allFields.add(field);
            }
        }

        return allFields;
    }

    @Override
    public TreeSet<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = new TreeSet<Field>(new FieldComparator());

            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Id.class)) {
                    fieldsWithoutId.add(field);
                }
            }
        }

        return fieldsWithoutId;
    }

    private static class FieldComparator implements Comparator<Field> {
        public int compare(Field f1, Field f2) {
            return (f1.getName().compareTo(f2.getName()));
        }
    }
}
