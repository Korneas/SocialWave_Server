package sw_server;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logica {
	
	private PApplet app;
	private Servidor server;
	
	private int id;
	private PFont fuente;
	private PImage[] elementos;

	public Logica(PApplet app) {
		super();
		this.app = app;
		
//		fuente = app.loadFont("Montserrat.ttf");
		
		elementos = new PImage[10];
		
//		elementos[0] = app.loadImage("");
	}
	
	public void ejecutar(){
		
	}
}
