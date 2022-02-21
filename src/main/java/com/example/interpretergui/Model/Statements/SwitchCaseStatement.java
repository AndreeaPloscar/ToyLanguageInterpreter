package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.Expressions.RelationalExpression;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;

public class SwitchCaseStatement implements IStatement{

    private IExpression mainExpression;
    private IExpression expression1;
    private IExpression expression2;
    private IStatement statement1;
    private IStatement statement2;
    private IStatement statementD;

    public SwitchCaseStatement(IExpression mainExpression, IExpression expression1, IExpression expression2, IStatement statement1, IStatement statement2, IStatement statementD) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statementD = statementD;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        RelationalExpression exp1 = new RelationalExpression(mainExpression, expression1, 3);
        RelationalExpression exp2 = new RelationalExpression(mainExpression, expression2, 3);
        IStatement transformed = new IfStatement(exp1, statement1, new IfStatement(exp2, statement2, statementD));
        state.getStack().push(transformed);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchCaseStatement( mainExpression.deepCopy(),  expression1.deepCopy(),  expression2.deepCopy(),  statement1.deepCopy(),  statement2.deepCopy(),  statementD.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typExp1 = expression1.typeCheck(typeEnvironment);
        IType typExp2 = expression1.typeCheck(typeEnvironment);
        IType typExp3 = expression1.typeCheck(typeEnvironment);
        if (typExp1.equals(typExp2) && typExp2.equals(typExp3)) {
            statement1.typeCheck(typeEnvironment.deepCopy());
            statement2.typeCheck(typeEnvironment.deepCopy());
            statementD.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else
            throw new WrongType("CondAssignment: Types of expressions are not equal");


    }

    @Override
    public String toString(){
        return "switch(" + mainExpression.toString() + ") (case (" + expression1.toString() + "): " + statement1.toString() + ") " + "(case (" + expression2.toString() + "): " + statement2.toString() + ")" + " (default: " + statementD.toString()+")"  ;
    }
}
