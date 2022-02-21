package com.example.interpretergui.Model.State;

import java.util.Map;

public interface ISemaphoreTable<K,V> {
    V lookup(K key);
    boolean isDefined(K key);
    void update(K key,V value);
    void add(K key, V value);
    String toString();
    //public HashMap<K, V> getContent();
    //ISemaphoreTable<K,V> deepCopy();
    Map<K,V> getMap();
}
