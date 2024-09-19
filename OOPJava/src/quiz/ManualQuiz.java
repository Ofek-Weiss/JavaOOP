package quiz;

import java.io.FileNotFoundException;

public class ManualQuiz implements Quizable {

	@Override
	public void createQuiz(QuestionRepository repository) {
		try {
			repository.createQuiz();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (MoreThan10QuestionsCreatedException e) {
			System.out.println(e.getMessage());
		} catch (LessThan4AnswersInMultiChoiceException e) {
			System.out.println(e.getMessage());

		}
		
	}

}
