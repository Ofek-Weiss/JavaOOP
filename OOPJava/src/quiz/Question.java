package quiz;

import java.io.Serializable;

public abstract class Question implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 7638218986882022269L;


	public enum QuestionDifficulty {HARD,MEDIUM,EASY};
	private static int ID = 1;
	private int id;
	protected QuestionDifficulty questionDifficulty;
	
	protected String content;
	private QuestionAnswer[] answers;
	
	public Question() {
		this.content = "";
		this.answers = new QuestionAnswer[0];
		this.id = ++ID;
		this.questionDifficulty = questionDifficulty;
	}

	public Question(String content, QuestionDifficulty questionDifficulty) {
		this.content = content;
		this.answers = answers;
		this.id = ++ID;
		this.questionDifficulty = questionDifficulty;
	}

	public String getContent() {
		return content;
	}

	public QuestionAnswer[] getAnswers() {
		return answers;
	}

	
	public void setContent(String content) {
		this.content = content;
	}
	

	

}
