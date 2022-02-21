package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.Value;

public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if(value.getType().equals(new BoolType())){
            BoolValue valueBool = (BoolValue) value;
            if(valueBool.getValue()){
                state.getStack().push(thenStatement);
            }else {
                state.getStack().push(elseStatement);
            }
        }else throw new  WrongType("wrong expression type!");
        return null;
    }


    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(),thenStatement.deepCopy(),elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typExp.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnvironment.deepCopy());
            elseStatement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else
            throw new WrongType("The condition of IF has not the type bool");
    }

    @Override
    public String toString() {
        return "(IF("+ expression.toString()+") THEN(" + thenStatement.toString() +")ELSE("+ elseStatement.toString()+"))";
    }
}
