package quiz;

import java.io.FileNotFoundException;

public class AutomaticQuiz implements Quizable{

	@Override
	public void createQuiz(QuestionRepository repository) {
		
		try {
			repository.createAutomaticQuiz();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (MoreThan10QuestionsCreatedException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
