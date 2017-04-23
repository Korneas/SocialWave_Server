package serial;

public class Post {

	private int postId, waves;
	private String tipo, titulo, autor;
	private byte[] archivo;

	public Post(int postId, int waves, String tipo, String titulo, byte[] archivo) {
		super();
		this.postId = postId;
		this.waves = waves;
		this.tipo = tipo;
		this.titulo = titulo;
		this.archivo = archivo;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getWaves() {
		return waves;
	}

	public void setWaves(int waves) {
		this.waves = waves;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

}
