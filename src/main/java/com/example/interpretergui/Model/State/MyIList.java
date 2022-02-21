package com.example.interpretergui.Model.State;

import java.util.ArrayList;

public interface MyIList <T>{
    void add(T value);
    String toString();
//    MyIList<T> deepCopy();
    ArrayList<T> getList();
}
