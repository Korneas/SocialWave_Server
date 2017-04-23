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
			try {
				data = XML.parse(
						"<usuarios></usuarios>\n<posteado></<posteado>\n<comentarios></comentarios>\n<waves></waves>");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
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

}
