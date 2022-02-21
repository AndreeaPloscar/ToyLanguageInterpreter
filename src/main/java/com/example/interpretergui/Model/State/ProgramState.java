package com.example.interpretergui.Model.State;

import com.example.interpretergui.Model.Exceptions.EmptyCollection;
import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Statements.IStatement;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private Stack<MyIDictionary<String, Value>> stackSymTables;
    private MyIList<Value> output;
    private IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private IStatement originalProgram;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private ILatchTable<Integer, Integer> latchTable;
    private IProcTable<String, Pair<List<String>, IStatement>> procTable;
    private ILockTable<Integer,Integer> lockTable;
    private ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable;
    private int id;
    public static int maxId;

    public ILatchTable<Integer, Integer> getLatchTable() {
        return latchTable;
    }

    public Stack<MyIDictionary<String, Value>> getStackSymTables() {
        return stackSymTables;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }
    public int getId() {
        return id;
    }

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, Value> symbolsTable, MyIList<Value> output, IFileTable<StringValue, BufferedReader> fileTable, IHeap<Integer, Value> heap, IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable,ILatchTable<Integer, Integer> latchTable, ILockTable<Integer, Integer> lockTable,ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semTable, IProcTable<String, Pair<List<String>, IStatement>> procTable, IStatement originalProgram) {

        this.executionStack = executionStack;
        this.stackSymTables = new Stack<>() ;
        this.stackSymTables.push(symbolsTable);
        this.output = output;
        this.procTable = procTable;
        this.originalProgram = originalProgram;
        executionStack.push(originalProgram.deepCopy());
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getMaxId() + 1;
        this.barrierTable = barrierTable;
        this.latchTable = latchTable;
        this.lockTable = lockTable;
        this.semTable = semTable;
        modifyMaxId(id);
    }
    public IHeap<Integer, Value> getHeap(){
        return heap;
    }

    public ISemaphoreTable<Integer, Pair<Integer, List<Integer>>> getSemTable() {
        return semTable;
    }

    public ILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public IBarrierTable<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return barrierTable;
    }

    public MyIStack<IStatement> getStack(){
        return executionStack;
    }

    public MyIDictionary<String, Value> getSymbolsTable(){
        return stackSymTables.peek();
    }

    public void setStackSymTables(Stack<MyIDictionary<String, Value>> stackSymTables) {
        this.stackSymTables = stackSymTables;
    }

    public MyIList<Value> getOutputList(){
        return output;
    }

    public Stack<MyIDictionary<String, Value>> cloneStack() {
        Stack<MyIDictionary<String, Value>> newStack = new Stack<>();
        Enumeration<MyIDictionary<String, Value>> enumeration = stackSymTables.elements();
        while(enumeration.hasMoreElements()){
            MyIDictionary<String, Value> element = enumeration.nextElement();
            newStack.push(element.deepCopy());

        }
        return newStack;
    }

    public void addProcedure(String name, List<String> parameters, IStatement body){
        procTable.add(name, new Pair<>(parameters, body));
    }

    public IProcTable<String, Pair<List<String>, IStatement>> getProcTable() {
        return procTable;
    }

    @Override
    public String toString() {
        return "current Program: "+ id + "\n" + executionStack.toString() + "\n" + stackSymTables.peek().toString() + "\n" + output.toString();
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        if(executionStack.isEmpty()) throw new EmptyCollection("Program State stack is empty");
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public static synchronized int getMaxId(){
        return maxId;
    }

    public static synchronized void modifyMaxId(int newMaxId){
        maxId = newMaxId;
    }


}


