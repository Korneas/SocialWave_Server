package serial;

public class Wave {
	
	private int postId;
	private String autor;
	
	public Wave(int postId, String autor) {
		super();
		this.postId = postId;
		this.autor = autor;
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

}
