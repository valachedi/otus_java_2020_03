package hw08.components.serializator;

import hw08.components.AbstractSerializator;

public class PrimitiveSerializator extends AbstractSerializator {
  public String serialize(Object value) {
    String result;
    Class fieldType = value.getClass();

    if(fieldType.equals(Integer.class)) {
      result = Integer.toString((int)value);
    } else if(fieldType.equals(short.class)) {
      result = Short.toString((short)value);
    } else if(fieldType.equals(long.class)) {
      result = Long.toString((long)value);
    } else if(fieldType.equals(float.class)) {
      result = Float.toString((float)value);
    } else if(fieldType.equals(double.class)) {
      result = Double.toString((double)value);
    } else if(fieldType.equals(boolean.class)) {
      result = (String)value;
    } else if(fieldType.equals(Character.class)) {
      result = quote(value.toString());
    } else if(fieldType.equals(Byte.class)) {
      result = Byte.toString((byte)value);
    } else {
      throw new RuntimeException("unexpected primitive type " + fieldType);
    }

    return result;
  }
}
