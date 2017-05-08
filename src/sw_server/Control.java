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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import serial.Confirmacion;
import serial.Post;
import serial.Usuario;
import serial.Validacion;

public class Control extends Observable implements Runnable {
	private Socket s;
	private Observer boss;
	private boolean life = true;
	private int id;

	public Control(Socket s, Observer boss, int id) {
		this.s = s;
		this.boss = boss;
		this.id = id;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (life) {
			try {
				recibir();
				Thread.sleep(100);
			} catch (IOException e) {
				System.out.println("Problema con cliente " + id);
				setChanged();
				boss.update(this, "finConexion");
				life = false;
				clearChanged();
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
		System.out.println("Servidor: Se envio: " + o.getClass());
	}

	public void actualizar() throws IOException {
		ArrayList<Post> post_all = DatabaseManager.getInstance().getPosts();
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(post_all);
		System.out.println("Servidor: Se actualizo el home | Numero de posts: "+post_all.size());
	}

	private void clasificar(Object o) {
		if (o instanceof Usuario) {
			ArrayList<Usuario> users = DatabaseManager.getInstance().getUsuarios();
			Usuario u = (Usuario) o;
			int totalUsers = 0;

			for (int i = 0; i < users.size(); i++) {
				if (!users.get(i).getApodo().contentEquals(u.getApodo())) {
					totalUsers++;
				}
			}

			if (totalUsers == users.size()) {
				DatabaseManager.getInstance().agregar(u);
				try {
					enviar(new Validacion(true, "registro"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					enviar(new Validacion(false, "registro"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (o instanceof Confirmacion) {
			ArrayList<Usuario> users = DatabaseManager.getInstance().getUsuarios();
			Confirmacion c = (Confirmacion) o;
			boolean exist = false;

			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getApodo().contentEquals(c.getName())
						&& users.get(i).getPass().contentEquals(c.getPass())) {
					exist = true;
				}
			}

			if (exist) {
				try {
					enviar(new Validacion(true, "log"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					enviar(new Validacion(false, "log"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (o instanceof Post) {
			Post p = (Post) o;
			DatabaseManager.getInstance().agregar(p);

			if (p.getTipo() != 0) {
				recibirArchivos(p);
			}

			try {
				enviar(new Validacion(true, "posteado"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(o instanceof Validacion){
			Validacion v = (Validacion) o;
			
			if(v.isCheck()){
				if(v.getType().contains("actualizar"));
				try {
					actualizar();
				} catch (IOException e) {
					System.out.println("No se pudo enviar actualizacion");
				}
			}
		}
	}

	public void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object recibido = in.readObject();
		clasificar(recibido);
		System.out.println("Cliente: Llego"+recibido.getClass());
		boss.update(null, recibido);
	}

	private void recibirArchivos(Post in) {
		String nombre = in.getAutor() + "_" + in.getName();
		byte[] buffer = in.getFile();

		try {
			guardarArchivo(nombre, buffer, in.getTipo());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void guardarArchivo(String name, byte[] buffer, int tipo) throws IOException {
		String extension = "";
		String adjunto = "";
		switch (tipo) {
		case 0:
			break;
		case 1:
			extension = "img/";
			adjunto = ".jpeg";
			break;
		case 2:
			extension = "files/";
			break;
		case 3:
			extension = "music/";
			break;
		}
		System.out.println("Tengo data para guardarla en " + extension);
		try {
			File saveDoc = new File("dataGuardada/" + extension + name + adjunto);
			saveDoc.createNewFile();
			FileOutputStream out = new FileOutputStream(saveDoc);
			out.write(buffer);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
