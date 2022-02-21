package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;

public class VariableExpression implements IExpression {
    private String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolsTable, IHeap<Integer, Value> heap) throws MyException {
        return SymbolsTable.lookup(name);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment.lookup(name);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
