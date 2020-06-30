package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PlayerGuiController {
	@FXML
	private Button btnA;
	@FXML
	private Button btnB;
	@FXML
	private Button btnC;
	@FXML
	private Label lblPlayerID;
	
	private int playerID;
	private IntegerProperty antwort;
	
	public void init() {
		lblPlayerID.setText("P"+playerID);
	}
	
	public IntegerProperty antwort() {
		if(antwort == null) {
			antwort = new SimpleIntegerProperty();
		}
		return antwort;
	}

	
	@FXML
	public void setAntwort() {
		if(btnA.isHover()) {
			antwort().set(1);
		}
		if(btnB.isHover()) {
			antwort().set(2);
		}
		if(btnC.isHover()) {
			antwort().set(3);
		}
	}

	public void setPlayerId(int playerID) {
		this.playerID = playerID;	
	}
	
}
