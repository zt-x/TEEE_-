package com.teee.domain.works.QuestionObject;


//题目的父类

import lombok.Data;

/**
 * @author Xu ZhengTao
 */
@Data
public class QuestionObject {
    int questionType;
    float questionScore;
    String questionText;

    public QuestionObject() {
    }

    public QuestionObject(int questionType, float questionScore, String questionText) {
        this.questionType = questionType;
        this.questionScore = questionScore;
        this.questionText = questionText;
    }
}
