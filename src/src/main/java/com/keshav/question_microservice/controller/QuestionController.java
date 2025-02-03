package com.keshav.question_microservice.controller;

import com.keshav.question_microservice.model.Question;
import com.keshav.question_microservice.model.QuestionWrapper;
import com.keshav.question_microservice.model.Response;
import com.keshav.question_microservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity< List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity< List<Question>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }

   @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.saveQuestion(question);
   }
   @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable int id){
        return questionService.deleteQuestionById(id);
   }
   @PutMapping("update/{id}")
    public ResponseEntity<String>updateQuestionById(@RequestBody Question question){
        return questionService.updateQuestionById(question);
   }
   @GetMapping("generate")
    public ResponseEntity<List<Integer>>getQuestionForQuiz(@RequestParam String categoryName , @RequestParam Integer numQuestion){
        return questionService.getQuestionForQuiz(categoryName , numQuestion);
   }
   @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>>getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
   }
    @PostMapping("getScore")
    public ResponseEntity<Integer>getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
   }


