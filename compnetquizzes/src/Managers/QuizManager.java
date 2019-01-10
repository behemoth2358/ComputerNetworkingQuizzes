package Managers;

import Interfaces.IQuestionModel;
import Interfaces.IQuizManager;
import Interfaces.IRepository;
import Interfaces.IGradeModel;
import Models.GradeModel;
import Utilities.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuizManager implements IQuizManager {
    private IRepository repository;
    private ArrayList<Integer> questionsIndices;
    private ArrayList<String> userAnswers;

    public QuizManager(IRepository repository, ArrayList<Integer> questionsIndices) {
        this.repository = repository;
        this.questionsIndices = questionsIndices;

        userAnswers = new ArrayList<>(Collections.nCopies(questionsIndices.size(), ""));
    }

    @Override
    public void saveUserAnswer(int questionIndex, String answer) {
        userAnswers.set(questionIndex, answer);
    }

    @Override
    public IGradeModel evaluate() {
        ArrayList<IQuestionModel> questions = repository.getQuestions();
        int totalSum = 0;
        int userScore = 0;
        
        for (int i = 0; i < questionsIndices.size(); i++) {
            String userAnswer = userAnswers.get(i);

            IQuestionModel question = questions.get(questionsIndices.get(i));
            totalSum += question.getPoints();

            if (userAnswer == null) continue;

            userScore += (checkAnswer(question, userAnswer) ? question.getPoints() : -question.getPoints());
        }

        Double grade = computeGrade(userScore, totalSum);

        return new GradeModel(grade, userScore, totalSum);
    }

    private Double computeGrade(int userScore, int totalSum) {
        return 10.0 * (double)userScore / totalSum;
    }

    private boolean checkAnswer(IQuestionModel question, String userAnswer) {
        userAnswer = userAnswer.replace(" ", "");
        userAnswer = userAnswer.replace("\n", "");

        String[] userChoices = computeChoices(userAnswer);
        String[] correctChoices = computeChoices(question.getAnswer());

        Arrays.sort(userChoices);
        Arrays.sort(correctChoices);

        if (correctChoices.length != userChoices.length) return false;

        for(int i = 0; i < correctChoices.length; i++) {
            if (!correctChoices[i].equalsIgnoreCase(userChoices[i])) {
                return false;
            }
        }

        return true;
    }

    private String[] computeChoices(String answer) {
        return answer.split("\\s*,\\s*");
    }

    @Override
    public IQuestionModel getQuestionByIndex(int index) {
        int questionIndex = questionsIndices.get(index);

        return repository.getQuestions().get(questionIndex);
    }

    @Override
    public Integer getNoOfQuestions() {
        return questionsIndices.size();
    }

    @Override
    public ArrayList<Triple<String, String, String>> getQuestionsStats() {
        ArrayList<IQuestionModel> questions = repository.getQuestions();

        ArrayList<Triple<String, String, String>> questionsStats = new ArrayList<>();

        for(int index = 0; index < questionsIndices.size(); index++) {
            IQuestionModel question = questions.get(questionsIndices.get(index));
            String userAnswer = userAnswers.get(index);

            String status = "Unanswered";

            if (userAnswer != null && userAnswer.length() > 0) {
                status = (checkAnswer(question, userAnswer) ? "Correct" : "Incorrect");
            }

            questionsStats.add(
                    new Triple<> (
                            String.valueOf(index),
                            question.getStatement(),
                            status
                    )
            );
        }

        return questionsStats;
    }

    @Override
    public String getUserAnswer(int index) {
        return userAnswers.get(index);
    }
}
