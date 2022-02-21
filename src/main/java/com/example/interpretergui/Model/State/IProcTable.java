package com.example.interpretergui.Model.State;

import java.util.Map;

public interface IProcTable <K,V>{
    V lookup(K key);
    boolean isDefined(K key);
    void update(K key,V value);
    void add(K key, V value);
    String toString();
    IProcTable<K,V> deepCopy();
    Map<K,V> getMap();
}
