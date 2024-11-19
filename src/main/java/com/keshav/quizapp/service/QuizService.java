package com.keshav.quizapp.service;

import com.keshav.quizapp.controller.Response;
import com.keshav.quizapp.dao.QuestionDao;
import com.keshav.quizapp.dao.QuizDao;
import com.keshav.quizapp.model.Question;
import com.keshav.quizapp.model.QuestionWrapper;
import com.keshav.quizapp.model.Quiz;
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
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionByCategory(category,numQ);
      Quiz quiz = new Quiz();
      quiz.setTitle(title);
      quiz.setQuestions(questions);
      quizDao.save(quiz);
      return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id); // Find question from quiz dao by id
        List<Question> questionsFromDb = quiz.get().getQuestions(); // get quiz id and get questions from List Question
        List<QuestionWrapper> questionForUser = new ArrayList<>(); // Filter the question right answer and other non required things and store into arraylist
            for(Question q : questionsFromDb){ // now start loop where List question , from List question using Question Wrapper select only  required option and store into arraylist .
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestiontitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionForUser.add(qw); // Save the required things into wrapper class
            }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
       Quiz quiz = quizDao.findById(id).get();
       List<Question>questions = quiz.getQuestions();
       int right=0 ;
       int i=0;
       for (Response response :responses){
           if (response.getResponse().equals(questions.get(i).getRightanswer()))
               right++;

           i++;
       }
            return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
