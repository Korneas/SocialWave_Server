package sw_server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observer;

public class Control implements Runnable {
	private Socket s;
	private Observer boss;

	public Control(Socket s, Observer boss) {
		this.s = s;
		this.boss = boss;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				recibir();
				recibirArchivos();
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void enviar(Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(o);
		System.out.println("Cliente: Se envio: " + o.getClass());
	}

	public void actualizar() {

	}

	public void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object recibido = in.readObject();
		boss.update(null, recibido);
	}

	private byte[] cargarArchivo(File fl) throws IOException {
		FileInputStream in = new FileInputStream(fl);
		byte[] buffer = new byte[in.available()];
		in.read(buffer);
		in.close();
		return null;
	}

	private File[] cargarMusica() {
		File audioDoc = new File("data/audio");
		File[] audioArray = audioDoc.listFiles();

		if (audioArray != null) {
			System.out.println("Archivos de audio encontrados: " + audioArray.length);
		} else {
			System.out.println("No hay archivos de audio actualmente");
		}
		return audioArray;
	}

	private File[] cargarImagen() {
		File imageDoc = new File("data/audio");
		File[] imageArray = imageDoc.listFiles();

		if (imageArray != null) {
			System.out.println("Archivos de imagen encontrados: " + imageArray.length);
		} else {
			System.out.println("No hay archivos de imagen actualmente");
		}
		return imageArray;
	}

	private File[] cargarArchivos() {
		File archiveDoc = new File("data/audio");
		File[] archiveArray = archiveDoc.listFiles();

		if (archiveArray != null) {
			System.out.println("Archivos encontrados: " + archiveArray.length);
		} else {
			System.out.println("No hay archivos actualmente");
		}
		return archiveArray;
	}

	private void recibirArchivos() throws IOException {
		DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
		int numArchivos = in.readInt();
		for (int i = 0; i < numArchivos; i++) {
			String nombre = in.readUTF();
			int size = in.readInt();
			byte[] buffer = new byte[size];
			int j = 0;
			while (j < size) {
				buffer[j] = in.readByte();
				j++;
			}
			guardarArchivo(nombre, buffer);
		}
	}

	private void guardarArchivo(String name, byte[] buffer) throws IOException {
		try {
			File saveDoc = new File("dataGuardada/" + name);
			saveDoc.createNewFile();
			FileOutputStream out = new FileOutputStream(saveDoc);
			out.write(buffer);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
