package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;

public class RelationalExpression implements IExpression{

    private IExpression firstExpression;
    private IExpression secondExpression;
    private int operator;

    public RelationalExpression(IExpression firstExpression, IExpression secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolsTable, IHeap<Integer, Value> heap) throws MyException {
        Value value1, value2;
        value1 = firstExpression.evaluate(SymbolsTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = secondExpression.evaluate(SymbolsTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int int1, int2;
                int1 = intValue1.getValue();
                int2 = intValue2.getValue();
                if (operator == 1) return new BoolValue(int1 < int2);
                else if (operator == 2) return new BoolValue(int1 <= int2);
                    else if (operator == 3) return new BoolValue(int1 == int2);
                        else if (operator == 4) return new BoolValue(int1!=int2);
                            else if (operator == 5) return new BoolValue(int1>int2);
                    return new BoolValue(int1>=int2);
            } else
                throw new WrongType("Second operand is not an integer!");
        } else
            throw new WrongType("First operand is not an integer!");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typ1, typ2;
        typ1 = firstExpression.typeCheck(typeEnvironment);
        typ2 = secondExpression.typeCheck(typeEnvironment);

        if (typ1.equals(new IntType()))
        {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new WrongType("Second operand is not an integer");
        }else
            throw new WrongType("First operand is not an integer");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(firstExpression.deepCopy(), secondExpression.deepCopy(), operator);
    }

    @Override
    public String toString(){
        String stringOperator;
        if (operator == 1) stringOperator = "<";
        else if (operator == 2) stringOperator = "<=";
        else if (operator == 3) stringOperator = "==";
        else if (operator == 4) stringOperator = "!=";
        else if (operator == 5) stringOperator = ">";
        else stringOperator = ">=";
        return firstExpression.toString() + stringOperator + secondExpression.toString();
    }
}
