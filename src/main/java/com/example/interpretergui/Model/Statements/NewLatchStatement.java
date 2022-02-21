package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.ILatchTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;

public class NewLatchStatement implements IStatement{

    private String variable;
    private IExpression expression;
    private static Integer newFreeAddress = 0;

    public NewLatchStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        IHeap<Integer, Value> heap = state.getHeap();
        Value value = expression.evaluate(symTbl, heap);
        if(value.getType().equals(new IntType())){
            IntValue val = (IntValue) value;
            Integer number = val.getValue();
            ILatchTable<Integer, Integer> latchTable = state.getLatchTable();
            latchTable.add(getNewFreeLocation(), number);
            if(symTbl.isDefined(variable)){
                Value varValue = symTbl.lookup(variable);
                if(varValue.getType().equals(new IntType())){
                    symTbl.update(variable, new IntValue(getNewFreeLocation()));
                }else throw new WrongType("Type of variable in newLatch must be integer!");
            }else throw new UndefinedVariable("Variable in newLatch not defined!");

        }else throw new WrongType("Type of expression in NewLatch must be integer!");
        incrementLocation();
        return null;
    }

    public static synchronized Integer getNewFreeLocation() {
        return newFreeAddress;
    }

    public static synchronized void incrementLocation(){
        newFreeAddress += 1;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(variable, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(new IntType()) && typExp.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("newLatch: types of variable and expression must be integers!");

    }

    @Override
    public String toString(){
        return "newLatch(" + variable + "," + expression.toString() + ")";
    }
}
