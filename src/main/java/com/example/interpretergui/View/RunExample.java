package com.example.interpretergui.View;

import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.Exceptions.MyException;

public class RunExample extends Command{
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute() {
        try{
         ctr.allStep();
        }
        catch (MyException exc){
            System.out.println(exc.getMessage());
        }
    }
}
