package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.*;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{

    IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if(value.getType().equals(new StringType())){
            StringValue valueString = (StringValue) value;
            if(!state.getFileTable().isDefined(valueString)){
                throw new UndefinedVariable("not defined");
            }
            try{
                BufferedReader buff = state.getFileTable().lookup(valueString);
                buff.close();
                state.getFileTable().remove(valueString);
            }catch (IOException exc){
                throw new FileException(exc.getMessage());
            }
        }else throw new WrongType("wrong expression type!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = expression.typeCheck(typeEnvironment);
        if (type.equals(new StringType()))
            return typeEnvironment;
        else throw new WrongType("Close file not string");
    }

    @Override
    public String toString() {
        return "closeFile("+expression.toString()+")";
    }

}
