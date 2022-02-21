package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.ILatchTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;

public class AwaitCountDownStatement implements IStatement{
    private String variable;

    public AwaitCountDownStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        ILatchTable<Integer, Integer> latchTable = state.getLatchTable();
        if(symTbl.isDefined(variable)){
            Value value = symTbl.lookup(variable);
            if(value.getType().equals(new IntType())){
                Integer index = ((IntValue)value).getValue();
                synchronized (AwaitCountDownStatement.class){
                if(latchTable.isDefined(index)){
                    if(latchTable.lookup(index) != 0){
                        state.getStack().push(this);
                    }
                }else throw new UndefinedVariable("Variable in await must be in Latch table");}
            }else throw new WrongType("Variable in Await must be an integer!");
        }else throw new UndefinedVariable("Variable in await not declared!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitCountDownStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Await: variable must be mapped to a number");

    }

    @Override
    public String toString(){
        return "await(" + variable + ")";
    }
}
