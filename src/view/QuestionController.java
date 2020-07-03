package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Frage;
import model.Player;

public class QuestionController{
	@FXML
	private Label lblFrage;
	@FXML
	private Label lblTime;
	@FXML
	private Label lblA1;
	@FXML
	private Label lblA2;
	@FXML
	private Label lblA3;
	@FXML
	private Label lblRound;
	
	private final static int TIMEOUT = 20;
	private final static int ROUNDS = 5;
	
	private ArrayList<Frage> fragen;
	private ArrayList<Player> players;
	private ArrayList<Integer> usedQuestions;
	private long roundStart;
	private Timer timer;
	private IntegerProperty restzeit;
	private Stage stage;
	private Frage currentFrage;
	private int round;
	private int answerCounter;
	

	public void setFragen(ArrayList<Frage> fragen) {
		this.fragen = fragen;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public IntegerProperty getRestzeit() {
		if (restzeit == null)
			restzeit = new SimpleIntegerProperty();
		return restzeit;
	}

	public void init() {
		if(usedQuestions == null) {
			usedQuestions = new ArrayList<Integer>();
		}
		round++;	
		answerCounter = 0;
		lblRound.setText("Runde: " + round);
		displayRandomFrage();

		roundStart = new Date().getTime();
		timer = new Timer();
		timer.scheduleAtFixedRate(tTask, 0, 1000);
		
		for(Player p : players) {
			p.playerStage.show();
			PlayerGuiController playerController = p.getController();
			playerController.init();
			ChangeListener<Number> listener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					onPlayerReturn(p, (int) newValue);
					playerController.antwort().removeListener(this);
					playerController.antwort().set(0);
				}
			};
			playerController.antwort().addListener(listener);
		}
		
	}
	
	public void onPlayerReturn(Player p, int value) {
		p.antwort().set(value);
        if(value == currentFrage.getAntwort()) {
        	p.addScore(restzeit.get() * 50);
        }
        p.playerStage.close();
        answerCounter++;
        
        
        if(answerCounter == players.size()) {
        	endRound();
        }        
	}
	
	TimerTask tTask = new TimerTask() {
		@Override
		public void run() {
			long deltaT = (new Date().getTime()-roundStart)/1000;
			getRestzeit().setValue((int)TIMEOUT-deltaT);
			Runnable updateRestzeit = new Runnable() {
				@Override
				public void run() {
					lblTime.setText("Zeit: "+getRestzeit().getValue());
				}		
			};
			Platform.runLater(updateRestzeit);
			if(getRestzeit().getValue() <= 0) {
				Runnable endRound = new Runnable() {
					@Override
					public void run() {
						endRound();
					}		
				};
				Platform.runLater(endRound);		
			}
		}
	};

	private void endRound() {
		
		
		timer.cancel();
		for(Player p : players) {
			//System.out.println("Player id: " + p.getPlayerID() + " || " + p.getScore());
			PlayerGuiController playerController = p.getController();;
			
		}
		if(round == ROUNDS) {
			openPodiumGui();
		} else {
			openScoreBoardGui();	
		}
		
		
	}

	private void openPodiumGui() {

		try {
			FXMLLoader podiumLoader = new FXMLLoader(this.getClass().getResource("Podium.fxml"));
			Scene podiumScene = new Scene(podiumLoader.load());
			PodiumController podiumController = podiumLoader.getController();
			podiumController.setPlayers(players);
			podiumController.init();
			stage.setScene(podiumScene);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void openScoreBoardGui() {
		try {
			FXMLLoader scoreBoardLoader = new FXMLLoader(Main.class.getResource("../view/ScoreBoard.fxml"));
			Scene scoreBoardScene= new Scene(scoreBoardLoader.load());
			ScoreBoardController scoreBoardController = scoreBoardLoader.getController();
			scoreBoardController.setStage(this.stage);
			scoreBoardController.setFragen(fragen);
			scoreBoardController.setUsedQuestions(usedQuestions);
			scoreBoardController.setPlayers(players);
			scoreBoardController.setRound(round);
			scoreBoardController.init();
			stage.setFullScreen(true);
			stage.setScene(scoreBoardScene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}

	public void setStage(Stage stage) {
		this.stage = stage;	
	}
	
	
	public void displayRandomFrage() {
		Random random = new Random();
		int frageIndex = random.nextInt(fragen.size());
		while(usedQuestions.contains(frageIndex)) {
			frageIndex = random.nextInt(fragen.size());
		}
		usedQuestions.add(frageIndex);
		currentFrage = fragen.get(frageIndex);
		lblFrage.setText(currentFrage.getFrage());
		lblA1.setText(currentFrage.getAntworten().get(0));
		lblA2.setText(currentFrage.getAntworten().get(1));
		lblA3.setText(currentFrage.getAntworten().get(2));
	}

	public void setRound(int round) {
		this.round = round;	
	}

	public void setUsedQuestions(ArrayList<Integer> usedQuestions) {
		this.usedQuestions = usedQuestions;	
	}
}
