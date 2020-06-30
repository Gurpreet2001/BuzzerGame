package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Frage;
import model.Player;

public class ScoreBoardController {
	@FXML
	private Button btnNext;
	@FXML
	private Label lblScore1;
	@FXML
	private Label lblScore2;
	@FXML
	private Label lblScore3;
	@FXML
	private Label lblPlayerName1;
	@FXML
	private Label lblPlayerName2;
	@FXML
	private Label lblPlayerName3;
	@FXML
	private Label lblThirdScore;

	

	private Stage stage;
	private ArrayList<Frage> fragen;
	private ArrayList<Player> players;
	private ArrayList<Integer> usedQuestions;
	private int round;
	
	public void init() {
		Collections.sort(players,new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				if(o1.getScore() < o2.getScore()) {
					return 1;
				}
				return -1;
			}
		});
		
		for(Player p : players) {

			if(lblScore1.getText().isEmpty()) {
				lblPlayerName1.setText("Player " + p.getPlayerID());
				lblScore1.setText(""+p.getScore());
			} 
			else if(lblScore2.getText().isEmpty()) {
				lblPlayerName2.setText("Player " + p.getPlayerID());
				lblScore2.setText(""+p.getScore());
			} 			
			else if(lblScore3.getText().isEmpty()) {
				lblPlayerName3.setText("Player " + p.getPlayerID());
				lblScore3.setText(""+p.getScore());
				lblThirdScore.setText("3.");
			} 
		}
		
	}
	
	public void next() {		
		try {		
			FXMLLoader questionLoader = new FXMLLoader(Main.class.getResource("../view/Question.fxml"));
			Scene questionScene = new Scene(questionLoader.load());
			QuestionController questionController = questionLoader.getController();
			questionController.setFragen(fragen);
			questionController.setPlayers(players);
			questionController.setRound(round);
			questionController.setUsedQuestions(usedQuestions);
			questionController.init();
			questionController.setStage(stage);
			stage.setScene(questionScene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setFragen(ArrayList<Frage> fragen) {
		this.fragen = fragen;
		
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;		
	}

	public void setRound(int round) {
			this.round = round;
	}

	public void setUsedQuestions(ArrayList<Integer> usedQuestions) {
		this.usedQuestions = usedQuestions;		
	}
}
