package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Frage;
import model.Player;

public class StartScreenController {
	@FXML
	private ImageView imgPlayer1R;
	@FXML
	private ImageView imgPlayer2R;
	@FXML
	private ImageView imgPlayer3R;
	@FXML
	private ImageView imgPlayer1NR;
	@FXML
	private ImageView imgPlayer2NR;
	@FXML
	private ImageView imgPlayer3NR;
	@FXML
	private Button btnAddPlayer;
	@FXML
	private Button btnRemovePlayer;
	
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private Stage stage;
	
	public void startGame() {	
		if(players.size() > 1) {
			openQuestionGui();
		} else {
			Alert a = new Alert(Alert.AlertType.INFORMATION,"Es braucht mindestens 2 Spieler um das Spiel zu starten");
			a.setTitle("Achtung!");
			a.setHeaderText("Achtung!");
			a.show();	
		}
		

	}
	
	public void addPlayer() {
		if(players.size() >= 0 && players.size() < 3) {
			players.add(new Player(players.size()+1));
			if(!imgPlayer1R.isVisible()) {
				imgPlayer1R.setVisible(true);
				imgPlayer1NR.setVisible(false);
			} else if(!imgPlayer2R.isVisible()) {
				imgPlayer2R.setVisible(true);	
				imgPlayer2NR.setVisible(false);
			} else {
				imgPlayer3R.setVisible(true);
				imgPlayer3NR.setVisible(false);
			}
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		
	}

	public void openQuestionGui(){
		try {
			FXMLLoader questionLoader = new FXMLLoader(Main.class.getResource("../view/Question.fxml"));
			Scene questionScene = new Scene(questionLoader.load());
			QuestionController questionController = questionLoader.getController();
			questionController.setFragen(getFragen());
			questionController.setPlayers(players);
			questionController.setStage(stage);
			questionController.init();
			stage.setFullScreen(true);			
			stage.setScene(questionScene);
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Frage> getFragen(){
		ArrayList<Frage> fragen = new ArrayList<Frage>();

        String csvFile = "src\\data\\fragen.csv";
        String line = "";
        BufferedReader br = null;
        String cvsSplitBy = ";";   
        try {
        	FileReader reader = new FileReader(csvFile);
        	br = new BufferedReader(reader);
        	
            while ((line = br.readLine()) != null) {
                String[] frage = line.split(cvsSplitBy);

                ArrayList<String> antworten = new ArrayList<String>();
                antworten.add(frage[1]);
                antworten.add(frage[2]);
                antworten.add(frage[3]);
                Frage f = new Frage(frage[0],antworten,Integer.parseInt(frage[4]));
                fragen.add(f);
            }
            
        } catch(Exception ex) {
        	
        } finally  {
        	try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
		
		return fragen;
	}


}
