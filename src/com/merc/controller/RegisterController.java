package com.merc.controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.merc.core.CreateDoc;
import com.merc.core.SearchDoc;
import com.merc.ui.cell.PromptButtonCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

	@FXML private TextField title_field;
	@FXML private TextField keyword_field;
	@FXML private ComboBox<String> category_combo;
	@FXML private Spinner<String> year_spinner;
	String old_fullpath;
	private Stage subStage;

    
 
 @Override
 public void initialize(URL location, ResourceBundle resources) {

	 initSpinner();
     
 }
 
 public void setSubStage(Stage subStage) {
	 this.subStage = subStage;
 }
 
 public void handleFileSelect(ActionEvent e) throws Exception {
	 
	 FileChooser fileChooser = new FileChooser();
     fileChooser.setTitle("파일을 선택하세요");
     fileChooser.setInitialDirectory(new File(".\\"));
     fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Documents", "*.pdf", "*.txt", "*.hwp", "*.doc", "*.docx", "*.xml", "*.html", "*.htm", "*.xls", "*.xlsx", "*.ppt", "*.pptx"));
     File selectedFile = fileChooser.showOpenDialog(null);
     
     if (selectedFile != null) { 
    	 title_field.setText(selectedFile.getName());	
     	 old_fullpath = selectedFile.getPath();
     }

 }
 
 public void handleDirectorySelect(ActionEvent e) throws Exception {
	 
	 DirectoryChooser directoryChooser = new DirectoryChooser();
	 directoryChooser.setTitle("폴더를 선택하세요");
	 directoryChooser.setInitialDirectory(new File(".\\"));
     File selectedFile = directoryChooser.showDialog(null);
     
     if (selectedFile != null) { 
    	 title_field.setText(selectedFile.getPath());	
     	 old_fullpath = selectedFile.getPath();
     }
 }
 
 public void handleInitialize(ActionEvent e) throws Exception {
	 title_field.setText(null);
	 keyword_field.setText("");
	 initSpinner();
     category_combo.getSelectionModel().clearSelection();
     category_combo.setButtonCell(new PromptButtonCell<>("카테고리"));
 	 
 }
 
 
 public void handleFileRegister(ActionEvent e) throws Exception {
	 
	 if(title_field.getText() != null && year_spinner.getValue() != "생산년도" && category_combo.getValue() != null){
	     if (Files.isDirectory(Paths.get(old_fullpath))) 
	     {
	         //Iterate directory
	         Files.walkFileTree(Paths.get(old_fullpath), new SimpleFileVisitor<Path>() 
	         {
	             @Override
	             public FileVisitResult visitFile(Path fullpath, BasicFileAttributes attrs) throws IOException 
	             {
	                 //Index this file
					 registerfile(fullpath);
	                 return FileVisitResult.CONTINUE;
	             }
	         });
	         subStage.close();
	         
	     } 
	     else
	     {
	         //Index this file
	    	 registerfile();
	     }
	 }
	 
	else {
        messageDialog("파일, 년도 또는 카테고리가 선택되지 않았습니다.");

	}
 }
 

 public void registerfile(Path fullpath) {
	 	try {
			
				
			String name = fullpath.getFileName().toString();
			String keyword = StringReplace(keyword_field.getText());
			String category = category_combo.getValue();
			int year = Integer.parseInt(year_spinner.getValue());
			String path = ".\\MERCDocFiles\\" + category + "\\" + year;
			String new_Fullpath = ".\\MERCDocFiles\\" + category + "\\" + year  +"\\" + name;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
				}
		
			SearchDoc searchdoc = new SearchDoc();

            if(searchdoc.isEmptyDirectory())
			{
		        Files.copy(fullpath, new File(new_Fullpath).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
		        new CreateDoc(keyword, category, year, new_Fullpath);
		        messageDialog("파일 " + name.substring(0, name.lastIndexOf('.')) +"이(가) 등록되었습니다.");
			}
			else
			{
            	
				if(searchdoc.isDuplicated(new_Fullpath))
				{
					messageDialog(name.substring(0, name.lastIndexOf('.')) + "은(는) 중복된 파일입니다.");
				}
				else
				{
			        Files.copy(fullpath, new File(new_Fullpath).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
			        new CreateDoc(keyword, category, year, new_Fullpath);
			        messageDialog("파일 " + name.substring(0, name.lastIndexOf('.')) +"이(가) 등록되었습니다.");
				}
			}

			} 
		catch (Exception e2) {
		}
}
 
 
 
 public void registerfile() {
	 	try {
			
			if(title_field.getText() != null && year_spinner.getValue() != "생산년도" && category_combo.getValue() != null){
				
				String name = title_field.getText();
				String keyword = StringReplace(keyword_field.getText());
				String category = category_combo.getValue();
				int year = Integer.parseInt(year_spinner.getValue());
				String path = ".\\MERCDocFiles\\" + category + "\\" + year;
				String new_Fullpath = ".\\MERCDocFiles\\" + category + "\\" + year  +"\\" + name;
				File file = new File(path);
				
				if (!file.exists()) {
					file.mkdirs();
					}
			
				SearchDoc searchdoc = new SearchDoc();

	            if(searchdoc.isEmptyDirectory())
				{
			        Files.copy(new File(old_fullpath).toPath(), new File(new_Fullpath).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
			        new CreateDoc(keyword, category, year, new_Fullpath);
			        messageDialog("파일 " + name.substring(0, name.lastIndexOf('.')) +"이(가) 등록되었습니다.");
				}
				else
				{
	            	
					if(searchdoc.isDuplicated(new_Fullpath))
					{
						messageDialog(name.substring(0, name.lastIndexOf('.')) + "은(는) 중복된 파일입니다.");
					}
					else
					{
				        Files.copy(new File(old_fullpath).toPath(), new File(new_Fullpath).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
				        new CreateDoc(keyword, category, year, new_Fullpath);
				        messageDialog("파일 " + name.substring(0, name.lastIndexOf('.')) +"이(가) 등록되었습니다.");
					}
				}

	    	}
			
			else {
		        messageDialog("파일, 년도 또는 카테고리가 선택되지 않았습니다.");

			}
			
			
			} 
		catch (Exception e2) {
		}
 }
 
 
 private void initSpinner() {
     year_spinner.setEditable(true);
     SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(years());
     year_spinner.setValueFactory(valueFactory);
 }
 
 private ObservableList<String> years(){
	 
	 ObservableList<String> temp = FXCollections.observableArrayList();
	 
	 Calendar now = Calendar.getInstance();   
     int current_year = now.get(Calendar.YEAR);  
     
     temp.add("생산년도");
     
     for(int i = 1990; i<=current_year; i++) {
    	 temp.add(Integer.toString(i));
     }
     
     return temp;
	 
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