package com.example.interpretergui.Repository;

import com.example.interpretergui.Model.State.ProgramState;

import java.util.List;

public interface IRepository {
    void add(ProgramState state);
    void logPrgStateExec(ProgramState current);
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> newList);
}
