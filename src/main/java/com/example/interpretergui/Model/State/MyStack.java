package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.EmptyCollection;
import com.example.interpretergui.Model.Statements.IStatement;

import java.util.Stack;

public class MyStack<T extends IStatement> implements MyIStack<T>{
    private Stack<T> stack;

    public MyStack(Stack<T> stack) {
        this.stack = stack;
    }

    public Stack<T> getStack() {
        return stack;
    }

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() {
        if(stack.isEmpty())
            throw new EmptyCollection("Stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        Stack<T> copy;
        StringBuilder string = new StringBuilder();
        copy = (Stack<T>) stack.clone();
        while( ! copy.isEmpty()){
           T element = copy.pop();
           string.append(element.toString());
           string.append("\n");
        }
        return string.toString();
    }
//
//    @Override
//    public MyIStack<T> deepCopy() {
//        Stack<T> newStack = new Stack<T>();
//        Enumeration<T> enumeration = stack.elements();
//        while (enumeration.hasMoreElements()) {
//            newStack.push((T) enumeration.nextElement().deepCopy());
//        }
//        return new MyStack<T>(newStack);
//    }
}
