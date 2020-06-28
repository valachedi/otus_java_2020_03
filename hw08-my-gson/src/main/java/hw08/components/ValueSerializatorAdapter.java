package hw08.components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hw08.components.AbstractSerializator;
import hw08.components.serializator.ArraySerializator;
import hw08.components.serializator.CollectionSerializator;
import hw08.components.serializator.PrimitiveSerializator;

public class ValueSerializatorAdapter extends AbstractSerializator {
  private static SerializatorInterface arraySerializator = new ArraySerializator();
  private static SerializatorInterface collectionSerializator = new CollectionSerializator();
  private static SerializatorInterface primitiveSerializator = new PrimitiveSerializator();

  private static Set<Class<?>> primitiveWrapperTypes;

  public String serialize(Object fieldValue) {
    if(fieldValue == null) {
      return "null";
    }

    String result = null;
    Class fieldType = fieldValue.getClass();

    if(fieldType.isPrimitive() || isWrapperType(fieldType)) {
      result = primitiveSerializator.serialize(fieldValue);
    } else {
      if (fieldType.equals(String.class)) {
        result = quote((String)fieldValue);
      } else if(fieldType.isArray()) {
        result = arraySerializator.serialize(fieldValue);
      } else if(Collection.class.isAssignableFrom(fieldType)) {
        result = collectionSerializator.serialize(fieldValue);
      } else {
        System.out.println("unexpected primitive type " + fieldType.getName());
      }
    }

    return result;
  }

  private static boolean isWrapperType(Class<?> type)
  {
      return getWrapperTypes().contains(type);
  }

  private static Set<Class<?>> getWrapperTypes()
  {
    if(primitiveWrapperTypes == null) {
      primitiveWrapperTypes = new HashSet<Class<?>>();
      primitiveWrapperTypes.add(Boolean.class);
      primitiveWrapperTypes.add(Character.class);
      primitiveWrapperTypes.add(Byte.class);
      primitiveWrapperTypes.add(Short.class);
      primitiveWrapperTypes.add(Integer.class);
      primitiveWrapperTypes.add(Long.class);
      primitiveWrapperTypes.add(Float.class);
      primitiveWrapperTypes.add(Double.class);
    }

    return primitiveWrapperTypes;
  }
}
