package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.State.*;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{

    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();
        MyIList<Value> output = state.getOutputList();
        MyIDictionary<String, Value> symTable = new MyDictionary();
        MyIStack<IStatement> stack = new MyStack<>();
        ProgramState program = new  ProgramState(stack, symTable, output, fileTable, heap, state.getBarrierTable(),state.getLatchTable(), state.getLockTable(), state.getSemTable(), state.getProcTable(),statement);
        program.setStackSymTables(state.cloneStack());
        return program;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork("+statement.toString()+")";
    }
}
