package model;


import java.util.ArrayList;

import javafx.beans.property.StringProperty;

/**
 * Klasse für speicherung von Fragen, jede Frage hat 3 Antworten.
 * @author Raphael Stamm
 *
 */
public class Frage{

	
	private String frage;
	private ArrayList<String> antworten = new ArrayList<String>();
	private int antwort;
	
	public Frage(String frage, ArrayList<String> antworten, int antwort) {
		this.frage = frage;
		this.antworten = antworten;
		this.antwort = antwort;
	}
	
	public String getFrage() {
		return frage;
	}
	public ArrayList<String> getAntworten() {
		return antworten;
	}
	public int getAntwort() {
		return antwort;
	}
	
	
}
