package com.merc.ui;

import com.merc.controller.DeleteController;
import com.merc.controller.RegisterController;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DeleteFile {

	 public DeleteFile() throws Exception {
		 
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("delete.fxml"));
	    Parent root = loader.load();
	    Scene scene = new Scene(root);
		Stage subStage = new Stage();
		subStage.setTitle("ªË¡¶");
		subStage.setScene(scene);
		subStage.setResizable(false);
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.show();
		 
		DeleteController controller = loader.getController();
		controller.setSubStage(subStage);
		
		
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	subStage.close();
            }
        });   
		 }
	 
}
