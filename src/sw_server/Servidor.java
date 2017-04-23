package sw_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (life) {
			try {
				System.out.println("Waiting for users...");
				users.add(new Control(sS.accept(), this));
				System.out.println("New user is: " + users.size());
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

	}

}
