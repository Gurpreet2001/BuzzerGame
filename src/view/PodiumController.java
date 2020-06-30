package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import model.Player;

public class PodiumController {
	@FXML
	private VBox VBoxPlace2;
	@FXML
	private Label lblPlace2Name;
	@FXML
	private Label lblPlace2Score;
	@FXML
	private VBox VBoxPlace1;
	@FXML
	private Label lblPlace1Name;
	@FXML
	private Label lblPlace1Score;
	@FXML
	private VBox VBoxPlace3;
	@FXML
	private Label lblPlace3Name;
	@FXML
	private Label lblPlace3Score;

	private ArrayList<Player> players;
	
	public void init() {
		switch(players.size()) {
		case 2 : 
			VBoxPlace1.setVisible(true);
			VBoxPlace2.setVisible(true);
			VBoxPlace3.setVisible(false);
			break;
		case 3 : 
			VBoxPlace1.setVisible(true);
			VBoxPlace2.setVisible(true);
			VBoxPlace3.setVisible(true);
			break;
		}
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

			if(lblPlace1Name.getText().isEmpty()) {
				lblPlace1Name.setText("Player " + p.getPlayerID());
				lblPlace1Score.setText(""+p.getScore());
			} 
			else if(lblPlace2Name.getText().isEmpty()) {
				lblPlace2Name.setText("Player " + p.getPlayerID());
				lblPlace2Score.setText(""+p.getScore());
			}
			else if(lblPlace3Name.getText().isEmpty()) {
				lblPlace3Name.setText("Player " + p.getPlayerID());
				lblPlace3Score.setText(""+p.getScore());
			}
		}
				
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
