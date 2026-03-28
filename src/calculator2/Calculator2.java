/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator2;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class Calculator2 extends Application {
    Label num1 = new Label("Number 1: ");
    Label num2 = new Label("Number 2: ");
    
    Label result = new Label("Result: ");
    Label resultValue = new Label("");
    
    Label history = new Label("History: ");
    Label historyValue = new Label("");
    
    TextField tf1 = new TextField();
    TextField tf2 = new TextField();
    Button addbtn = new Button("+");
    Button subbtn = new Button("-");
    Button mulbtn = new Button("*");
    Button divbtn = new Button("/");
    Button cbtn = new Button("Clear");
    Button hbtn = new Button("History");
    
   
    
    
    private final String HISTORY_FILE = "src/calculator2/history.txt";

    
    @Override
    public void start(Stage primaryStage) {
        // new history for each execution
        clearHistoryFile();
        
        addbtn.setPrefWidth(80);
        subbtn.setPrefWidth(80);
        mulbtn.setPrefWidth(80);
        divbtn.setPrefWidth(80);
        cbtn.setPrefWidth(120);
        hbtn.setPrefWidth(120);
        
        result.setAlignment(Pos.CENTER);
        history.setAlignment(Pos.CENTER);
        result.setFont(Font.font("Arial", 20));
        history.setFont(Font.font("Arial", 20));
        
        HBox hboxnum1 = new HBox(10, num1, tf1);
        hboxnum1.setAlignment(Pos.CENTER);
        
        HBox hboxnum2 = new HBox(10, num2, tf2);
        hboxnum2.setAlignment(Pos.CENTER);
        
        HBox hboxop = new HBox(10, addbtn, subbtn, mulbtn, divbtn);
        hboxop.setAlignment(Pos.CENTER);
        
        HBox hboxbtn = new HBox(10, cbtn, hbtn);
        hboxbtn.setAlignment(Pos.CENTER);
        
        //hbox for result value
        HBox hboxres = new HBox(10, result, resultValue);
        hboxres.setAlignment(Pos.CENTER);
        
        //hbox for history value
        VBox vboxhistory = new VBox(10, history, historyValue);
        vboxhistory.setAlignment(Pos.CENTER);
        
        VBox vboxcon = new VBox(20, hboxnum1, hboxnum2, hboxop, hboxres, hboxbtn,vboxhistory);
        vboxcon.setPadding(new Insets(20));
        vboxcon.setAlignment(Pos.CENTER);
        
        //events
         addbtn.setOnAction(e -> calculate("+"));
        subbtn.setOnAction(e -> calculate("-"));
        mulbtn.setOnAction(e -> calculate("*"));
        divbtn.setOnAction(e -> calculate("/"));
        
        cbtn.setOnAction(e -> clear());

        hbtn.setOnAction(e -> {
            historyValue.setText(loadHistoryFromFile());
        });
        

        
        BorderPane p = new BorderPane(vboxcon);
        
        Scene s = new Scene(p, 500, 500);
        
        
        primaryStage.setScene(s);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Calculator With History");
        primaryStage.show();
       
    }

    private void calculate(String op) {
        try {
            double n1 = Double.parseDouble(tf1.getText());
            double n2 = Double.parseDouble(tf2.getText());
            double result = 0;

            switch (op) {
                case "+":
                    result = n1 + n2;
                    break;
                case "-":
                    result = n1 - n2;
                    break;
                case "*":
                    result = n1 * n2;
                    break;
                case "/":
                    if (n2 == 0) {
                        resultValue.setText("Cannot divide by zero");
                        return;
                    }
                    result = n1 / n2;
                    break;
            }

            resultValue.setText(String.valueOf(result));

            String operation = n1 + " " + op + " " + n2 + " = " + result;

        saveToHistoryFile(operation);
            

        } catch (NumberFormatException ex) {
            resultValue.setText("Invalid input");
        }
    }
    
    private void clear() {
             tf1.clear();
            tf2.clear();
            resultValue.setText("");
            historyValue.setText("");
    }
    
    private void saveToHistoryFile(String operation) {
    try {
        
        FileWriter writer = new FileWriter(HISTORY_FILE, true);
        writer.write(operation + "\n");
        writer.close();
    } catch (IOException e) {
        System.out.println(e);
    }
}
    
    private String loadHistoryFromFile() {
    String result = "";

    try {
        Scanner scanner = new Scanner(new File(HISTORY_FILE));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result += line + "\n";
        }

        scanner.close();
    } catch (IOException e) {
        return "";
    }

    return result;
}
    //IOException for file 
    private void clearHistoryFile() {
    try (PrintWriter writer = new PrintWriter(HISTORY_FILE)) {
        writer.print("");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
