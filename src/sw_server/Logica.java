package sw_server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import serial.Confirmacion;
import serial.Usuario;

public class Logica implements Observer {

	private PApplet app;
	private Servidor server;
	
	private PFont fuente;
	private PImage[] elementos;

	private ArrayList<Usuario> users;

	public Logica(PApplet app) {
		super();
		this.app = app;

		server = new Servidor();
		new Thread(server).start();
		server.addObserver(this);
		
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// fuente = app.createFont("Montserrat.ttf", 40);

		elementos = new PImage[10];

		// elementos[0] = app.loadImage("");

	}

	public void ejecutar() {

	}

	public void exit() {
		DatabaseManager.getInstance().guardar();
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}
