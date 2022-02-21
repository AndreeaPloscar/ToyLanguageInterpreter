package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Values.Value;

import java.util.ArrayList;

public class MyList<T extends Value> implements MyIList<T>{
    private ArrayList<T> list;

    public ArrayList<T> getList() {
        return list;
    }

    public MyList() {
        list = new ArrayList<T>();
    }

    private MyList(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public String toString() {
        String string = "";
        for (T element :list){
            string += element.toString();
            string += "\n";
        }
        return string;
    }

//    @Override
//    public MyIList<T> deepCopy() {
//        ArrayList<T> newList = new ArrayList<T>();
//        for (T elem : list){
//            newList.add((T) elem.deepCopy());
//        }
//        return new MyList<T>(newList);
//    }
}
