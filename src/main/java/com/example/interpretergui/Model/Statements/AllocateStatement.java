package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class AllocateStatement implements IStatement{

    String variableName;
    IExpression expression;

    public AllocateStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        if (symTbl.isDefined(variableName))
        {
            IType type= symTbl.lookup(variableName).getType();
            if (type instanceof RefType)
            {
               Value value =  expression.evaluate(symTbl, state.getHeap());
                if( value.getType().equals(((RefType) type).getInner())){
                    Integer address = state.getHeap().add(value);
                    RefValue new_ref_value = new RefValue(address, ((RefType) type).getInner());
                    symTbl.update(variableName, new_ref_value);
                }
                else throw new WrongType("Wrong type");
            }
            else throw new WrongType("Wrong type");
        }
        else throw new UndefinedVariable("Variable is undefined");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AllocateStatement(variableName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variableName);
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(new RefType(typExp)))
            return typeEnvironment;
        else
            throw new WrongType("\"NEW stmt: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new("+ variableName+ "," +expression.toString()+")";
    }
}
