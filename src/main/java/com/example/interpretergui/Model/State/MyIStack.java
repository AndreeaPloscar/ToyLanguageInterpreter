package com.example.interpretergui.Model.State;

import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    String toString();
//    MyIStack<T> deepCopy();
    Stack<T> getStack();
}
