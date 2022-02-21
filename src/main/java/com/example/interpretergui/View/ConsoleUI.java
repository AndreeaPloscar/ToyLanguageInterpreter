package com.example.interpretergui.View;//package View;
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
//
//import java.util.Objects;
//import java.util.Scanner;
//
//public class ConsoleUI {
//
//    private Controller controller;
//
//    public ConsoleUI(Controller controller) {
//        this.controller = controller;
//    }
//
//    public void printMenu(){
//        System.out.println("1 - See all programs\n" +
//                "2 - Choose program to execute\n" +
//                "x - Exit\n");
//    }
//
//    public void seePrograms(){
//        System.out.println("1 - int v; v=2;Print(v)\n" +
//                "2 - int a;int b; a=2+3*5;b=a+1;Print(b)\n" +
//                "3 - bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)\n");
//    }
//
//    public void executeProgram(){
//        System.out.println("Which program do you want to execute?");
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(">>");
//        String option = scanner.nextLine();
//        System.out.println("1 - With intermediate steps");
//        System.out.println("0 - Without intermediate steps");
//        System.out.println(">>");
//        String option2 = scanner.nextLine();
//        controller.setDisplayFlag(!Objects.equals(option2, "0"));
//        try{
//
//            controller.loadProgram(Input.getProgram(option));
//            controller.allStep();
//            System.out.println("Output:");
//            System.out.println(controller.getOutput());
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void run(){
//        System.out.println("--------- INTERPRETER ---------");
//        Scanner scanner = new Scanner(System.in);
//        printMenu();
//        Input.LoadInput();
//        System.out.println(">>");
//        String option = scanner.nextLine();
//        while(!Objects.equals(option, "x")){
//            switch (option){
//                case "1":
//                    seePrograms();
//                    break;
//                case "2":
//                    executeProgram();
//                    break;
//            }
//            printMenu();
//            System.out.println(">>");
//            option = scanner.nextLine();
//        }
//    }
//}
