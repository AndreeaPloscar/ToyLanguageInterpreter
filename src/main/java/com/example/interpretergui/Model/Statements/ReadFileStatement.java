package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.FileException;
import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.IFileTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    IExpression exp;
    String varName;

    public ReadFileStatement(IExpression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> table = state.getSymbolsTable();
        if(table.isDefined(varName)){
            Value value = table.lookup(varName);
            if(value.getType().equals(new IntType())){
                Value val = exp.evaluate(table, state.getHeap());
                if(val.getType().equals(new StringType())){
                    StringValue string_value = (StringValue) val;
                    IFileTable<StringValue, BufferedReader> file_table = state.getFileTable();
                    if(file_table.isDefined(string_value)){
                        BufferedReader buff = file_table.lookup(string_value);
                        try{
                        String line = buff.readLine();
                        int int_value;
                        if(line == null){
                            int_value = 0;
                        }
                        else{
                            line = line.replace("<NL>", "");
                        int_value = Integer.parseInt(line);
                        }
                        IntValue updated = new IntValue(int_value);
                        table.update(varName,updated);
                        }catch (IOException exc){
                            throw new FileException(exc.getMessage());
                        }
                    }else{
                        throw new UndefinedVariable("not defined");
                    }
                }else {
                    throw new WrongType("wrong type");
                }
            }else{
                throw new WrongType("wrong type");
            }
        }else{
            throw new UndefinedVariable("not defined");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(varName);
        IType typExp = exp.typeCheck(typeEnvironment);
        if (typeVar.equals(new IntType()))
            if (typExp.equals(new StringType()))
                return typeEnvironment;
            else
                throw new WrongType("Read file wrong type");
        else
            throw new WrongType("Read file wrong type");

    }

    @Override
    public String toString() {
        return "readFile("+exp.toString()+","+varName.toString()+")";
    }
}
