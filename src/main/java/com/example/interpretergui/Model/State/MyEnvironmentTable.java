package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.Types.IType;

import java.util.HashMap;
import java.util.Map;

public class MyEnvironmentTable implements MyIDictionary<String, IType> {
    private HashMap<String, IType> map;

    public HashMap<String, IType> getContent() {
        return map;
    }

    public HashMap<String, IType> getMap() {
        return map;
    }

    public MyEnvironmentTable() {
        map = new HashMap<String,IType>();
    }

    private MyEnvironmentTable(HashMap<String,IType> dictionary) {
        this.map = dictionary;
    }

    public IType lookup(String key){
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
    public void update(String key, IType value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public void add(String key, IType value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String key   : map.keySet()) {
            IType value = map.get(key);
            string.append(key.toString());
            string.append(" -> ");
            string.append(value.toString());
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public MyIDictionary<String, IType> deepCopy() {
        HashMap<String,IType > newMap = new HashMap<String,IType>();
        for ( Map.Entry<String, IType> entry : map.entrySet()) {
            String key = entry.getKey();
            IType value = entry.getValue();
            newMap.put(key, value.deepCopy());
        }
        return new MyEnvironmentTable(newMap);
    }
}
