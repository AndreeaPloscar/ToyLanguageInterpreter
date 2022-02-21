package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.State.IFileTable;

import java.util.HashMap;

public class FileTable<K,V> implements IFileTable<K,V> {

    private HashMap<K,V> map;

    public HashMap<K, V> getMap() {
        return map;
    }

    public FileTable() {
        map = new HashMap<K,V>();
    }

    private FileTable(HashMap<K, V> dictionary) {
        this.map = dictionary;
    }

    public V lookup(K key){
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public void add(K key, V value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }

    @Override
    public String toString() {
        String string = "";
        for (K key   : map.keySet()) {
            V value = map.get(key);
            string += key.toString();
            string += " -> ";
            string += value.toString();
            string += "\n";
        }
        return string;
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }
}
