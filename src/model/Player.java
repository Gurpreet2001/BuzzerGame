package model;

import java.io.IOException;

import application.Main;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.PlayerGuiController;

public class Player {
	private int playerID;


	private int score;
	private IntegerProperty antwort;
	public Stage playerStage;
	PlayerGuiController playerController;
	
	public Player(int playerID) {
		
		this.playerID = playerID;
		
		try {
			playerStage = new Stage();
			FXMLLoader playerLoader = new FXMLLoader(Main.class.getResource("../view/PlayerGui.fxml"));
			Scene scene = new Scene(playerLoader.load());
			playerController = playerLoader.getController();
			playerController.setPlayerId(this.playerID);
			playerController.init();
			playerStage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		score = 0;
	}
	
	public IntegerProperty antwort() {
		if(antwort == null) {
			antwort = new SimpleIntegerProperty();
		}		
		return antwort;	
	}
	
	public void addScore(int amount) {
		if(amount > 0) {
			this.score += amount;
		}
	}
	
	public PlayerGuiController getController() {
		return playerController;
	}
	
	
	public int getScore() {
		return score;
	}
	
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

}
