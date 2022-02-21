package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary implements MyIDictionary<String, Value>{

    public HashMap<String, Value> getMap() {
        return map;
    }

    private HashMap<String, Value> map;

    public HashMap<String, Value> getContent() {
        return map;
    }

    public MyDictionary() {
        map = new HashMap<String, Value>();
    }

    private MyDictionary(HashMap<String, Value> dictionary) {
        this.map = dictionary;
    }

    public Value lookup(String key){
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        return map.get(key);
    }

    @Override
    public boolean isDefined(String key) {
        return map.containsKey(key);
    }

    @Override
    public void update(String key, Value value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public void add(String key, Value value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String key   : map.keySet()) {
            Value value = map.get(key);
            string.append(key.toString());
            string.append(" -> ");
            string.append(value.toString());
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public MyIDictionary<String, Value> deepCopy() {
        HashMap<String, Value > newMap = new HashMap<String, Value>();
        for ( Map.Entry<String, Value> entry : map.entrySet()) {
            String key = entry.getKey();
            Value value = entry.getValue();
            newMap.put(key, value.deepCopy());
        }
        return new MyDictionary(newMap);
    }

}
