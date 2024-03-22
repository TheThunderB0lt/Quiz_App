package com.driver.Quiz_App.Service;

import com.driver.Quiz_App.Model.Question;
import com.driver.Quiz_App.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionRepository.save(question);
        return "Success";
    }
}
