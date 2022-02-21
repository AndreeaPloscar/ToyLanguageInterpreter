package com.example.interpretergui.Model.Tests;//package Model.Tests;
//
//import Controller.Controller;
//import Model.Expressions.ArithmeticExpression;
//import Model.Expressions.ValueExpression;
//import Model.Expressions.VariableExpression;
//import Model.Statements.*;
//import Model.Types.BoolType;
//import Model.Types.IntType;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import Repository.IRepository;
//import Repository.Repository;
//
//import static org.junit.Assert.*;
//
//
//import java.util.Objects;
//
//public class Test {
//    public static void test_all(){
//        IRepository repository = new Repository("");
//        Controller controller = new Controller(repository);
//        controller.setDisplayFlag(false);
//        System.out.println("....Tests....");
//
//        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
//                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
//                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(1,new ValueExpression(new IntValue(2)),new ArithmeticExpression(3,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
//                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(1,new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
//        controller.loadProgram(ex2);
//        controller.allStep();
//        assertTrue(controller.getRepository().getCurrentProgram().getStack().isEmpty());
//        assertEquals("18\n", controller.getRepository().getCurrentProgram().getOutputList().toString());
//        assertEquals("a --> 17\nb --> 18\n", controller.getRepository().getCurrentProgram().getSymbolsTable().toString());
//
//        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
//                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))),
//                                new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
//                                VariableExpression("v"))))));
//
//        controller.loadProgram(ex3);
//        controller.allStep();
//        assertTrue(controller.getRepository().getCurrentProgram().getStack().isEmpty());
//        assertEquals("2\n", controller.getRepository().getCurrentProgram().getOutputList().toString());
//        assertEquals("a --> true\nv --> 2\n", controller.getRepository().getCurrentProgram().getSymbolsTable().toString());
//
//
//        System.out.println("Tests passed!\n");
//    }
//}
