package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.MyIList;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){ return "print(" + expression.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<Value> output = state.getOutputList();
        output.add(expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        return null;
    }


    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }
}