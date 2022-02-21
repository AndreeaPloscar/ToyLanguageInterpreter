package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class LogicExpression implements IExpression {
    private IExpression firstExpression;
    private IExpression secondExpression;
    private int operator;

    public LogicExpression(IExpression firstExpression, IExpression secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolsTable, IHeap<Integer, Value> heap) throws MyException {
        Value value1, value2;
        value1 = firstExpression.evaluate(SymbolsTable, heap);
        if (value1.getType().equals(new BoolType())) {
            value2 = secondExpression.evaluate(SymbolsTable, heap);
            if (value2.getType().equals(new BoolType())) {
                BoolValue boolValue1 = (BoolValue) value1;
                BoolValue boolValue2 = (BoolValue) value2;
                boolean bool1, bool2;
                bool1 = boolValue1.getValue();
                bool2 = boolValue2.getValue();
                if (operator == 1) return new BoolValue(bool1 & bool2);
                else
                     return new BoolValue(bool1 | bool2);
            } else
                throw new WrongType("Second operand is not a boolean!");
        } else
            throw new WrongType("First operand is not a boolean!");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typ1, typ2;
        typ1 = firstExpression.typeCheck(typeEnvironment);
        typ2 = secondExpression.typeCheck(typeEnvironment);

        if (typ1.equals(new BoolType()))
        {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new WrongType("Second operand is not a boolean");
        }else
            throw new WrongType("First operand is not a boolean");
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(firstExpression.deepCopy(), secondExpression.deepCopy(), operator);
    }

    @Override
    public String toString() {
        String operatorString = "";
        switch (operator){
            case 1:
                operatorString = "and";
                break;
            case 2:
                operatorString = "or";
                break;
        }
        return firstExpression.toString() + " " + operatorString + " " + secondExpression.toString();
    }
}
