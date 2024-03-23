package com.driver.Quiz_App.Repository;

import com.driver.Quiz_App.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
