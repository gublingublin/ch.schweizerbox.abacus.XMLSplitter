package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.DesignController;

public class Listener {

	
	@FXML private TextField TF_Quelldatei;
	
	
	
	public void listener(){
		
		
		InvalidationListener iv = (evt) -> {
			if(!TF_Quelldatei.isFocused()){
				System.out.println("testfocus");
			}
		};
		System.out.println("focus läuft");
		TF_Quelldatei.focusedProperty().addListener(
				new WeakInvalidationListener(iv)
				);
		
		
		}
	
	
	
	
	
}
