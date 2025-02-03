package com.keshav.question_microservice.service;


import com.keshav.question_microservice.dao.QuestionDao;
import com.keshav.question_microservice.model.Question;
import com.keshav.question_microservice.model.QuestionWrapper;
import com.keshav.question_microservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity< List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> saveQuestion(Question question) {
        questionDao.save(question);
        try {
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not Success",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestionById(int id) {
        questionDao.deleteById(id);
        try {
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestionById(Question question) {
        questionDao.save(question);
        try {
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuestion) {
        List<Integer> question = questionDao.findRandomQuestionByCategory(categoryName, numQuestion);
        return new ResponseEntity<>(question , HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds)
            questions.add(questionDao.findById(id).get());

        for (Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestiontitle(question.getQuestiontitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right=0 ;
        for (Response response :responses){
            Question question = questionDao.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightanswer()))
                right++;
        }
        return new ResponseEntity<>(right ,HttpStatus.OK);
    }
}

