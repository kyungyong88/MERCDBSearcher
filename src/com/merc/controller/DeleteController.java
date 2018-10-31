package com.merc.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.merc.core.DeleteDoc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class DeleteController implements Initializable {
	@FXML private Label title_label;
	String fullpath;
	DeleteDoc deletedoc;
	
	 @Override
	 public void initialize(URL location, ResourceBundle resources) {
	     
	 }
	 
	 public void handleFileSelect(ActionEvent e) throws Exception {
		 
		 FileChooser fileChooser = new FileChooser();
	     fileChooser.setTitle("파일을 선택하세요");
	     fileChooser.setInitialDirectory(new File("\\"));
	     fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Documents", "*.pdf", "*.txt", "*.hwp", "*.doc", "*.docx", "*.xml", "*.html", "*.htm", "*.xls", "*.xlsx", "*.ppt", "*.pptx"));
	     File selectedFile = fileChooser.showOpenDialog(null);
	     
	     if (selectedFile != null) { 
	    	 title_label.setText(selectedFile.getName());
	     	 fullpath = selectedFile.getPath();
	     }


	 }
	 
	 
	 
	 public void handleFileDelete(ActionEvent e) throws Exception {
     	try {
     		deletedoc = new DeleteDoc();
    		if(deletedoc.deleteDocument(fullpath))
    		{
    			File file = new File(fullpath);
    			file.delete();
    			messageDialog("삭제되었습니다.");
    			title_label.getScene().getWindow().hide();
    		}
    		else
    			messageDialog("파일이 없습니다.");
    		}
    	catch (Exception e1) {
			
			e1.printStackTrace();
		}

		 }
	 
	 private void messageDialog(String message) {
		 
	     Alert alert = new Alert(AlertType.INFORMATION);
	     alert.setTitle("알림");
	     alert.setHeaderText(null);
	     alert.setContentText(message);
	     alert.showAndWait();
	 }
	 

}
