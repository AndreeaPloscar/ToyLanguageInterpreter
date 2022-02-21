package com.example.interpretergui.Model.Expressions;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class ReadHeapExpression implements IExpression{

    IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolsTable, IHeap<Integer, Value> heap) throws MyException {
        Value value = expression.evaluate(SymbolsTable, heap);
        if (value.getType() instanceof RefType){
            RefValue refValue = (RefValue) value;
            int address = refValue.getAddress();
            if (heap.find(address)){
                return heap.get(address);
            }else{
                throw new UndefinedVariable("Variable not defined");
            }
        }else {
            throw new WrongType("Wrong type");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typ=expression.typeCheck(typeEnvironment);
        if (typ instanceof RefType) {
            RefType referenceType =(RefType) typ;
            return referenceType.getInner();
        }
        else throw new MyException("The readHeap argument is not a Ref Type");
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }
    @Override
    public String toString() {
        return "ReadHeap(" + expression.toString() + ")";
    }
}
