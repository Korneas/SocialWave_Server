package sw_server;

import processing.core.PApplet;

public class MainServer extends PApplet{
	
	private Logica log;

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("sw_server.MainServer");
	}

	@Override
	public void settings() {
		size(300, 300);
	}

	@Override
	public void setup() {
		log = new Logica(this);
	}

	@Override
	public void draw() {
		background(0);
		log.ejecutar();
	}
	
	@Override
	public void exit() {
		log.exit();
		super.exit();
	}
}
