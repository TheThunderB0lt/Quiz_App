package com.driver.Quiz_App.Service;

import com.driver.Quiz_App.Model.Question;
import com.driver.Quiz_App.Model.QuestionWrapper;
import com.driver.Quiz_App.Model.Quiz;
import com.driver.Quiz_App.Model.Response;
import com.driver.Quiz_App.Repository.QuestionRepository;
import com.driver.Quiz_App.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int num, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, num);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> createQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (Question q : questionFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).get();
        //get questions from the quiz
        List<Question> questions = quiz.getQuestions();
        int correctAns = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                correctAns++;
            }
            i++;
        }
        return new ResponseEntity<>(correctAns, HttpStatus.OK);
    }
}
