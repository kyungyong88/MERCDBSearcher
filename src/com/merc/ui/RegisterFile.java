package com.merc.ui;

import java.util.Calendar;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegisterFile {
				
			

	 public RegisterFile() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
		Scene scene = new Scene(root);
		Stage subStage = new Stage();
		subStage.setTitle("µî·Ï");
		subStage.setScene(scene);
		subStage.setResizable(false);
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.show();
		
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	subStage.close();
            }
        });   
        
        
		}
	 

	 
	}