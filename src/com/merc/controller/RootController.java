package com.merc.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileSystemView;

import com.merc.core.SearchDoc;
import com.merc.ui.DeleteFile;
import com.merc.ui.PromptButtonCell;
import com.merc.ui.RegisterFile;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.control.Alert.AlertType;

public class RootController implements Initializable {
	
@FXML TextField search_field;
@FXML TextField keyword_field;
@FXML private ComboBox<String> category_combo;
@FXML private Spinner<Integer> year_spinner;
@FXML private ListView<String> items_listview;


ObservableList<String> data = FXCollections.observableArrayList(
        "a.msg", "a1.msg", "b.txt", "c.pdf", 
        "d.html", "e.png", "f.zip",
        "g.docx", "h.xlsx", "i.pptx");


SearchDoc searchdoc;


 
 @Override
 public void initialize(URL location, ResourceBundle resources) {
	 
	 search_field.setPromptText("검색할 단어를 입력하세요.");
	 keyword_field.setPromptText("키워드");
	 initSpinner();
	 
	 items_listview.setItems(data);

	 items_listview.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
         @Override
         public ListCell<String> call(ListView<String> list) {
             return new AttachmentListCell();
         }
     });

     
     
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
    		    category_combo.getSelectionModel().clearSelection();
    		    category_combo.setButtonCell(new PromptButtonCell<>("카테고리"));
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
 
	static HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<String, Image>();

	
	   private static class AttachmentListCell extends ListCell<String> {
	        @Override
	        public void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	                setText(null);
	            } else {
	                Image fxImage = getFileIcon(item);
	                ImageView imageView = new ImageView(fxImage);
	                setGraphic(imageView);
	                setText(item);
	            }
	        }
	    }
	   
	    private static Image getFileIcon(String fname) {
	        final String ext = getFileExt(fname);

	        Image fileIcon = mapOfFileExtToSmallIcon.get(ext);
	        if (fileIcon == null) {

	            javax.swing.Icon jswingIcon = null; 

	            File file = new File(fname);
	            if (file.exists()) {
	                jswingIcon = getJSwingIconFromFileSystem(file);
	            }
	            else {
	                File tempFile = null;
	                try {
	                    tempFile = File.createTempFile("icon", ext);
	                    jswingIcon = getJSwingIconFromFileSystem(tempFile);
	                }
	                catch (IOException ignored) {
	                    // Cannot create temporary file. 
	                }
	                finally {
	                    if (tempFile != null) tempFile.delete();
	                }
	            }

	            if (jswingIcon != null) {
	                fileIcon = jswingIconToImage(jswingIcon);
	                mapOfFileExtToSmallIcon.put(ext, fileIcon);
	            }
	        }

	        return fileIcon;
	    }
	    
	    private static String getFileExt(String fname) {
	        String ext = ".";
	        int p = fname.lastIndexOf('.');
	        if (p >= 0) {
	            ext = fname.substring(p);
	        }
	        return ext.toLowerCase();
	    }
	    
	    
	    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

	        // Windows {
	        FileSystemView view = FileSystemView.getFileSystemView();
	        javax.swing.Icon icon = view.getSystemIcon(file);
	        // }

	        // OS X {
	        //final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
	        //javax.swing.Icon icon = fc.getUI().getFileView(fc).getIcon(file);
	        // }

	        return icon;
	    }
	    
	    
	    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
	        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
	                BufferedImage.TYPE_INT_ARGB);
	        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
	        return SwingFXUtils.toFXImage(bufferedImage, null);
	    }
}