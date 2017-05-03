package serial;

import java.io.Serializable;

public class Usuario implements Serializable{

	private String apodo,pass,correo;

	public Usuario(String apodo, String pass, String correo) {
		super();
		this.apodo = apodo;
		this.pass = pass;
		this.correo = correo;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}