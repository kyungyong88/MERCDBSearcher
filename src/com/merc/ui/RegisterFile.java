package com.merc.ui;


import com.merc.controller.RegisterController;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegisterFile {
				
			

	 public RegisterFile() throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
	    Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage subStage = new Stage();
		subStage.setTitle("µî·Ï");
		subStage.setScene(scene);
		subStage.setResizable(false);
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.show();
		
		
		RegisterController controller = loader.getController();
		controller.setSubStage(subStage);
		
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	subStage.close();
            }
        });   
        
        
		}
	 

	 
	}