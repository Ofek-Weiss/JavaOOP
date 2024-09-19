package quiz;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import quiz.Question.QuestionDifficulty;

public class QuestionRepository implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7037168107632328873L;
	private String nameOfSubject;
	private Question[] questions;
	private Answer[] allAnswers;

	public QuestionRepository(String nameOfSubject,Question[] questions, Answer[] answers) {
		this.nameOfSubject = nameOfSubject;
		this.questions = questions;
		this.allAnswers = answers;
	}

	public void printOnlyQuestions()
	{
		for (int i = 0; i < questions.length; i++) {
			System.out.println(i+":\t"+questions[i].getContent());
			QuestionAnswer[] qAnswers = questions[i].getAnswers();
		}	
	}

	public void printQuestionsAndAnswers() {
		for (int i = 0, k=1; i < questions.length; i++) {
			System.out.println(k++ + ". " + questions[i].getContent());
			System.out.println();
			if (questions[i] instanceof MultiChoiceQuestion) {
				MultiChoiceQuestion multiChoiceQuestion = (MultiChoiceQuestion) questions[i];
				QuestionAnswer[] qAnswers = multiChoiceQuestion.getAnswers();
				for (int j = 0, m=1; j < qAnswers.length; j++) {
					Answer answer = qAnswers[j].getAnswer();
					System.out.println(m++ + ". "+ answer.getContent() + " " +  "(" + qAnswers[j].isCorrect() + ")");
				}
			} else if (questions[i] instanceof OpenQuestion) {
				OpenQuestion openQuestion = (OpenQuestion) questions[i];
				System.out.println(openQuestion.getAnswer());
			}
			System.out.println();
		}
	}



	public void addAnswer(Answer answer) {
		Answer[] newAllAnswers = Arrays.copyOf(allAnswers, allAnswers.length + 1);
		newAllAnswers[allAnswers.length] = answer;
		allAnswers = newAllAnswers;
	}

	public void addQuestion(Question question) {		
		Question[] resizedQuestions = Arrays.copyOf(questions, questions.length + 1);
		resizedQuestions[questions.length] = question;
		questions = resizedQuestions;

	}

	public void deleteQuestion(int questionIndex) {
		if (questionIndex >= 0 && questionIndex < questions.length) {
			// Create a new array with the length of the original array - 1
			Question[] newQuestions = Arrays.copyOf(questions, questions.length - 1);
			int j = 0;
			for (int i = 0; i < questions.length; i++) {
				if (i != questionIndex) {
					// Copy the question to the new array if it's not the one to be deleted
					newQuestions[j] = questions[i];
					j++;
				}
			}
			// Update the questions array to the new array
			questions = newQuestions;
		} else {
			System.out.println("Invalid question index.");
		}
	}


	public void createQuiz() throws FileNotFoundException, MoreThan10QuestionsCreatedException, LessThan4AnswersInMultiChoiceException {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		String text = date.format(formatter);
		File examFile= new File("Manual exam"+ text + ".txt");
		File solutionFile= new File("Manual solution" + text + ".txt");
		PrintWriter pwExam = new PrintWriter(examFile);
		PrintWriter pwSolution = new PrintWriter(solutionFile);
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many questions do you want in the test?");
		int numOfQuestions = scanner.nextInt();
		if(numOfQuestions>10) {
			throw new MoreThan10QuestionsCreatedException();
		}
		printOnlyQuestions();
		System.out.println("Enter desired question's index (-1 for finish):");
		int questionIndex = scanner.nextInt();
		int questionCount =1;
		while(questionIndex != -1 && numOfQuestions!=0) {
			numOfQuestions--;
			if(questionIndex<0 || questionIndex >= questions.length) {

				System.out.println("Invalid question id");
			}
			else {
				if(questions[questionIndex] instanceof MultiChoiceQuestion) {
					pwExam.write(questionCount + ": " + questions[questionIndex].getContent() + "\n");
					pwSolution.write(questionCount + ": " + questions[questionIndex].getContent() + "\n");				
					questionCount++;
					QuestionAnswer [] qa = questions[questionIndex].getAnswers();
					System.out.println("Question " + questionIndex + " available answers:");
					//counter for correct answers
					int counterForCorrectAnswer = 0;
					for (int i = 0; i < qa.length; i++) {
						if(qa[i].isCorrect()) {
							counterForCorrectAnswer++;
						}
					}

					for(int j=0;j<qa.length;j++)
					{
						if(counterForCorrectAnswer==1 && qa[j].isCorrect()) {
							pwSolution.write("The correct answer: " + qa[j].getAnswer().getContent() + "\n");
						}
						if(counterForCorrectAnswer>1) {
							pwSolution.write("The correct answer: There is more than one answer \n");
							counterForCorrectAnswer = -1;
						}
						if(counterForCorrectAnswer==0) {
							pwSolution.write("The correct answer: None of the above \n");
							counterForCorrectAnswer = -1;
						}

						System.out.println(j + " " + qa[j].getAnswer().getContent());
					}

					int count = 3;
					int answerIndex; 
					System.out.print("Enter desired answers numbers or -1 to finish");				
					answerIndex = scanner.nextInt();
					if(answerIndex<-1 || answerIndex >= qa.length) {
						System.out.println("Invalid index \n");
						break;
					}
					pwExam.write("\t1. None of the above \n");
					pwExam.write("\t2. There is more than one answer \n");
					while(answerIndex != -1)
					{

						pwExam.print("\t" + count + "." + qa[answerIndex].getAnswer().getContent());
						count++;
						pwExam.println();
						System.out.print("Enter desired answers numbers or -1 to finish");
						answerIndex = scanner.nextInt();
						if(answerIndex<-1 || answerIndex >= qa.length) {
							System.out.println("Invalid index");
							break;
						}

					}
					if(count-1<4) {
						throw new LessThan4AnswersInMultiChoiceException();
					}
				}
				if(questions[questionIndex] instanceof OpenQuestion) {
					pwExam.write(questionCount + ": " + questions[questionIndex].getContent() + "\n");
					pwSolution.write(questionCount + ": " + questions[questionIndex].getContent() + "\n");
					questionCount++;
					pwSolution.write(((OpenQuestion) questions[questionIndex]).getAnswer() + "\n");
				}

				if(numOfQuestions!=0) {
					printOnlyQuestions();
					System.out.println("Enter desired question's index (-1 for finish):");
					questionIndex = scanner.nextInt();
				}
			}
		}

		pwExam.close();
		pwSolution.close();
		System.out.println("Files were added sucessfully \n");


	}

	public void createAutomaticQuiz() throws MoreThan10QuestionsCreatedException, FileNotFoundException {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		String text = date.format(formatter);
		File examFile= new File("Automatic exam"+ text + ".txt");
		File solutionFile= new File("Automatic solution" + text + ".txt");
		PrintWriter pwExam = new PrintWriter(examFile);
		PrintWriter pwSolution = new PrintWriter(solutionFile);
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many questions do you want in the test?");
		int numOfQuestions = scanner.nextInt();		
		if(numOfQuestions>10) {
			throw new MoreThan10QuestionsCreatedException();			
		}

		boolean arr[] = new boolean[questions.length];//going to help us find used questions
		for (int i = 0; i < arr.length; i++) {
			arr[i] = false;
		}

		int counter = 0;
		int questionIndexCounter = 1;
		while(counter<numOfQuestions) {
			int questionNumber = new Random().nextInt(questions.length);
			if(arr[questionNumber]==false && questions[questionNumber] != null) {//check for used questions
				counter++;
				arr[questionNumber] = true;//used question
				if(questions[questionNumber] instanceof MultiChoiceQuestion) {
					QuestionAnswer [] qa = questions[questionNumber].getAnswers(); 		

					if(qa.length<4) {
						counter--;
						continue;//try another question
					}

					int counterForCorrectAnswer = 0;
					for (int i = 0; i < qa.length; i++) {
						if(qa[i].isCorrect()) {
							counterForCorrectAnswer++;
						}
					}


					if(counterForCorrectAnswer==0 || counterForCorrectAnswer==1) {
						pwExam.write(questionIndexCounter + ": " + questions[questionNumber].getContent() + "\n");
						pwExam.write("\t1. None of the above \n");
						pwSolution.write(questionIndexCounter + ": " + questions[questionNumber].getContent() + "\n");

						if(counterForCorrectAnswer==0) {

							pwSolution.write("\t1. The correct answer: None of the above \n");
						}
						boolean arrayOfAnswers[] = new boolean[qa.length];
						for (int i = 0; i < arrayOfAnswers.length; i++) {
							arrayOfAnswers[i] = false;
						}

						int i = 2;
						while( i < 6) {														
							int answerNumber = new Random().nextInt(qa.length);
							if(arrayOfAnswers[answerNumber]==false && qa[answerNumber] != null) {
								pwExam.write("\t" + i + ". " + qa[answerNumber].getAnswer().getContent() + "\n");
								if(qa[answerNumber].isCorrect()) {
									pwSolution.write("\t" + qa[answerNumber].getAnswer().getContent() + "\n");
								}
								i++;
								arrayOfAnswers[answerNumber] = true;
							}										
						}
					}
					else {
						counter--;
						continue;//try another question
					}

					questionIndexCounter++;	
				}
				if(questions[questionNumber] instanceof OpenQuestion) {
					pwExam.write(questionIndexCounter+ ": " + questions[questionNumber].getContent() + "\n");
					pwSolution.write(questionIndexCounter+ ": " + questions[questionNumber].getContent() + "\n");
					pwSolution.write(((OpenQuestion) questions[questionNumber]).getAnswer() + "\n");

					questionIndexCounter++;
				}

			}

		}

		pwExam.close();
		pwSolution.close();
		System.out.println("Files were added sucessfully \n");
	}

	public Answer[] getAllAnswers() {
		return allAnswers;
	}

	public Question[] getQuestions() {
		return questions;
	}

	public int getQuestionsCount() {
		return questions.length;
	}

	public Question getQuestion(int index) {
		return questions[index];
	}

	public void saveQuiz(QuestionRepository repository, String filename) throws IOException {
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filename));
		file.writeObject(repository);
		file.close();


	}

	public static QuestionRepository readRepository(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream(filename));
		QuestionRepository repository = (QuestionRepository)file.readObject();
		file.close();
		return repository;

	}





}
