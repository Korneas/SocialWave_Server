package sw_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import processing.data.XML;
import serial.Comentario;
import serial.Post;
import serial.Usuario;
import serial.Wave;

public class DatabaseManager {

	private XML data;
	private File fl;
	private static DatabaseManager ref;

	private ArrayList<Post> posts;
	private ArrayList<Usuario> users;
	private ArrayList<Wave> waves;
	private ArrayList<Comentario> comment;

	/**
	 * Constructor del DatabaseManager con el que se carga el archivo .xml y si este no existe entonces se crea
	 * @param ruta
	 */
	public DatabaseManager(String ruta) {
		fl = new File(ruta);

		posts = new ArrayList<Post>();
		users = new ArrayList<Usuario>();
		waves = new ArrayList<Wave>();
		comment = new ArrayList<Comentario>();

		if (fl.exists() && fl.isFile()) {
			System.out.println("Archivo .xml cargado");
			try {
				data = new XML(fl);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Achivo .xml creado");
			try {
				data = XML.parse("<information></information>");
				data.addChild("usuarios");
				data.addChild("comentarios");
				data.addChild("waves");
				data.addChild("posts");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para el uso del patro Singleton
	 * @return
	 */
	public static DatabaseManager getInstance() {
		if (ref == null) {
			ref = new DatabaseManager("data/info.xml");
		}
		return ref;
	}

	/**
	 * Metodo para agregar objetos tantos al ArrayList de estos como a el archivo .xml
	 * @param o
	 */
	public synchronized void agregar(Object o) {
		if (o instanceof Comentario) {

			XML com = data.getChild("comentarios");

			Comentario c = (Comentario) o;

			int postId = c.getPostId();
			String apodo = c.getApodo();
			String texto = c.getTexto();

			XML newComment = com.addChild("comment");

			newComment.setInt("postId", postId);
			newComment.setString("apodo", apodo);
			newComment.setString("texto", texto);

			comment.add(new Comentario(postId, apodo, texto));

		} else if (o instanceof Post) {

			XML us = data.getChild("posts");

			Post p = (Post) o;

			String autor = p.getAutor();
			String msg = p.getMsg();
			int tipo = p.getTipo();

			String ruta = "";
			String adjunto = "";

			if (tipo != 0) {
				String extension = "";
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
				ruta = "dataGuardada/" + extension + autor+"_"+p.getName()+adjunto;
			}
			
			int postId = p.getId();
			String name = p.getName();

			XML newPost = us.addChild("post");

			newPost.setString("autor", autor);
			newPost.setString("msg", msg);
			newPost.setString("ruta", ruta);
			newPost.setString("name", name);
			newPost.setInt("postId", postId);
			newPost.setInt("tipo", tipo);

			posts.add(new Post(autor, msg, name, tipo, postId, p.getFile()));

		} else if (o instanceof Usuario) {

			XML us = data.getChild("usuarios");

			Usuario u = (Usuario) o;

			String apodo = u.getApodo();
			String pass = u.getPass();
			String correo = u.getCorreo();

			XML newUser = us.addChild("user");

			newUser.setString("apodo", apodo);
			newUser.setString("pass", pass);
			newUser.setString("correo", correo);

			users.add(new Usuario(apodo, pass, correo));

		} else if (o instanceof Wave) {

		}
	}

	public void guardar() {
		data.save(fl);
	}

	/**
	 * Se buscan en el xml los usuarios registrados para cargarlos al arraylist
	 * @return
	 */
	public synchronized ArrayList<Usuario> getUsuarios() {
		XML usuarios = data.getChild("usuarios");
		XML[] users_all = usuarios.getChildren("user");

		if (users.isEmpty()) {
			for (int i = 0; i < users_all.length; i++) {
				String name = users_all[i].getString("apodo");
				String pass = users_all[i].getString("pass");
				String mail = users_all[i].getString("correo");
				users.add(new Usuario(name, pass, mail));
			}
		}
		return users;
	}

	public ArrayList<Post> getPosts() {
		XML posteados = data.getChild("posts");
		XML[] post_all = posteados.getChildren("post");

		if (posts.isEmpty()) {
			for (int i = 0; i < post_all.length; i++) {
				String autor = post_all[i].getString("autor");
				String msg = post_all[i].getString("msg");
				String name = post_all[i].getString("name");
				int tipo = post_all[i].getInt("tipo");
				int postId = post_all[i].getInt("postId");

				String ruta = post_all[i].getString("ruta");
				byte[] filePost = new byte[0];
				if (tipo != 0) {
					if (ruta != null) {
						try {
							File archivo = new File(ruta);
							FileInputStream inFile = new FileInputStream(archivo);
							byte[] buf = new byte[inFile.available()];
							inFile.read(buf);
							inFile.close();
							filePost = buf;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				posts.add(new Post(autor, msg, name, tipo, postId, filePost));
			}
		}
		return posts;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public ArrayList<Comentario> getComment() {
		return comment;
	}

}
