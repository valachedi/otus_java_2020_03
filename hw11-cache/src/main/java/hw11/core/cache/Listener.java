package hw11.core.cache;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface Listener<K, V> {
    void notify(K key, V value, String action);
}
