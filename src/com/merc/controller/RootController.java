package com.merc.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;


import com.merc.core.SearchDoc;
import com.merc.ui.DeleteFile;
import com.merc.ui.RegisterFile;
import com.merc.ui.cell.AttachmentListCell;
import com.merc.ui.cell.PromptButtonCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.scene.control.Alert.AlertType;

public class RootController implements Initializable {
	
@FXML TextField search_field;
@FXML TextField keyword_field;
@FXML private ComboBox<String> category_combo;
@FXML private Spinner<String> year_spinner;
@FXML private ListView<String> items_listview;


ObservableList<String> data;

SearchDoc searchdoc;


 
 @Override
 public void initialize(URL location, ResourceBundle resources) {
	 
	 search_field.setPromptText("검색할 단어를 입력하세요.");
	 keyword_field.setPromptText("키워드");
	 initSpinner();
 
 }
 
 public void handleRegister(ActionEvent e) throws Exception {
	 new RegisterFile();
 }
 
 public void handleDelete(ActionEvent e) throws Exception {
	 new DeleteFile();

 }
 
 public void handleSearch(ActionEvent e) throws Exception {
		try {
    		if(search_field.getText().equals("")) {
    			String keyword = keyword_field.getText();
    			String category = "";
    			if(category_combo.getValue() != null)
    				category = category_combo.getValue();
    			String year = "";
    			if(year_spinner.getValue() != "생산년도")
    				year = year_spinner.getValue().toString();
    			
    			searchdoc = new SearchDoc();
    			
    			button_adder(searchdoc.findAllFiles(keyword, category, year));
    			search_field.setText("");
    			keyword_field.setText("");
    			initSpinner();	
    		    category_combo.getSelectionModel().clearSelection();
    		    category_combo.setButtonCell(new PromptButtonCell<>("카테고리"));
    		}
    			
    		else {
    			String name = search_field.getText();	
    			String keyword = keyword_field.getText();
    			String category = "";
    			if(category_combo.getValue() != null)
    				category = category_combo.getValue();
    			String year = "";
    			if(year_spinner.getValue() != "생산년도")
    				year = year_spinner.getValue().toString();
    			
    			searchdoc = new SearchDoc();
    			

    			if(searchdoc.isEmptyDirectory())
    			{
    				messageDialog("등록된 파일이 없습니다.");
    			}
    			else
    			{
    			button_adder(searchdoc.booleancontent(name, keyword, category, year));
    			
    			search_field.setText("");
    			keyword_field.setText("");
    			initSpinner();	
    		    category_combo.getSelectionModel().clearSelection();
    		    category_combo.setButtonCell(new PromptButtonCell<>("카테고리"));
    			}
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
 
 private void messageDialog(String message) {
	 
     Alert alert = new Alert(AlertType.INFORMATION);
     alert.setTitle("알림");
     alert.setHeaderText(null);
     alert.setContentText(message);
     alert.showAndWait();
 }
 
 
	private void button_adder(ArrayList <String> ListA) {
		
		if(ListA.isEmpty())
		{
			messageDialog("검색된 파일이 없습니다.");
		}
		
		data = FXCollections.observableArrayList();
		for (Object object : ListA) {
			String element = (String) object;
		    data.add(element);
		    }
		
		 items_listview.setItems(data);

		 items_listview.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            return new AttachmentListCell();
	        }
	    });

		 
		}
	


	
 
}