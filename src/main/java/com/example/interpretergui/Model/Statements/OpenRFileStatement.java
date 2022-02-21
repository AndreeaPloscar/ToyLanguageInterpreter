package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.FileException;
import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{

    IExpression expression;

    public OpenRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if(value.getType().equals(new StringType())){
            StringValue valueString = (StringValue) value;
            if(state.getFileTable().isDefined(valueString)){
                throw new VariableAlreadyDeclared("already defined");
            }
            try{
            BufferedReader buff = new BufferedReader(new FileReader(valueString.getValue()));
            state.getFileTable().add(valueString, buff);
            }catch (IOException exc){
                throw new FileException(exc.getMessage());
            }
        }else throw new WrongType("wrong expression type!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = expression.typeCheck(typeEnvironment);
        if (type.equals(new StringType()))
            return typeEnvironment;
        else throw new WrongType("Open file not string");
    }

    @Override
    public String toString() {
        return "openFile("+expression.toString()+")";
    }
}
