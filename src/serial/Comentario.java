package serial;

public class Comentario {
	
	private int postId;
	private String apodo,texto;
	
	public Comentario(int postId, String apodo, String texto) {
		super();
		this.postId = postId;
		this.apodo = apodo;
		this.texto = texto;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
