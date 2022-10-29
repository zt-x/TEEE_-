package com.teee.domain.works.QuestionObject;


import com.teee.config.Code;
import lombok.Data;

import java.util.ArrayList;


/**
 * @author Xu ZhengTao
 */
@Data
public class ChoiceQuestion extends QuestionObject{
    private ArrayList<String> choices;
    private int rightAnswer;

    public ChoiceQuestion(ArrayList<String> choices, int rightAnswer) {
        this.choices = choices;
        this.rightAnswer = rightAnswer;
    }

    public ChoiceQuestion(int questionType, float questionScore, String questionText, ArrayList<String> choices, int rightAnswer) {
        super(questionType, questionScore, questionText);
        this.choices = choices;
        this.rightAnswer = rightAnswer;
        this.questionType = Code.BankType_choice_question;
    }
}
