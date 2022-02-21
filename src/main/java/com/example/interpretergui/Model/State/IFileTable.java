package com.example.interpretergui.Model.State;

import java.util.HashMap;

public interface IFileTable <K,V>{
    V lookup(K key);
    boolean isDefined(K key);
    void update(K key,V value);
    void add(K key, V value);
    String toString();
    void remove(K key);
    HashMap<K, V> getMap();
}
