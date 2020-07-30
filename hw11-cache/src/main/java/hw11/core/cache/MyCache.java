package hw11.core.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache<K, V> {

    private static final String ACTION_NAME_GET = "get";
    private static final String ACTION_NAME_PUT = "put";
    private static final String ACTION_NAME_REMOVE = "remove";

    private final WeakHashMap<K, V> innerCache = new WeakHashMap();
    private final List<Listener> listeners = new ArrayList<Listener>();

    @Override
    public void put(K key, V value) {
        innerCache.put(key, value);
        notifyListeners(key, value, ACTION_NAME_PUT);
    }

    @Override
    public void remove(K key) {
        notifyListeners(key, null, ACTION_NAME_REMOVE);
        innerCache.remove(key);
    }

    @Override
    public V get(K key) {
        var userFromCache = innerCache.get(key);
        notifyListeners(key, userFromCache, ACTION_NAME_GET);
        return userFromCache;
    }

    @Override
    public void addListener(Listener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, String actionName) {
        for(Listener listener : listeners) {
            listener.notify(key, value, actionName);
        }
    }
}
