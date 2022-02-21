package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;

import java.util.HashMap;

public class LatchTable implements ILatchTable<Integer, Integer>{

    private HashMap<Integer, Integer> map;

    public synchronized HashMap<Integer, Integer> getMap() {
        return map;
    }

    public LatchTable() {
        map = new HashMap<Integer, Integer> ();
    }

    private LatchTable(HashMap<Integer, Integer>  dictionary) {
        this.map = dictionary;
    }

    public synchronized Integer lookup(Integer key){
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        return map.get(key);
    }

    @Override
    public synchronized boolean isDefined(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized void update(Integer key, Integer value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public synchronized void add(Integer key, Integer value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }

    @Override
    public synchronized String toString() {
        StringBuilder string = new StringBuilder();
        for (Integer key   : map.keySet()) {
            Integer value = map.get(key);
            string.append(key.toString());
            string.append(" -> ");
            string.append(value.toString());
            string.append("\n");
        }
        return string.toString();
    }

//    @Override
//    public ILatchTable<Integer, Integer> deepCopy() {
//        HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
//        for ( Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            Integer key = entry.getKey();
//            Integer value = entry.getValue();
//            newMap.put(key, value);
//        }
//        return new LatchTable(newMap);
//    }

}
