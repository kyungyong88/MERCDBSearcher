package com.merc.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteFile {

	 public DeleteFile() throws Exception {
		 Parent root = FXMLLoader.load(getClass().getResource("delete.fxml"));
		 Scene scene = new Scene(root);
		 Stage subStage = new Stage();
		 subStage.setTitle("ªË¡¶");
		 subStage.setScene(scene);
		 subStage.setResizable(false);
		 subStage.initModality(Modality.APPLICATION_MODAL);
		 subStage.show();
		 }
	 
}
