package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;

public class AssignStatement implements IStatement {

    private String id;
    private IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
//        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String,Value> symTbl= state.getSymbolsTable();
        if (symTbl.isDefined(id))
    {
            Value val = expression.evaluate(symTbl, state.getHeap());
            IType typId= (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
            {
                symTbl.update(id, val);
            }
            else throw new WrongType("Declared type of variable " + id + " and type of " +
                "the assigned expression do not match!");
    }
    else throw new UndefinedVariable("The used variable" +id + " was not declared before");
            return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id,expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(id);
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(typExp))
            return typeEnvironment;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return id+"="+ expression.toString();
    }
}
