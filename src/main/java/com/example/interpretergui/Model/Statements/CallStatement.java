package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.*;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CallStatement implements IStatement{

    private String name;
    private List<IExpression> expressions;

    public CallStatement(String name, List<IExpression> expressions) {
        this.name = name;
        this.expressions = expressions;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IProcTable<String, Pair<List<String>, IStatement>> procTable = state.getProcTable();
        MyIDictionary<String, Value> symTable = state.getSymbolsTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(procTable.isDefined(name)){
            List<String> array = procTable.lookup(name).getKey();
            IStatement body = procTable.lookup(name).getValue();
            MyIDictionary<String, Value> newSymTable = new MyDictionary();
            for (IExpression expr: expressions){
                Value value = expr.evaluate(symTable, heap);
                newSymTable.add(array.get(expressions.indexOf(expr)), value);
            }
            state.getStackSymTables().push(newSymTable);
            state.getStack().push(new ReturnStatement());
            state.getStack().push(body);
        }else throw new UndefinedVariable("Procedure not defined!");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        ArrayList<IExpression> newExpr = new ArrayList<>();
        for(IExpression elem: expressions){
            newExpr.add(elem.deepCopy());
        }
        return new CallStatement(name, newExpr);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "call " + name + "(" + expressions + ")";
    }
}
