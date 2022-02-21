package com.example.interpretergui;

import com.example.interpretergui.Controller.Controller;

import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Expressions.*;
import com.example.interpretergui.Model.State.*;
import com.example.interpretergui.Model.Statements.*;
import com.example.interpretergui.Model.Types.BoolType;
import com.example.interpretergui.Model.Types.IntType;
import com.example.interpretergui.Model.Types.RefType;
import com.example.interpretergui.Model.Types.StringType;
import com.example.interpretergui.Model.Values.BoolValue;
import com.example.interpretergui.Model.Values.IntValue;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;
import com.example.interpretergui.Repository.IRepository;
import com.example.interpretergui.Repository.Repository;

import com.example.interpretergui.View.RunExample;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProgramsListController {

    @FXML
    private ListView<Controller> list;

    private final ObservableList<Controller> items = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        addData();
        list.setItems(items);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Controller>() {
            @Override
            public void changed(ObservableValue<? extends Controller> observableValue, Controller s, Controller t1) {
                try{
                    observableValue.getValue().getInitialState().typeCheck(new MyEnvironmentTable());
                }catch (MyException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Typechecking failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Program cannot be executed! Please choose another program.");
                    alert.showAndWait();
                    return;
                }
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == RunProgramController.class) {
                        return new RunProgramController(list.getSelectionModel().getSelectedItem());
                    } else {
                        try {
                            return type.getDeclaredConstructor().newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("program-view.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);

                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }


    private void addData(){
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        ProgramState program1 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex1);

        IRepository repo1 = new Repository("log1.txt");
        repo1.add(program1);
        Controller ctr1 = new Controller(repo1);

        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));

        ProgramState program2 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex2);
        IRepository repo2 = new Repository("log2.txt");
        repo2.add(program2);

        Controller ctr2 = new Controller(repo2);

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));


        ProgramState program3 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex3);
        IRepository repo3 = new Repository("log3.txt");
        repo3.add(program3);

        Controller ctr3 = new Controller(repo3);

        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf",new StringType()),
                new CompoundStatement(new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc",new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))))))))));


        ProgramState program4 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex4);

        IRepository repo4 = new Repository("log4.txt");
        repo4.add(program4);

        Controller ctr4 = new Controller(repo4);

        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement( new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),  new ValueExpression(new IntValue(5)),1))))
                ));


        ProgramState program5 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex5);
        IRepository repo5 = new Repository("log5.txt");
        repo5.add(program5);
        Controller ctr5 = new Controller(repo5);


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

        ProgramState program6 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex6);
        IRepository repo6 = new Repository("log6.txt");
        repo6.add(program6);
        Controller ctr6 = new Controller(repo6);


        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression( new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)),2)))), new PrintStatement(new VariableExpression("v")))));


        ProgramState program7 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex7);
        IRepository repo7 = new Repository("log7.txt");
        repo7.add(program7);
        Controller ctr7 = new Controller(repo7);

        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))), new AllocateStatement("v", new ValueExpression(new IntValue(30)))));


        ProgramState program8 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex8);
        IRepository repo8 = new Repository("log8.txt");
        repo8.add(program8);
        Controller ctr8 = new Controller(repo8);

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


        ProgramState program9 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex9);
        IRepository repo9 = new Repository("log9.txt");
        repo9.add(program9);
        Controller ctr9 = new Controller(repo9);



        IStatement ex10= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new StringValue("2"))), new PrintStatement(new VariableExpression("v"))));

        ProgramState program10 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex10);

        IRepository repo10 = new Repository("log10.txt");
        repo10.add(program10);
        Controller ctr10 = new Controller(repo10);

        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new StringValue("10")),new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));

        ProgramState program11 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex11);
        IRepository repo11 = new Repository("log11.txt");
        repo11.add(program11);
        Controller ctr11 = new Controller(repo11);

        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5),
                                new CompoundStatement(new ForkStatement(new PrintStatement(new VariableExpression("v"))),
                                        new AssignStatement("v", new ArithmeticExpression( new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)),2)))), new PrintStatement(new VariableExpression("v")))));
        ProgramState program12 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex12);
        IRepository repo12 = new Repository("log12.txt");
        repo12.add(program12);
        Controller ctr12 = new Controller(repo12);

        IStatement ex13 = new ForkStatement(new ForkStatement(new ForkStatement(new PrintStatement(new ValueExpression(new StringValue("hello"))))));

        ProgramState program13 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex13);
        IRepository repo13 = new Repository("log13.txt");
        repo13.add(program13);
        Controller ctr13 = new Controller(repo13);

        IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(new AllocateStatement("v1", new ValueExpression(new IntValue(2)))
                                        ,new CompoundStatement(new AllocateStatement("v2", new ValueExpression(new IntValue(3))),
                                        new CompoundStatement(new AllocateStatement("v3", new ValueExpression(new IntValue(4))),
                                                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                                                        new CompoundStatement(new NewBarrierStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)),
                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                                                                ))), new CompoundStatement(
                                                                        new ForkStatement(new CompoundStatement(new AwaitStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)),
                                                                                new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression(new ValueExpression(new IntValue(10)), new ReadHeapExpression(new VariableExpression("v2")), 3)), new PrintStatement(new ReadHeapExpression(new VariableExpression("v2")))))
                                                                        )),
                                                                        new CompoundStatement(new AwaitStatement("cnt"), new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))))
                                                                )
                                                                ))
                                                ))))))
        );
        ProgramState program14 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex14);
        IRepository repo14 = new Repository("log14.txt");
        repo14.add(program14);
        Controller ctr14 = new Controller(repo14);


        IStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(new AllocateStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AllocateStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new AllocateStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)),
                                                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                        new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                new ForkStatement(new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                                        new ForkStatement(new CompoundStatement(new WriteHeapStatement("v3", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                                                                                                                                        new CountDownStatement("cnt"))))
                                                                                                                ))))
                                                                                        )))),
                                                                                new CompoundStatement(new AwaitCountDownStatement("cnt"),
                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(new CountDownStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))))))))))))));

        ProgramState program15 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(),new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex15);
        IRepository repo15 = new Repository("log15.txt");
        repo15.add(program15);
        Controller ctr15 = new Controller(repo15);


        IStatement ex16 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
                                new CompoundStatement(new VariableDeclarationStatement("q", new IntType()),
                                        new CompoundStatement(new AllocateStatement("v1", new ValueExpression(new IntValue(20))),
                                                new CompoundStatement(new AllocateStatement("v2", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new NewLockStatement("x"),
                                                                new CompoundStatement(new ForkStatement(
                                                                        new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(new LockStatement("x"),
                                                                                                new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(
                                                                                                        new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                        new ValueExpression(new IntValue(1)),2)),
                                                                                                        new UnlockStatement("x")
                                                                                                ))
                                                                                ),
                                                                                new CompoundStatement(new LockStatement("x"),
                                                                                        new CompoundStatement(
                                                                                                new WriteHeapStatement("v1", new ArithmeticExpression(
                                                                                                        new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                        new ValueExpression(new IntValue(10)),3)),
                                                                                                new UnlockStatement("x"))

                                                                                ))),
                                                                        new CompoundStatement(new NewLockStatement("q"),
                                                                                new CompoundStatement(new ForkStatement(
                                                                                        new CompoundStatement(new ForkStatement(
                                                                                                new CompoundStatement(new LockStatement("q"),
                                                                                                        new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression(

                                                                                                                new ReadHeapExpression(new VariableExpression("v2")),
                                                                                                                new ValueExpression(new IntValue(5)),1
                                                                                                        )),
                                                                                                                new UnlockStatement("q")
                                                                                                        ))),
                                                                                                new CompoundStatement(new LockStatement("q"),
                                                                                                        new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression(

                                                                                                                new ReadHeapExpression(new VariableExpression("v2")),
                                                                                                                new ValueExpression(new IntValue(10)),3
                                                                                                        )), new UnlockStatement("q"))
                                                                                                )
                                                                                        )
                                                                                ), new CompoundStatement(new NopStatement(),
                                                                                        new CompoundStatement(new NopStatement(),
                                                                                                new CompoundStatement(new NopStatement(),
                                                                                                        new CompoundStatement(new NopStatement(),
                                                                                                                new CompoundStatement(new LockStatement("x"),
                                                                                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                                                new CompoundStatement(new UnlockStatement("x"),
                                                                                                                                        new CompoundStatement(new LockStatement("q"),
                                                                                                                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                                        new UnlockStatement("q")
                                                                                                                                                ))))))))
                                                                                )))))))))));
        ProgramState program16 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(),ex16);
        IRepository repo16 = new Repository("log16.txt");
        repo16.add(program16);
        Controller ctr16 = new Controller(repo16);

        IStatement ex17 = new CompoundStatement(new VariableDeclarationStatement("x",new IntType()),new CompoundStatement(new VariableDeclarationStatement("v1",new RefType(new IntType())),new CompoundStatement(new VariableDeclarationStatement("v2",new RefType(new IntType())), new CompoundStatement(new AllocateStatement("v1",new ValueExpression(new IntValue(20))),new CompoundStatement(new AllocateStatement("v2",new ValueExpression(new IntValue(30))),new CompoundStatement(new NewLockStatement("x"),new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"),new CompoundStatement(new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)),2)),new UnlockStatement("x")))),new CompoundStatement(new LockStatement("x"),new CompoundStatement(new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)),1)),new UnlockStatement("x"))))),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new WriteHeapStatement("v2",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(1)),1))),new CompoundStatement(new WriteHeapStatement("v2",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(1)),1)),new UnlockStatement("x")))),
                        new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(), new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),new CompoundStatement(new NopStatement(),
                                new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))))
                        )))))))))
                )
        )))))));

        ProgramState program17 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(),new LockTable(), new SemaphoreTable(), new ProcTable(),ex17);
        IRepository repo17 = new Repository("log17.txt");
        repo17.add(program17);
        Controller ctr17 = new Controller(repo17);

        IStatement ex18 = new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new AllocateStatement("v1",new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireSemStatement("cnt"),
                                                new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)),3 )),
                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),new ReleaseSemStatement("cnt"))))),
                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireSemStatement("cnt"),
                                                        new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)),
                                                                new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(2)), 3)),
                                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseSemStatement("cnt"))))
                                                )),
                                                        new CompoundStatement(new AcquireSemStatement("cnt"),
                                                                new CompoundStatement(new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)),2)),
                                                                        new ReleaseSemStatement("cnt")))))))));
        ProgramState program18 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(), new LockTable(), new SemaphoreTable(), new ProcTable(), ex18);
        IRepository repo18 = new Repository("log18.txt");
        repo18.add(program18);
        Controller ctr18 = new Controller(repo18);

        IStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(new VariableDeclarationStatement("w", new IntType()),
                                new CompoundStatement(new AssignStatement("w", new ValueExpression(new IntValue(5))),
                                        new CompoundStatement(new CallStatement("sum", Arrays.asList(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), 3), new VariableExpression("w"))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new ForkStatement(new CompoundStatement(new CallStatement("product", Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))), new ForkStatement(new CallStatement("sum", Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))))))
                                                )
                                        )))
                )
        );
        ProgramState program19 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(),new LockTable(), new SemaphoreTable(),new ProcTable(), ex19);
        program19.addProcedure("sum", Arrays.asList("a", "b"),new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 1)), new PrintStatement(new VariableExpression("v")))));
        program19.addProcedure("product", Arrays.asList("a", "b"), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 3)),  new PrintStatement(new VariableExpression("v")))));
        IRepository repo19 = new Repository("log19.txt");
        repo19.add(program19);
        Controller ctr19 = new Controller(repo19);

        IStatement ex20 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new SwitchCaseStatement(new ArithmeticExpression(new VariableExpression("a"),
                                                                new ValueExpression(new IntValue(10)), 3), new ArithmeticExpression(new VariableExpression("b"),
                                                                new VariableExpression("c"), 3),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                                        new PrintStatement(new VariableExpression("b"))),
                                                                new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                        new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300))) ),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))))))
                        )));
        ProgramState program20 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(),new LockTable(), new SemaphoreTable(),new ProcTable(), ex20);
        IRepository repo20 = new Repository("log20.txt");
        repo20.add(program20);
        Controller ctr20 = new Controller(repo20);

        IStatement ex21 = new CompoundStatement(new VariableDeclarationStatement( "a",new RefType(new IntType())),
                new CompoundStatement(
                        new AllocateStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement(new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)),
                                new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 1),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression
                                        (new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a")),3)))),"v"), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))
        );
        ProgramState program21 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(),new LockTable(), new SemaphoreTable(),new ProcTable(), ex21);
        IRepository repo21 = new Repository("log21.txt");
        repo21.add(program21);
        Controller ctr21 = new Controller(repo21);

        IStatement ex22 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(new AllocateStatement("a", new ValueExpression(new IntValue(0)))
                                        ,new CompoundStatement(new AllocateStatement("b", new ValueExpression(new IntValue(0)))
                                        , new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(1)))
                                        ,new CompoundStatement( new WriteHeapStatement("b", new ValueExpression(new IntValue(2)))
                                        ,new CompoundStatement(new ConditionalAssignmentStatement("v", new RelationalExpression(new ReadHeapExpression(new VariableExpression("a")), new ReadHeapExpression(new VariableExpression("b")),1), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new CompoundStatement(new ConditionalAssignmentStatement("v", new RelationalExpression(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2)),2), new ReadHeapExpression(new VariableExpression("a")),5), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200)))
                                                        ,new PrintStatement(new VariableExpression("v"))))
                                )))
                                ))
                        )
                ));
        ProgramState program22 = new ProgramState(new MyStack<IStatement>(), new MyDictionary(), new MyList<Value>(),new FileTable<StringValue, BufferedReader>(),new Heap(), new BarrierTable(), new LatchTable(),new LockTable(), new SemaphoreTable(),new ProcTable(), ex22);
        IRepository repo22 = new Repository("log22.txt");
        repo22.add(program22);
        Controller ctr22 = new Controller(repo22);

        items.add(ctr1);
        items.add(ctr2);
        items.add(ctr3);
        items.add(ctr4);
        items.add(ctr5);
        items.add(ctr6);
        items.add(ctr7);
        items.add(ctr8);
        items.add(ctr9);
        items.add(ctr10);
        items.add(ctr11);
        items.add(ctr12);
        items.add(ctr13);
        items.add(ctr14);
        items.add(ctr15);
        items.add(ctr16);
        items.add(ctr17);
        items.add(ctr18);
        items.add(ctr19);
        items.add(ctr20);
        items.add(ctr21);
        items.add(ctr22);
    }

}