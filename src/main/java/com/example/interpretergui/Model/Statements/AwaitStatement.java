package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.IBarrierTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.util.List;

public class AwaitStatement implements IStatement{

    private String variable;

    public AwaitStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();

        if(symTbl.isDefined(variable)){
            Value value = symTbl.lookup(variable);
            if(value.getType().equals(new IntType())){
                IntValue intVal = (IntValue) value;
                Integer index = intVal.getValue();

                IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
                synchronized (AwaitStatement.class){
                if(barrierTable.isDefined(index)){
                    Pair<Integer, List<Integer>> pair = barrierTable.lookup(index);
                    int NL = pair.getValue().size();
                    Integer N1 = pair.getKey();
                    if (N1 > NL){
                        if (!pair.getValue().contains(state.getId())) {
                            pair.getValue().add(state.getId());
                        }
                        state.getStack().push(this);
                    }
                }else throw new UndefinedVariable("Variable in Await is not a semaphore!");}
            }else throw new WrongType("Variable in await must be integer!");
        }else throw new UndefinedVariable("Await: Variable not declared!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Await: Variable must be o type integer");

    }

    @Override
    public String toString(){
        return "await(" + variable + ")";
    }
}
