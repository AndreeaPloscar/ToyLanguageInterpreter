package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Values.Value;

import java.util.Map;

public interface IHeap<K, V>{
    K add(V value);
    V get(K key);
    boolean find(K key);
    void update(K key, V val);
    public Map<K, V> getContent();
    void setContent(Map<K, V> map);
    public Map<Integer, Value> getMap();
}
