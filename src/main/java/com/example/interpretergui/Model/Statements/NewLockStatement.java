package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.State.ILockTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;

public class NewLockStatement implements IStatement{

    private String variable;
    private static Integer newFreeLocation = 0;

    public NewLockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        ILockTable<Integer,Integer> lockTable = state.getLockTable();
        lockTable.add(getNewFreeLocation(), -1);
        if (symTbl.isDefined(variable))
        {   IType typId= (symTbl.lookup(variable)).getType();
            if (typId.equals(new IntType()))
            {
                symTbl.update(variable, new IntValue(newFreeLocation));
            } else throw new WrongType("newLock: wrong type");
        }
        else throw new UndefinedVariable("The used variable" +variable + " was not declared before");
        incrementLocation();
        return null;
    }

    public static synchronized Integer getNewFreeLocation() {
        return newFreeLocation;
    }

    public static synchronized void incrementLocation(){
        newFreeLocation += 1;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);

        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("New lock variable must be integer!");

    }

    @Override
    public String toString(){
        return "newLock(" + variable + ")";
    }
}
