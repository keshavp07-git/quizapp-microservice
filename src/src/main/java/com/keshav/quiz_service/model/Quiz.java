package com.keshav.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates a unique id
    private Long id;  // Primary key field

    private String title;

    @ElementCollection  // If questionIds is a simple collection, use @ElementCollection
    private List<Integer> questionIds;

    // Getter and setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter for 'title'
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for 'questionIds'
    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }
}
