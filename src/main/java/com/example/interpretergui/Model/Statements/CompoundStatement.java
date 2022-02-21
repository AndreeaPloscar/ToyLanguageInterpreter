package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.MyIStack;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(),second.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString() + " ";
    }
}
