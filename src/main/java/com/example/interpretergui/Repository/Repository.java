package com.example.interpretergui.Repository;

import com.example.interpretergui.Model.Exceptions.FileException;
import com.example.interpretergui.Model.Exceptions.FileWriterException;
import com.example.interpretergui.Model.State.MyIDictionary;
import com.example.interpretergui.Model.State.MyIList;
import com.example.interpretergui.Model.State.MyIStack;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Statements.IStatement;
import com.example.interpretergui.Model.Values.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> states;
    String logFilePath;

    public Repository(String logFilePath) {
        this.states = new ArrayList<>();
        this.logFilePath = logFilePath;
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println("");
        }
        catch (IOException exc){
            throw new FileException(exc.getMessage());
        }
    }

    public void add(ProgramState state){
        states.add(state);
    }

    @Override
    public void logPrgStateExec(ProgramState current) {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println("Current program: " + current.getId());
            logFile.println("ExeStack:");
            MyIStack<IStatement> stack = current.getStack();
            MyIDictionary<String, Value> table = current.getSymbolsTable();
            MyIList<Value> list = current.getOutputList();
            logFile.println(stack.toString());
            logFile.println("SymTable:");
            logFile.println(table.toString());
            logFile.println("Out:");
            logFile.println(list.toString());
            logFile.println("FileTable:");
            logFile.println(current.getFileTable().toString());
            logFile.println("BarrierTable:");
            logFile.println(current.getBarrierTable().toString());
            logFile.println("LockTable:");
            logFile.println(current.getLockTable().toString());
            logFile.println("ProceduresTable:");
            logFile.println(current.getProcTable().toString());
            logFile.println("LatchTable:");
            logFile.println(current.getLatchTable().toString());
            logFile.println("SemaphoreTable:");
            logFile.println(current.getSemTable().toString());
            logFile.println("Heap:");
            logFile.println(current.getHeap().toString());
            logFile.println("-----------------------------------------------------------");
            logFile.close();

        }catch (IOException exception){
            throw new FileWriterException(exception.getMessage());
        }

    }

    @Override
    public List<ProgramState> getProgramList(){
        return states;
    }

    @Override
    public void setProgramList(List<ProgramState> newList){
        states = newList;
    }


}
