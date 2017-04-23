package sw_server;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logica {

	private PApplet app;
	private Servidor server;
	private DatabaseManager dM;

	private int id;
	private PFont fuente;
	private PImage[] elementos;

	public Logica(PApplet app) {
		super();
		this.app = app;

		server = new Servidor();
		dM = new DatabaseManager("data/info.xml");

//		fuente = app.createFont("Montserrat.ttf", 40);

		elementos = new PImage[10];

		// elementos[0] = app.loadImage("");
	}

	public void ejecutar() {

	}

	public void exit() {
		dM.guardar();
	}
}
