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

public class AcquireSemStatement implements IStatement{
    private String variable;

    public AcquireSemStatement(String variable) {
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
                synchronized (AcquireSemStatement.class){
                if(semTable.isDefined(index)){
                    Pair<Integer, List<Integer>> pair = semTable.lookup(index);
                    Integer NL = pair.getValue().size();
                    Integer N1 = pair.getKey();
                    if(N1>NL){
                        if (!pair.getValue().contains(state.getId())){
                            pair.getValue().add(state.getId());
                        }
                    }else{
                        state.getStack().push(this);
                    }
                }else throw new UndefinedVariable("Variable in acquire is not a semaphore!");}
            }else throw new WrongType("Type of variable in Acquire is not integer");
        }else throw new UndefinedVariable("Variable in Acquire is not declared");
        return null;
    }


    @Override
    public IStatement deepCopy() {
        return new AcquireSemStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Acquire Semaphore: wrong type!");
    }

    @Override
    public String toString(){
        return "acquire(" + variable + ")";
    }
}
