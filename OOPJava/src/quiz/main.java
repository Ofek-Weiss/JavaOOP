package quiz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import quiz.Question.QuestionDifficulty;

public class main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {	
		String filename = "quiz.dat";
		QuestionRepository repository = QuestionRepository.readRepository(filename);
		Answer[] allAnswers;

		Scanner scanner = new Scanner(System.in);
		int choice;
		do {
			System.out.println("Please select an option:");
			System.out.println("1. Print questions and answers");
			System.out.println("2. Add an answer");
			System.out.println("3: Add an answer to existing question");
			System.out.println("4: Add a new question");
			System.out.println("5: Delete an answer for a certain question");
			System.out.println("6: Delete a question");
			System.out.println("7: Create your own quiz:");
			System.out.println("8: Exit");

			choice = scanner.nextInt();
			scanner.nextLine(); // consume the newline character after reading an int

			switch (choice) {
			case 1:
				repository.printQuestionsAndAnswers();
				break;
			case 2:
				System.out.println("Enter the content of the new answer:");
				String answerContent = scanner.nextLine();
				Answer newAnswer = new Answer(answerContent);
				repository.addAnswer(newAnswer);
				System.out.println("Answer added successfully.");
				System.out.println();
				break;
			case 3:
				System.out.println("Which question do you want to add an answer to?");
				repository.printOnlyQuestions();
				int questionIndex = scanner.nextInt();
				scanner.nextLine(); // consume the newline character after reading an int
				if(repository.getQuestion(questionIndex)instanceof OpenQuestion) {
					System.out.println("Invalid choice (can not add an answer to an open question) \n");
					break;
				}

				if (questionIndex < 0 || questionIndex >= repository.getQuestionsCount()) {
					System.out.println("Invalid question index. \n");
					break;
				}

				MultiChoiceQuestion question = (MultiChoiceQuestion) repository.getQuestion(questionIndex);
				System.out.println("Select an answer to add to the question:");
				allAnswers = repository.getAllAnswers();
				QuestionAnswer[] questionAnswers = question.getAnswers();
				int availableAnswersCount = allAnswers.length - questionAnswers.length;
				int answerIndex = -1;
				for (int i = 0; i < allAnswers.length; i++) {
					boolean found = false;
					for (int j = 0; j < questionAnswers.length; j++) {
						if (allAnswers[i].equals(questionAnswers[j].getAnswer())) {
							found = true;
							break;
						}
					}
					if (!found) {
						availableAnswersCount++;
						if (availableAnswersCount > 1) {
							System.out.print(", ");
						}
						System.out.print(i + ". " + allAnswers[i].getContent());
						if (availableAnswersCount == 10) {
							break;
						}
						if (answerIndex == -1) {
							answerIndex = i;
						}
					}
				}
				System.out.println();

				if (availableAnswersCount == 0) {
					System.out.println("There are no available answers to add to this question. \n");
					break;
				}

				if (availableAnswersCount == 1) {
					System.out.println("There is only one available answer to add to this question. \n");
					break;
				}

				answerIndex = scanner.nextInt();
				scanner.nextLine(); // consume the newline character after reading an int

				if (answerIndex < 0 || answerIndex >= allAnswers.length) {
					System.out.println("Invalid answer index. \n");
					break;
				}
				System.out.println("Is your answer correct? (true/false)");
				boolean isCorrect = scanner.nextBoolean();
				Answer selectedAnswer = allAnswers[answerIndex];
				QuestionAnswer newQuestionAnswer = new QuestionAnswer(selectedAnswer, isCorrect);
				question.addQuestionAnswer(newQuestionAnswer);
				System.out.println("Answer added successfully to the question. \n");
				break;
			case 4:
				System.out.println("What type of question do you want to add? \npress 1 for MultiChoice or 0 for Open");
				// Prompt user for new question content
				int choiceForTypeOfQuestion = scanner.nextInt();
				if(choiceForTypeOfQuestion==1) {
					System.out.println("Enter the content of the new question:");

					String newQuestionContent = scanner.nextLine();

					newQuestionContent=scanner.nextLine();

					System.out.println("Enter the difficulty of the question: ");//HARD or MEDIUM or EASY


					String newQuestionDifficulty = scanner.next();


					MultiChoiceQuestion newQuestion = new MultiChoiceQuestion(newQuestionContent,QuestionDifficulty.valueOf(newQuestionDifficulty.toUpperCase()));
					newQuestion.setContent(newQuestionContent);

					// Add new Question object to question repository
					repository.addQuestion(newQuestion);


					System.out.println("New question added successfully. \n");
					break;
				}
				if(choiceForTypeOfQuestion==0) {
					System.out.println("Enter the content of the new question:");

					String newQuestionContent = scanner.nextLine();

					newQuestionContent = scanner.nextLine();

					System.out.println("Enter the difficulty of the question: ");

					String newQuestionDifficulty = scanner.next();

					scanner.nextLine();

					System.out.println("Enter the content of the answer");

					String newAnswerContent = scanner.nextLine();
					// Create new Question object
					OpenQuestion newQuestion = new OpenQuestion(newQuestionContent,QuestionDifficulty.valueOf(newQuestionDifficulty.toUpperCase()), newAnswerContent);
					newQuestion.setContent(newQuestionContent);
					newQuestion.setAnswer(newAnswerContent);

					// Add new Question object to question repository
					repository.addQuestion(newQuestion);

					System.out.println("New question added successfully. \n");
					break;
				}
				System.out.println("Invalid Input");
				break;	
			case 5:
				// Ask user for question index
				System.out.println("Enter the index of the question:");
				repository.printOnlyQuestions();
				int questionIndexToDeleteFrom = scanner.nextInt();
				scanner.nextLine(); // consume remaining new line character
				if(repository.getQuestion(questionIndexToDeleteFrom)instanceof OpenQuestion) {
					System.out.println("Invalid choice (can not delete an answer from an open question) \n");
					break;
				}

				// Check if the index is valid
				if (questionIndexToDeleteFrom < 0 || questionIndexToDeleteFrom >= repository.getQuestionsCount()) {
					System.out.println("Invalid question index \n");
					break;
				}

				// Ask user for answer index to delete
				System.out.println("Enter the index of the answer to delete:");
				int answerIndexToDelete = scanner.nextInt();
				scanner.nextLine(); // consume remaining new line character

				// Check if the index is valid
				MultiChoiceQuestion questionToDeleteFrom = (MultiChoiceQuestion) repository.getQuestion(questionIndexToDeleteFrom);
				if (answerIndexToDelete < 0 || answerIndexToDelete >= questionToDeleteFrom.getAnswers().length) {
					System.out.println("Invalid answer index \n");
					break;
				}

				// Delete the answer
				questionToDeleteFrom.deleteQuestionAnswer(answerIndexToDelete);
				System.out.println("Answer deleted successfully \n");						    
				break;
			case 6:
				System.out.println("Which question would you like to delete? (Enter index)");
				int questionIndexToDelete = scanner.nextInt();

				// Delete the question
				repository.deleteQuestion(questionIndexToDelete);
				System.out.println("Question deleted successfully \n");
				break;
			case 7:				
				System.out.println("Press 0 for Manual quiz or 1 for Automatic quiz");
				int numDecision = scanner.nextInt();
				if(numDecision == 0) {
					ManualQuiz mq = new ManualQuiz();	
					mq.createQuiz(repository);
				}
				if(numDecision == 1) {
					AutomaticQuiz aq = new AutomaticQuiz();
					aq.createQuiz(repository);
				}

				break;
			case 8:
				repository.saveQuiz(repository,filename);
				System.out.println("Exiting the program...");
				break;
			default:
				System.out.println("Invalid option selected.");
				break;
			}
		} while (choice != 8);

		scanner.close();

	}

	



}
