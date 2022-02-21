package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IType;

public class ConditionalAssignmentStatement implements IStatement{

    private String variable;
    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;

    public ConditionalAssignmentStatement(String variable, IExpression exp1, IExpression exp2, IExpression exp3) {
        this.variable = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement transformed = (new IfStatement(exp1, new AssignStatement(variable, exp2), new AssignStatement(variable, exp3)));
        state.getStack().push(transformed);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(variable, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typExp = exp1.typeCheck(typeEnvironment);
        IType varType = typeEnvironment.lookup(variable);
        IType type1 = exp2.typeCheck(typeEnvironment);
        IType type2 = exp3.typeCheck(typeEnvironment);
        if (typExp.equals(new BoolType())) {
           if(varType.equals(type1) && varType.equals(type2))
            return typeEnvironment;
           else
               throw new WrongType("Expressions don't have the correct types!");
        }
        else
            throw new WrongType("The condition does not evaluate to bool");
    }

    @Override
    public String toString(){
        return variable + "=(" + exp1.toString() + ")?" + exp2.toString() + ":" + exp3.toString();
    }
}
