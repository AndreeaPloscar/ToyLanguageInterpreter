package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.Expressions.RelationalExpression;
import com.example.interpretergui.Model.Expressions.VariableExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;

public class ForStatement implements IStatement {
    private IExpression initialValue;
    private IExpression condition;
    private IExpression increment;
    private IStatement statement;
    private String variableName;

    public ForStatement(IExpression initialValue, IExpression condition, IExpression increment, IStatement statement, String variableName) {
        this.initialValue = initialValue;
        this.condition = condition;
        this.increment = increment;
        this.statement = statement;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        IStatement transformed = new CompoundStatement(new VariableDeclarationStatement(variableName, new IntType()),
                new CompoundStatement(new AssignStatement(variableName, initialValue),
                        new WhileStatement(new RelationalExpression(new VariableExpression(variableName), condition,1),
                                new CompoundStatement(statement, new AssignStatement(variableName, increment)))));
        state.getStack().push(transformed);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement( initialValue.deepCopy(),  condition.deepCopy(),  increment.deepCopy(),  statement.deepCopy(),  variableName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        MyIDictionary<String, IType> newEnv = typeEnvironment.deepCopy();
        newEnv.add("v", new IntType());
        IType typExp1 = initialValue.typeCheck(newEnv);
        IType typExp2 = condition.typeCheck(newEnv);
        IType typExp3 = increment.typeCheck(newEnv);
        if (typExp1.equals(new IntType()) && typExp2.equals(new IntType()) && typExp3.equals(new IntType())) {
            statement.typeCheck(newEnv.deepCopy());
          return typeEnvironment;
        }
        else
            throw new WrongType("For: Expressions must evaluate to Integer");

    }

    @Override
    public String toString() {
        return "for ("+variableName+"="+initialValue.toString()+";"+variableName+"<"+condition.toString()+";"+increment.toString()+") " +
                "{ "+statement.toString()+" }";
    }
}
