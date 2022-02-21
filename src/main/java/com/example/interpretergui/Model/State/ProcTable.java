package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcTable implements IProcTable<String, Pair<List<String>, IStatement>>{

    private HashMap<String,  Pair<List<String>, IStatement>> map;

    public ProcTable() {
        this.map = new HashMap<>();
    }

    public ProcTable(HashMap<String, Pair<List<String>, IStatement>> map) {
        this.map = map;
    }

    @Override
    public Pair<List<String>, IStatement> lookup(String key) {
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
    public void update(String key, Pair<List<String>, IStatement> value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public void add(String key, Pair<List<String>, IStatement> value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String key   : map.keySet()) {
            Pair<List<String>, IStatement> value = map.get(key);
            string.append(key);

            string.append(value.getKey().toString());
            string.append(" { ");
            string.append(value.getValue().toString());
            string.append("}");
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public IProcTable<String, Pair<List<String>, IStatement>> deepCopy() {
        HashMap<String, Pair<List<String>, IStatement> > newMap = new HashMap<>();
        for ( Map.Entry<String,Pair<List<String>, IStatement>> entry : map.entrySet()) {
            String key = entry.getKey();
            Pair<List<String>, IStatement> value = entry.getValue();
            newMap.put(key, value);
        }
        return new ProcTable(newMap);
    }

    @Override
    public Map<String, Pair<List<String>, IStatement>> getMap() {
        return map;
    }


}
