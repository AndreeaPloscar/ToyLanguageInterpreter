package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap<Integer, Value> {
    private Map<Integer, Value> map;
    private Integer newFreeLocation;

    public Heap() {
        map = new HashMap<Integer, Value>();
        newFreeLocation = 1;
    }

    public Map<Integer, Value> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, Value> map) {
        this.map = map;
    }

    public Integer add(Value val){
        Integer old_location = newFreeLocation;
        map.put( newFreeLocation, val);
        Integer pos = 1;
        while (map.containsKey(pos))
        {
            pos++;
        }
        newFreeLocation = pos;
        return  old_location;
    }

    public Value get(Integer address){
        return map.get(address);
    }

    @Override
    public boolean find(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public void update(Integer key, Value value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Integer key   : map.keySet()) {
            Value value = map.get(key);
            string.append(key.toString());
            string.append(" -> ");
            string.append(value.toString());
            string.append(" | ");
        }
        return string.toString();
    }

    public Map<Integer, Value> getMap() {
        return map;
    }
}
