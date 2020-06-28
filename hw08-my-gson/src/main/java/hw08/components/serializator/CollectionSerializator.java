package hw08.components.serializator;

import java.util.Collection;
import hw08.components.AbstractSerializator;
import hw08.components.SerializatorInterface;
import hw08.components.ValueSerializatorAdapter;

public class CollectionSerializator extends AbstractSerializator {
  public String serialize(Object collection) {
    String result = "";
    SerializatorInterface valueSerializator = new ValueSerializatorAdapter();

    for(Object eachElement : (Collection)collection) {
      if(!result.isEmpty()) {
        result += ',';
      }

      result += valueSerializator.serialize(eachElement);
    }

    return '[' + result + ']';
  }
}
