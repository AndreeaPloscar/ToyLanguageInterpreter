package com.example.interpretergui.Model.State;

import java.util.Map;

public interface ILockTable <K,V>{
    V lookup(K key);
    boolean isDefined(K key);
    void update(K key,V value);
    void add(K key, V value);
    String toString();
   // ILockTable<K,V> deepCopy();
    Map<K,V> getMap();
}
