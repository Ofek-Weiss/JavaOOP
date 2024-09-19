package quiz;

public class MoreThan10QuestionsCreatedException extends Exception {
	
	public MoreThan10QuestionsCreatedException() {
		super("Error: The maximum questions in your exam is 10 \n");
	}
}
