package com.example.interpretergui.Controller;

import com.example.interpretergui.Model.Exceptions.ProgramFinnsihed;
import com.example.interpretergui.Model.State.*;
import com.example.interpretergui.Model.Statements.IStatement;
import com.example.interpretergui.Model.Values.RefValue;
import com.example.interpretergui.Model.Values.Value;
import com.example.interpretergui.Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    IRepository repository;
    boolean displayFlag;
    IStatement initialState;
    ExecutorService executor;


//    public IRepository getRepository() {
//        return repository;
//    }

    public Controller(IRepository repository) {
        this.repository = repository;
        this.initialState = repository.getProgramList().get(0).getOriginalProgram();
        displayFlag = true;
    }

    public IRepository getRepository() {
        return repository;
    }

   public void oneStepForAllPrograms(List<ProgramState> programs){
        programs.forEach(program -> repository.logPrgStateExec(program));
        List<Callable<ProgramState>> callList = programs.stream()
                .map((ProgramState p)->(Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        try {
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            programs.addAll(newProgramList);
            programs.forEach(prg -> repository.logPrgStateExec(prg));
            repository.setProgramList(programs);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public IStatement getInitialState() {
        return initialState;
    }


    public void initialize_executor(){
        executor = Executors.newFixedThreadPool(2);
    }

    public void oneStepForAllExisting(){
        List<ProgramState> programsList = removeCompletedPrograms(repository.getProgramList());
        if(programsList.size() > 0){

            oneStepForAllPrograms(programsList);

            List<Integer> addresses = new ArrayList<>();
            programsList.forEach(programState ->
                    addresses.addAll(getAddresses(programState.getSymbolsTable().getContent().values(), programState.getHeap().getContent().values())));
            programsList.get(0).getHeap().setContent(safeGarbageCollector(addresses, programsList.get(0).getHeap().getContent()));

            programsList.forEach(program -> repository.logPrgStateExec(program));
        }
        else{
            //repository.setProgramList(programsList);
            executor.shutdownNow();
            throw new ProgramFinnsihed("Done");
        }

    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programsList = removeCompletedPrograms(repository.getProgramList());
        while(programsList.size() > 0){
            oneStepForAllPrograms(programsList);
            List<Integer> addresses = new ArrayList<>();
            programsList.forEach(programState ->
                    addresses.addAll(getAddresses(programState.getSymbolsTable().getContent().values(), programState.getHeap().getContent().values())));
            programsList.get(0).getHeap().setContent(safeGarbageCollector(addresses, programsList.get(0).getHeap().getContent()));

            programsList.forEach(program -> repository.logPrgStateExec(program));

            programsList = removeCompletedPrograms(repository.getProgramList());
        }

        executor.shutdownNow();
        repository.setProgramList(programsList);
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    List<Integer> getAddresses(Collection<Value> symTableValues, Collection<Value>heap){
        List <Integer> fromTable =  symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
        List <Integer> fromHeap = heap.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .collect(Collectors.toList());
        List<Integer> result = new ArrayList<>();
        result.addAll(fromTable);
        result.addAll(fromHeap);
        return result;
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramsList){
       return inProgramsList.stream()
               .filter(ProgramState::isNotCompleted)
               .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return initialState.toString();
    }
}



//    public String getOutput(){
//        return repository.getCurrentProgram().getOutputList().toString();
//

//    public boolean isDisplayFlag() {
//        return displayFlag;
//    }
//
//    public void setDisplayFlag(boolean displayFlag) {
//        this.displayFlag = displayFlag;
//    }
//
//    public void loadProgram(IStatement statement){
//        ProgramState program = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), new Heap(),statement);
//        repository.add(program);
//    }