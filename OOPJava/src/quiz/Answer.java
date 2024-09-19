package quiz;

import java.io.Serializable;

public class Answer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 921626524755879901L;
	private String content;

	public Answer(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
