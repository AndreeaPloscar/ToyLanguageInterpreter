package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;

public interface IExpression {
    Value evaluate(MyIDictionary<String,Value> SymbolsTable, IHeap<Integer, Value> heap) throws MyException;
    IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException;
    IExpression deepCopy();
}
