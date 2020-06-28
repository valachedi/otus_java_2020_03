package hw08.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hw08.components.ValueSerializatorAdapter;

public class MyGson {
  public String toJson(Object object) {
    if(object == null) {
      return "null";
    }

    List<String> resultParts = new ArrayList<String>();
    var valueSerializator = new ValueSerializatorAdapter();

    for(Field field : object.getClass().getDeclaredFields()) {
      if (Modifier.isFinal(field.getModifiers())) {
        continue;
      }

      try {
        field.setAccessible(true);
        Object fieldValue = field.get(object);

        if(fieldValue != null) {
          resultParts.add('"'+field.getName() + "\":" + valueSerializator.serialize(fieldValue));
        }
      } catch(IllegalAccessException e) {
        throw new RuntimeException("unable to get value of " + field.getName());
      }
    }

    return "{" + resultParts.stream().collect(Collectors.joining(",")) + "}";
  }
}
