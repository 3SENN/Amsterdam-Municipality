package nl.hva.amsserver.service;

import nl.hva.amsserver.models.quiz.Quiz;
import nl.hva.amsserver.models.quiz.QuizResult;
import nl.hva.amsserver.models.user.User;
import nl.hva.amsserver.repositories.quiz.QuizResultRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizResultService {
    /**
     * The Quiz result repository jpa.
     */
    @Autowired
    QuizResultRepositoryJPA quizResultRepositoryJPA;

    /**
     * Find all quiz results.
     *
     * @return the list of quiz results
     */
    public List<QuizResult> findAll() {
        return new ArrayList<>(quizResultRepositoryJPA.findQuizHighscores());
    }

    /**
     * Find by user id (optional).
     *
     * @param id the id of the user
     * @return optional quiz results of user
     */
    public List<QuizResult> findById(long id) {
        return quizResultRepositoryJPA.findByUserId(id);
    }

    public long getTotalAmountOfQuizResults() {
        return quizResultRepositoryJPA.count();
    }

    /**
     * Retrieve quiz result total scores
     * @return
     */
    public Map<String, Integer> getScores() {
        Map<String, Integer> map = new HashMap<>();

        int totalScore = quizResultRepositoryJPA.totalScore().isEmpty() ? 0 : quizResultRepositoryJPA.totalScore().get();
        int averageScore = quizResultRepositoryJPA.averageScore().isEmpty() ? 0 : quizResultRepositoryJPA.averageScore().get();
        int percentage;

        if (totalScore == 0 || averageScore == 0) percentage = 0;
        else percentage = 100 / (totalScore / averageScore);

        // adding properties to the JSON object
        map.put("users", (int) quizResultRepositoryJPA.count());
        map.put("totalScore", totalScore);
        map.put("averageScore", averageScore);
        map.put("percentage", percentage);

        return map;
    }

    public QuizResult findByUserAndQuiz(User user, Quiz quiz) {
        return quizResultRepositoryJPA.findByUserIdAndQuizId(user, quiz);
    }

}
