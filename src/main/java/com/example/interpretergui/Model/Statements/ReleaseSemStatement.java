package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.ISemaphoreTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.util.List;

public class ReleaseSemStatement implements IStatement{
    private String variable;

    public ReleaseSemStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymbolsTable();
        ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable = state.getSemTable();
        if(symTable.isDefined(variable)){
            Value varVal = symTable.lookup(variable);
            if(varVal.getType().equals(new IntType())){
                IntValue intVal = (IntValue) varVal;
                Integer index = intVal.getValue();
                synchronized (ReleaseSemStatement.class){
                if(semTable.isDefined(index)){
                    Pair<Integer, List<Integer>> pair = semTable.lookup(index);
                        if (pair.getValue().contains(state.getId())){
                            pair.getValue().remove((Integer) state.getId());
                        }
                }else throw new UndefinedVariable("Variable in release is not a semaphore!");}
            }else throw new WrongType("Type of variable in release is not integer");
        }else throw new UndefinedVariable("Variable in release is not declared");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReleaseSemStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Release Semaphore: wrong type!");
    }

    @Override
    public String toString(){
        return "release(" + variable + ")";
    }
}
