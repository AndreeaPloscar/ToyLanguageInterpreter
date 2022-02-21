package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.ISemaphoreTable;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CreateSemaphoreStatement implements IStatement{

    private String variable;
    private IExpression expression;
    private static Integer newFreeLocation = 0;

    public CreateSemaphoreStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
       MyIDictionary<String, Value> symTable = state.getSymbolsTable();
       IHeap<Integer, Value> heap = state.getHeap();
       Value expValue =  expression.evaluate(symTable, heap);
       if(expValue.getType().equals(new IntType())){
           IntValue intValue = (IntValue) expValue;
           ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable = state.getSemTable();
           semTable.add(getNewFreeLocation(), new Pair<>(intValue.getValue(), new ArrayList<>()));
           if(symTable.isDefined(variable)){
               IType type = symTable.lookup(variable).getType();
               if(type.equals(new IntType())){
                   symTable.update(variable, new IntValue(getNewFreeLocation()));
               }else throw new WrongType("Variable in CreateSemaphore not Integer!");
           }else throw new UndefinedVariable("Variable in CreateSemaphore undeclared!");
       }else throw new WrongType("Expression in CreateSemaphore does not evaluate to Integer!");
       incrementLocation();
       return null;
    }

    public static synchronized Integer getNewFreeLocation() {
        return newFreeLocation;
    }

    public static synchronized void incrementLocation(){
        newFreeLocation += 1;
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStatement(variable, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(variable);
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(new IntType()) && typExp.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("Create Semaphore: wrong types!");
    }

    @Override
    public String toString(){
        return "createSemaphore(" + variable + ", " + expression.toString() + ")";
    }

}
