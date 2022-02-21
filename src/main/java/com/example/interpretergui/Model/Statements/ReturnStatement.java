package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;

public class ReturnStatement implements IStatement{

    public ReturnStatement() {}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getStackSymTables().pop();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "return";
    }
}
