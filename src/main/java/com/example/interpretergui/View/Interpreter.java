package com.example.interpretergui.View;

import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Expressions.*;
import com.example.interpretergui.Model.State.*;
import com.example.interpretergui.Model.Statements.*;
import com.example.interpretergui.Model.Types.*;
import com.example.interpretergui.Model.Values.*;
import com.example.interpretergui.Repository.IRepository;
import com.example.interpretergui.Repository.Repository;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        try{
            ex1.typeCheck(new MyEnvironmentTable());
            ProgramState program1 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex1);

            IRepository repo1 = new Repository("log1.txt");
            repo1.add(program1);
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        }
        catch (MyException e){
            System.out.println("1: " +
                    e.getMessage()
            );
        }


        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));
        try{
            ex2.typeCheck(new MyEnvironmentTable());
            ProgramState program2 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex2);
            IRepository repo2 = new Repository("log2.txt");
            repo2.add(program2);
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2",ex2.toString(),ctr2));

        }
        catch (MyException e){
            System.out.println("2: " +
                    e.getMessage()
            );
        }

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));

        try{
            ex3.typeCheck(new MyEnvironmentTable());
            ProgramState program3 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex3);
            IRepository repo3 = new Repository("log3.txt");
            repo3.add(program3);
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3",ex3.toString(),ctr3));
        }
        catch (MyException e){
            System.out.println("3: " +
                    e.getMessage()
            );
        }


        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf",new StringType()),
                new CompoundStatement(new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc",new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))))))))));

        try{
            ex4.typeCheck(new MyEnvironmentTable());
            ProgramState program4 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex4);

            IRepository repo4 = new Repository("log4.txt");
            repo4.add(program4);
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        }
        catch (MyException e){
            System.out.println("4: " +
                    e.getMessage()
            );
        }


        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement( new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),  new ValueExpression(new IntValue(5)),1))))
                ));

        try{
            ex5.typeCheck(new MyEnvironmentTable());
            ProgramState program5 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex5);
            IRepository repo5 = new Repository("log5.txt");
            repo5.add(program5);
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        }
        catch (MyException e){
            System.out.println("5: " +
                    e.getMessage()
            );
        }


        IStatement ex6 = new CompoundStatement( new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement( new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new AllocateStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                ));
        try{
            ex6.typeCheck(new MyEnvironmentTable());
            ProgramState program6 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex6);
            IRepository repo6 = new Repository("log6.txt");
            repo6.add(program6);
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        }
        catch (MyException e){
            System.out.println("6: " +
                    e.getMessage()
            );
        }


        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression( new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)),2)))), new PrintStatement(new VariableExpression("v")))));

        try {
            ex7.typeCheck(new MyEnvironmentTable());
            ProgramState program7 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex7);
            IRepository repo7 = new Repository("log7.txt");
            repo7.add(program7);
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        }
        catch (MyException e){
            System.out.println("7: " +
                    e.getMessage()
            );
        }

        // Ref int v; new(v,20); new(v,30);

        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))), new AllocateStatement("v", new ValueExpression(new IntValue(30)))));

        try{
            ex8.typeCheck(new MyEnvironmentTable());
            ProgramState program8 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex8);
            IRepository repo8 = new Repository("log8.txt");
            repo8.add(program8);
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        }
        catch (MyException e){
            System.out.println("8: " +
                    e.getMessage()
            );
        }


        IStatement ex9 = new CompoundStatement( new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new AllocateStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                        )
                                                )
                                        )),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        try{
            ex9.typeCheck(new MyEnvironmentTable());
            ProgramState program9 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex9);
            IRepository repo9 = new Repository("log9.txt");
            repo9.add(program9);
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        }
        catch (MyException e){
            System.out.println("9: " +
                    e.getMessage()
            );
        }


        IStatement ex10= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new StringValue("2"))), new PrintStatement(new VariableExpression("v"))));
        try{
            ex10.typeCheck(new MyEnvironmentTable());
            ProgramState program10 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex10);

            IRepository repo10 = new Repository("log10.txt");
            repo10.add(program10);
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExample("10",ex10.toString(),ctr10));
        }
        catch (MyException e){
            System.out.println("10: " +
                    e.getMessage()
            );
        }

        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new StringValue("10")),new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));
        try{
            ex11.typeCheck(new MyEnvironmentTable());
            ProgramState program11 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex11);
            IRepository repo11 = new Repository("log11.txt");
            repo11.add(program11);
            Controller ctr11 = new Controller(repo11);
            menu.addCommand(new RunExample("11",ex11.toString(),ctr11));

        }
        catch (MyException e){
            System.out.println("11: " +
                    e.getMessage()
            );
        }


        menu.show();
    }
}
