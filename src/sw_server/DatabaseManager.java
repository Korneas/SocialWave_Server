package sw_server;

import java.io.File;
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

	public static DatabaseManager getInstance() {
		if (ref == null) {
			ref = new DatabaseManager("data/info.xml");
		}
		return ref;
	}

	public void agregar(Object o) {
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

	public ArrayList<Usuario> getUsuarios() {
		return users;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public ArrayList<Comentario> getComment() {
		return comment;
	}

}
