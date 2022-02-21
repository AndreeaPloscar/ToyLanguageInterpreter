package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.VariableAlreadyDeclared;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;

public class VariableDeclarationStatement implements IStatement{
    private String name;
    private IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> table = state.getSymbolsTable();
        if (table.isDefined(name)){
            throw new VariableAlreadyDeclared("Cannot declare same variable twice!");
        }
        else{
            table.add(name, type.getDefaultValue());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(name,type.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.add(name,type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
