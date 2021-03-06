package com.merc.ui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
 @Override
 public void start(Stage primaryStage) throws Exception {
  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
  Parent root = loader.load();
  Scene scene = new Scene(root);
  
  primaryStage.setTitle("MERC DB Searching Program");
  primaryStage.setScene(scene);
  primaryStage.setResizable(false);
  primaryStage.show();
  
 }
 
public static void main(String[] args) {
  launch(args);
 }
 
}