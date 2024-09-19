package quiz;

import quiz.Question.QuestionDifficulty;

public class OpenQuestion extends Question {

	protected String answer;
	
	public OpenQuestion(String content, QuestionDifficulty questionDifficulty ,String answer) {
		super(content,questionDifficulty );
		this.answer = answer;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	

	
	
	
}
