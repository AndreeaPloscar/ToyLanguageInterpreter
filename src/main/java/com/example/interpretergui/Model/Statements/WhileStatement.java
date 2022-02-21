package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.MyIStack;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class WhileStatement implements IStatement{
    IExpression expression;
    IStatement statement;

    public WhileStatement(IExpression expression,  IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        MyIStack<IStatement> stack = state.getStack();
        if (value.getType().equals(new BoolType())){
            BoolValue boolVal = (BoolValue) value;
            if(boolVal.getValue()){
                stack.push(this);
                stack.push(statement);
            }
        }else throw new WrongType("Wrong type");

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typExp.equals(new BoolType())) {
            statement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else
            throw new WrongType("The condition of WHILE does not have the type bool");

    }

    @Override
    public String toString() {
        return "while ("+expression.toString()+") {"+statement.toString()+"}";
    }
}
