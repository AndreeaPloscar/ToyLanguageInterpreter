package com.example.interpretergui.View;//package View;
//
//import Model.Expressions.ArithmeticExpression;
//import Model.Expressions.ValueExpression;
//import Model.Expressions.VariableExpression;
//import Model.Statements.*;
//import Model.Types.BoolType;
//import Model.Types.IntType;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import com.sun.jdi.IntegerValue;
//
//import java.util.ArrayList;
//
//public class Input {
//    private static ArrayList<IStatement> inputs ;
//
//    public static void LoadInput() {
//        inputs = new ArrayList<IStatement>();
//        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
//                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
//
//        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
//                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
//                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(1,new ValueExpression(new IntValue(2)),new ArithmeticExpression(3,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
//                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression(1,new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
//
//        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
//                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))),
//                                new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
//                                VariableExpression("v"))))));
//        inputs.add(ex1);
//        inputs.add(ex2);
//        inputs.add(ex3);
//    }
//
//    public static IStatement getProgram(String index){
//        return inputs.get(Integer.parseInt(index)-1);
//    }
//}
