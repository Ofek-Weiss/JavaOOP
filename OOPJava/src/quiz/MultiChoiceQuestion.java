package quiz;

import java.util.Arrays;

public class MultiChoiceQuestion extends Question {

	private QuestionAnswer[] answers;


	public MultiChoiceQuestion(String content,QuestionDifficulty questionDifficulty ,QuestionAnswer[] answers) {
		super(content, questionDifficulty);
		this.answers = answers;
	}
	

	public MultiChoiceQuestion(String content,QuestionDifficulty questionDifficulty ) {
		super(content,questionDifficulty);
		this.answers = new QuestionAnswer[0];
	}
	

	//@Override
	public void addQuestionAnswer(QuestionAnswer questionAnswer) {
		QuestionAnswer[] resizedAnswers = Arrays.copyOf(answers, answers.length + 1);
		resizedAnswers[answers.length] = questionAnswer;
		answers = resizedAnswers;
	}

	//@Override
	public void deleteQuestionAnswer(int answerIndexToDelete) {
		int newLength = answers.length - 1;
		QuestionAnswer[] newQuestionAnswers = new QuestionAnswer[newLength];

		for (int i = 0, j = 0; i < answers.length; i++) {
			if (i != answerIndexToDelete) {
				newQuestionAnswers[j++] = answers[i];
			}
		}

		answers = newQuestionAnswers;
	}


	@Override
	public QuestionAnswer[] getAnswers() {
		return answers;
	}



}

