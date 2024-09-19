package quiz;

import java.io.Serializable;

public class QuestionAnswer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6543254610565498269L;
	private Answer answer;
    private boolean isCorrect;

    public QuestionAnswer(Answer answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public Answer getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
	
	
}
