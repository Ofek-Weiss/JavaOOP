## Multiple Question Quiz in Java (OOP)
# Project Description
This project is a simple Java application that creates a multiple-choice quiz using object-oriented programming principles. Each question has a maximum of 10 answers, and each answer is represented as an object. The project is built with the following key classes:

QuestionRepository: This class holds an array of Question objects and provides the functionality to manage the questions and their answers.
Question: This class represents a single question and contains an array of QuestionAnswer objects (the possible answers to the question).
Answer: This class represents the content of an individual answer.
QuestionAnswer: This class holds an Answer object and a boolean flag indicating whether the answer is correct for the associated Question.
Features
Print All Questions and Answers: Print all questions in the quiz and their possible answers.
Add an Answer to a Question: Add a new answer to an existing question in the repository.
Create a Quiz: Allows a user to select a set of questions for a quiz, adds two extra answers ("Nothing is correct" and "There is more than one correct answer"), and creates two files: one for the quiz and one for the correct solutions.
File Generation: Automatically generate a quiz file and a solution file named with the exact date and time they were created.
