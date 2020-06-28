package hw08.components.serializator;

import java.lang.reflect.Array;
import hw08.components.AbstractSerializator;
import hw08.components.SerializatorInterface;
import hw08.components.ValueSerializatorAdapter;

public class ArraySerializator extends AbstractSerializator {
  public String serialize(Object values) {
    String result = "[";
    SerializatorInterface valueSerializator = new ValueSerializatorAdapter();

    for(int i = 0; i < Array.getLength(values); i++) {
      if(i > 0) {
        result += ',';
      }

      result += valueSerializator.serialize(Array.get(values, i));
    }

    result += ']';

    return result;
  }
}
