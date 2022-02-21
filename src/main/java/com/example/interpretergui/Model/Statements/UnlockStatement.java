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

public class UnlockStatement implements IStatement{
    private String variable;

    public UnlockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        ILockTable<Integer,Integer> lockTable = state.getLockTable();

        if (symTbl.isDefined(variable))
        {   IType typId= (symTbl.lookup(variable)).getType();
            if (typId.equals(new IntType()))
            {
                Value foundIndex = symTbl.lookup(variable);
                IntValue intValue = (IntValue) foundIndex;
                if (lockTable.isDefined(intValue.getValue()))
                {
                    Integer value = lockTable.lookup(intValue.getValue());
                    if(value == state.getId()){
                        lockTable.update(intValue.getValue(), -1);
                    }
                }
                else throw new UndefinedVariable("Index undefined");
            } else throw new WrongType("Unlock: wrong type of variable");
        }
        else throw new UndefinedVariable("The used variable" +variable + " was not declared before");
        return null;
    }


    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);

        if (typeVar.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Unlock variable must be integer!");
    }

    @Override
    public String toString(){
        return "unlock(" + variable + ")";
    }

}
