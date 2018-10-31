package com.merc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.merc.core.SearchDoc;
import com.merc.ui.DeleteFile;
import com.merc.ui.RegisterFile;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RootController implements Initializable {
	
@FXML TextField search_field;
@FXML TextField keyword_field;
@FXML private ComboBox<String> category_combo;
@FXML private Spinner<Integer> year_spinner;

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
    			messageDialog("검색어를 입력하세요");
    		}
    			
    		else {
    			String name = search_field.getText();	
    			String keyword = keyword_field.getText();
    			String category = "";
    			if(category_combo.getValue() != null)
    				category = category_combo.getValue();
    			String year = "";
    			if(year_spinner.getValue() != null)
    				year = year_spinner.getValue().toString();
    			
    			searchdoc = new SearchDoc();
    			
    			//button_adder(searchdoc.searchcontent(name, keyword, category, year));
    			button_adder(searchdoc.booleancontent(name, keyword, category, year));
    			
    			search_field.setText("");
    			keyword_field.setText("");
    			initSpinner();	
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
 
 private void messageDialog(String message) {
	 
     Alert alert = new Alert(AlertType.INFORMATION);
     alert.setTitle("알림");
     alert.setHeaderText(null);
     alert.setContentText(message);
     alert.showAndWait();
 }
 
 
	private void button_adder(ArrayList ListA) {
		
/*		lower_panel.removeAll();
		
        for(Object object : ListA){ 
        	String element = (String) object;
    		JButton button = new JButton(element);
    		button.setBackground(UIManager.getColor("Button.background"));
    		lower_panel.add(button);
    		button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
            		Desktop d = Desktop.getDesktop();
            		try {
    					d.open(new File(element));
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                }
            });
        } 
        
		
		lower_panel.validate();
		lower_panel.repaint();*/
	}
 

}