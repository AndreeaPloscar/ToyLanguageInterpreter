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

public class CountDownStatement implements IStatement{
    private String variable;

    public CountDownStatement(String variable) {
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
                synchronized (CountDownStatement.class){
                if(latchTable.isDefined(index)){
                    if(latchTable.lookup(index) > 0){
                       latchTable.update(index,latchTable.lookup(index)  -1);
                    }
                    state.getOutputList().add(new IntValue(state.getId()));
                }else throw new UndefinedVariable("Variable in CountDown must be in Latch table");}
            }else throw new WrongType("Variable in CountDown must be an integer!");
        }else throw new UndefinedVariable("Variable in CountDown not declared!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CountDownStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("CountDown: variable must be mapped to a number");

    }

    @Override
    public String toString(){
        return "countDown(" + variable + ")";
    }
}
