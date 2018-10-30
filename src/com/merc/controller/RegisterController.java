package com.merc.controller;


import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.merc.core.CreateDoc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

	@FXML private TextField title_field;
	@FXML private TextField keyword_field;
	@FXML private ComboBox<String> category_combo;
	@FXML private Spinner<Integer> year_spinner;
	String old_fullpath;

    
 
 @Override
 public void initialize(URL location, ResourceBundle resources) {

	 initSpinner();
     
 }
 
 public void handleFileSelect(ActionEvent e) throws Exception {
	 
	 FileChooser fileChooser = new FileChooser();
     fileChooser.setTitle("파일을 선택하세요");
     fileChooser.setInitialDirectory(new File("\\"));
     fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Documents", "*.pdf", "*.txt", "*.hwp", "*.doc", "*.docx", "*.xml", "*.html", "*.htm", "*.xls", "*.xlsx", "*.ppt", "*.pptx"));
     File selectedFile = fileChooser.showOpenDialog(null);
     
     if (selectedFile != null) { 
    	 title_field.setText(selectedFile.getName());	
     	 old_fullpath = selectedFile.getPath();
     }


 }
 
 public void handleInitialize(ActionEvent e) throws Exception {
	 title_field.setText("");
	 keyword_field.setText("");
	 initSpinner();
 	 
 }
 
 public void handleFileRegister(ActionEvent e) throws Exception {
 	try {
		
		if(title_field.getText() != null && year_spinner.getValue() != null && category_combo.getValue() != null){
			
			String name = title_field.getText();
			String keyword = StringReplace(keyword_field.getText());
			String category = category_combo.getValue();
			int year = year_spinner.getValue();
			String path = ".\\MERCDocFiles\\" + category + "\\" + year;
			String new_Fullpath = ".\\MERCDocFiles\\" + category + "\\" + year  +"\\" + name;
			File file = new File(path);
			
			if (!file.exists()) {
				file.mkdirs();
				}
		
			
	        Files.copy(new File(old_fullpath).toPath(), new File(new_Fullpath).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
	        new CreateDoc(keyword, category, year, new_Fullpath);
	        messageDialog("등록되었습니다.");
	        
	        title_field.getScene().getWindow().hide();
    	}
		
		else {
	        messageDialog("파일, 년도 또는 카테고리가 선택되지 않았습니다.");

		}
		
		
		} 
	catch (Exception e2) {
	}
 }
 
 private void initSpinner() {
	 
     Calendar now = Calendar.getInstance();   
     int current_year = now.get(Calendar.YEAR);  
     
     year_spinner.setEditable(true);
     SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, current_year, current_year, 1);
     year_spinner.setValueFactory(valueFactory);
 }
 
 
 public String StringReplace(String str){
	   
	   String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]"; 
	   str =str.replaceAll(match, " "); 
	   return str; 
	   
 }
 
 private void messageDialog(String message) {
	 
     Alert alert = new Alert(AlertType.INFORMATION);
     alert.setTitle("알림");
     alert.setHeaderText(null);
     alert.setContentText(message);
     alert.showAndWait();
 }
 
 
 

}