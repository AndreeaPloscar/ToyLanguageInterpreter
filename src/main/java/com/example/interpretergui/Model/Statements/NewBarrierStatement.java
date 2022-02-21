package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.IBarrierTable;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class NewBarrierStatement implements IStatement{
    private String variable;
    private IExpression expression;
    private static Integer newFreeLocation = 0;

    public NewBarrierStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl= state.getSymbolsTable();
        IHeap<Integer, Value> heap = state.getHeap();
        Value value = expression.evaluate(symTbl, heap);
        if(value.getType().equals(new IntType())){
            IntValue intValue = (IntValue) value;
            IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTable();
            barrierTable.add(getNewFreeLocation(), new Pair<>(intValue.getValue(), new ArrayList<>()));
            if(symTbl.isDefined(variable)){
                symTbl.update(variable, new IntValue(getNewFreeLocation()));
            }else
            {
                symTbl.add(variable, new IntValue(getNewFreeLocation()));
            }
        }else throw new WrongType("NewBarrier: Expression must evaluate to Integer!");
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
        return new NewBarrierStatement(variable, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType varType = typeEnvironment.lookup(variable);
        IType typExp = expression.typeCheck(typeEnvironment);
        if (typExp.equals(new IntType()) && varType.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException("New Barrier: expression and variable must be integer ");

    }

    @Override
    public String toString(){
        return "newBarrier(" + variable + ", " + expression.toString() + ")";
    }
}
