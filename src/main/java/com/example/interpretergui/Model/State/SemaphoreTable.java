package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class SemaphoreTable implements ISemaphoreTable<Integer, Pair<Integer,List<Integer>>>{

    public  synchronized HashMap<Integer, Pair<Integer, List<Integer>>> getMap() {
        return map;
    }

    private HashMap<Integer, Pair<Integer, List<Integer>>> map;


    public SemaphoreTable() {
        map = new HashMap<Integer, Pair<Integer, List<Integer>>>();
    }

    private SemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> dictionary) {
        this.map = dictionary;
    }

    public synchronized Pair<Integer, List<Integer>> lookup(Integer key){
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
    public synchronized void update(Integer key, Pair<Integer,List<Integer>> value) {
        if(!map.containsKey(key)){
            throw new UndefinedVariable("Variable not defined!");
        }
        map.replace(key, value);
    }

    @Override
    public synchronized void add(Integer key, Pair<Integer,List<Integer>> value) {
        if(map.containsKey(key)){
            throw new VariableAlreadyDeclared("Already exists!");
        }
        map.put(key, value);
    }

    @Override
    public synchronized String toString() {
        StringBuilder string = new StringBuilder();
        for (Integer key   : map.keySet()) {
            Pair<Integer,List<Integer>> pair = map.get(key);
            string.append(key.toString());
            string.append(" -> ");
            string.append(pair.toString());
            string.append("\n");
        }
        return string.toString();
    }

//    @Override
//    public ISemaphoreTable<Integer, Pair<Integer,List<Integer>>> deepCopy() {
//        HashMap<Integer, Pair<Integer,List<Integer>> > newMap = new HashMap<Integer, Pair<Integer,List<Integer>>>();
//        for ( Map.Entry<Integer, Pair<Integer,List<Integer>>> entry : map.entrySet()) {
//            Integer key = entry.getKey();
//            Pair<Integer,List<Integer>> value = entry.getValue();
//            newMap.put(key, value);
//        }
//        return new SemaphoreTable(newMap);
//    }
}
