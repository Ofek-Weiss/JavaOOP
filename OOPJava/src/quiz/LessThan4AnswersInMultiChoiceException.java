package quiz;

public class LessThan4AnswersInMultiChoiceException extends Exception {
	
	public LessThan4AnswersInMultiChoiceException() {
		super("Error: The minimum amount of answers is 4 \n");
	}
}
