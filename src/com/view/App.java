package com.view;

import com.model.Database;
import com.model.FuncionarioRepository;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lojas.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    static Database db = new Database("Database.db");
    static public FuncionarioRepository funcionarioRepository = new FuncionarioRepository(db);
    
    public static void print(String s)
    {
        System.out.println(s);
    }
    
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

