package com.merc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.merc.ui.DeleteFile;
import com.merc.ui.RegisterFile;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class RootController implements Initializable {

 
 @Override
 public void initialize(URL location, ResourceBundle resources) {
 }
 
 public void handleRegister(ActionEvent e) throws Exception {
	 new RegisterFile();
 }
 
 public void handleDelete(ActionEvent e) throws Exception {
	 new DeleteFile();

 }
 

}