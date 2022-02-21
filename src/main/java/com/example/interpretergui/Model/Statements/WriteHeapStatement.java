package com.example.interpretergui.Model.Statements;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.UndefinedVariable;
import com.example.interpretergui.Model.Exceptions.WrongType;
import com.example.interpretergui.Model.Expressions.IExpression;
import com.example.interpretergui.Model.State.IHeap;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Types.IType;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;

public class WriteHeapStatement implements IStatement{

    String variableName;
    IExpression expression;

    public WriteHeapStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> table = state.getSymbolsTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(table.isDefined(variableName)){
            Value val = table.lookup(variableName);
            if(val.getType() instanceof RefType){
                RefValue ref_value = (RefValue) val;
                if(heap.find(ref_value.getAddress())){
                    Value val_from_expr = expression.evaluate(table, heap);
                    if(val_from_expr.getType().equals(((RefType)ref_value.getType()).getInner())){
                        heap.update(ref_value.getAddress(), val_from_expr);
                        return null;
                    }else throw new WrongType("Wrong type");
                }else throw new UndefinedVariable("Variable not defined");
            }
            else throw new WrongType("Wrong type");
        }else throw new UndefinedVariable("Variable not defined");
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression.deepCopy());
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
        return "writeHeap("+variableName+","+expression.toString()+")";
    }
}
