package hw11.core.service;

import hw11.core.cache.Listener;
import hw11.core.model.User;

public class DbServiceUserCacheListener<K, V> implements Listener<K, V> {
  public void notify(K id, V value, String action) {
    System.out.println("user cache ["+action+"] event: id="+id+", user="+value);
  }
}
