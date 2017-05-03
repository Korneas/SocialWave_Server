package sw_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import serial.Usuario;

public class Servidor extends Observable implements Observer, Runnable {

	private static final int PORT = 5000;
	private ServerSocket sS;
	private boolean life;
	private ArrayList<Control> users;

	public Servidor() {

		life = true;
		users = new ArrayList<Control>();

		try {
			sS = new ServerSocket(PORT);
			System.out.println("Servidor iniciado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviar(Object o,int controlNum){
		
	}

	@Override
	public void run() {
		while (life) {
			try {
				System.out.println("Esperando...");
				users.add(new Control(sS.accept(), this, users.size() + 1));
				System.out.println("Nuevo usuario es: " + users.size());
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
		clearChanged();
		
//		users.get(0).enviar(null);
	}

}
