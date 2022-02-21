package com.example.interpretergui;

import com.example.interpretergui.Controller.Controller;
import com.example.interpretergui.Model.Exceptions.MyException;
import com.example.interpretergui.Model.Exceptions.ProgramFinnsihed;
import com.example.interpretergui.Model.State.ProgramState;
import com.example.interpretergui.Model.Statements.IStatement;
import com.example.interpretergui.Model.Values.StringValue;
import com.example.interpretergui.Model.Values.Value;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.w3c.dom.events.MouseEvent;
import com.example.interpretergui.Controller.Controller;

import java.io.BufferedReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RunProgramController implements Initializable {
    private Controller controller;

    @FXML
    private TextField prgStates;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableValue;

    @FXML
    private ListView<String> outList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<String> prgStatesIdentifiers;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolsTable;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolsTableVariable;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolsTableValue;

    @FXML
    private ListView<String> executionStack;


    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> barriersTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barriersTableAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barriersTableIds;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> barriersTableValue;


    @FXML
    private TableView<Map.Entry<Integer, Integer>> lockTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> lockTableAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> lockTableId;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semTableAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semTableIds;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semTableValue;

    @FXML
    private Button runStep;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchAddresses;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchValues;

    @FXML
    private TableView<Map.Entry<String, Pair<List<String>, IStatement>>> procTable;

    @FXML
    private TableColumn<Map.Entry<String, Pair<List<String>, IStatement>>, String> procTableNames;

    @FXML
    private TableColumn<Map.Entry<String, Pair<List<String>, IStatement>>, String> procTableBody;



    public RunProgramController(Controller controller) {
        this.controller = controller;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            updateAll();
        controller.initialize_executor();
        prgStatesIdentifiers.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                updateSym();
                updateStack();
            }
        });

        runStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                try {
                    controller.oneStepForAllExisting();
                    updateAll();
                } catch (ProgramFinnsihed exception) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Toy Language Interpreter");
                    alert.setHeaderText(null);
                    alert.setContentText("Program finished successfully!");
                    alert.showAndWait();
                    Stage stage = (Stage) prgStates.getScene().getWindow();
                    stage.close();
                }
                catch (MyException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Toy Language Interpreter");
                    alert.setHeaderText("Errors while running program");
                    alert.setContentText(exception.getMessage());
                    alert.showAndWait();
                    Stage stage = (Stage) prgStates.getScene().getWindow();
                    stage.close();
                }

            }
        });



    }



    public void updateAll(){
        updateStatesNr();
        updateHeap();
        updateOut();
        updateFiles();
        updateStates();
        updateLatch();
        updateLockTable();
        updateBarriers();
        updateSemaphores();
        updateProcedures();
        if (prgStatesIdentifiers.getSelectionModel().getSelectedItem() == null){
            prgStatesIdentifiers.getSelectionModel().selectFirst();
        }
        updateSym();
        updateStack();
    }


    public void updateProcedures(){
        procTable.refresh();
        ObservableList<HashMap.Entry<String, Pair<List<String>, IStatement>>> tableList = FXCollections.observableArrayList();
        procTableNames.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey() + cellData.getValue().getValue().getKey()));
        procTableBody.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
        if (! controller.getRepository().getProgramList().isEmpty())
            tableList.addAll(controller.getRepository().getProgramList().get(0).getProcTable().getMap().entrySet());
        procTable.setItems(tableList);
    }
    public void updateBarriers(){
        barriersTable.refresh();
        ObservableList<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>> barriersTableList = FXCollections.observableArrayList();
        barriersTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        barriersTableIds.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
        barriersTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getKey().toString()));

        if (! controller.getRepository().getProgramList().isEmpty())
            barriersTableList.addAll(controller.getRepository().getProgramList().get(0).getBarrierTable().getMap().entrySet());
        barriersTable.setItems(barriersTableList);
    }

    public void updateSemaphores(){
        semTable.refresh();
        ObservableList<HashMap.Entry<Integer, Pair<Integer, List<Integer>>>> semTableList = FXCollections.observableArrayList();
        semTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        semTableIds.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
        semTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getKey().toString()));
        if (! controller.getRepository().getProgramList().isEmpty())
            semTableList.addAll(controller.getRepository().getProgramList().get(0).getSemTable().getMap().entrySet());
        semTable.setItems(semTableList);
    }


    public void updateLockTable(){
        lockTable.refresh();
        ObservableList<HashMap.Entry<Integer, Integer>> lockTableList = FXCollections.observableArrayList();
        lockTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        lockTableId.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        if (! controller.getRepository().getProgramList().isEmpty())
            lockTableList.addAll(controller.getRepository().getProgramList().get(0).getLockTable().getMap().entrySet());
        lockTable.setItems(lockTableList);
    }


    public void updateStatesNr(){
        prgStates.setText(""+controller.getRepository().getProgramList().size());
    }

    public void updateLatch(){
        latchTable.refresh();
        ObservableList<HashMap.Entry<Integer, Integer>> tableList = FXCollections.observableArrayList();
        latchAddresses.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        latchValues.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        if (! controller.getRepository().getProgramList().isEmpty())
            tableList.addAll(controller.getRepository().getProgramList().get(0).getLatchTable().getMap().entrySet());
        latchTable.setItems(tableList);
    }


    public void updateHeap(){
        heapTable.refresh();
        ObservableList<HashMap.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        if (! controller.getRepository().getProgramList().isEmpty())
        heapTableList.addAll(controller.getRepository().getProgramList().get(0).getHeap().getMap().entrySet());
        heapTable.setItems(heapTableList);
    }
    
    public void updateOut(){
        ObservableList<String> outList2 = FXCollections.observableArrayList();
        if (! controller.getRepository().getProgramList().isEmpty())
        for(Value e : controller.getRepository().getProgramList().get(0).getOutputList().getList()){
            outList2.add(e.toString());
        }
        outList.setItems(outList2);
    }
    
    public void updateFiles(){
        ObservableList<String> filesList2 = FXCollections.observableArrayList();
        if (! controller.getRepository().getProgramList().isEmpty())
        for(Map.Entry<StringValue, BufferedReader> e : controller.getRepository().getProgramList().get(0).getFileTable().getMap().entrySet()){
            filesList2.add(e.getKey().getValue());
        };
        fileList.setItems(filesList2);
    }
    
    public void updateStates(){
        ObservableList<String> prgStateList = FXCollections.observableArrayList();
        for(ProgramState e : controller.getRepository().getProgramList()) {
            prgStateList.add(Integer.toString(e.getId()));
        }
        prgStatesIdentifiers.setItems(prgStateList);
    }
    
    public void updateSym(){
        symbolsTable.refresh();
        ObservableList<HashMap.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        symbolsTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symbolsTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        List<ProgramState> allPrgs = controller.getRepository().getProgramList();
        ProgramState prgResult = null;
        for(ProgramState e : allPrgs) {
            if(e.getId() == Integer.parseInt(prgStatesIdentifiers.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymbolsTable().getMap().entrySet());
            symbolsTable.setItems(symTableList);
        }
    }
    
    public void updateStack(){

        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();

        List<ProgramState> allPrgs = controller.getRepository().getProgramList();
        ProgramState prgResult = null;
        for(ProgramState e : allPrgs) {
            if(e.getId() == Integer.parseInt(prgStatesIdentifiers.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if(prgResult != null) {

            for(IStatement e : prgResult.getStack().getStack()) {
                exeStackItemsList.add(e.toString());
            }

            executionStack.setItems(exeStackItemsList);
        }
    }


}
