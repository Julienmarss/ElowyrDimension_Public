package fr.elowyr.dimension.utils.io.provide;

public interface IProvider<K, V> {

    void provide(K key, V value);

    void remove(K key);

    V get(K key);
}
