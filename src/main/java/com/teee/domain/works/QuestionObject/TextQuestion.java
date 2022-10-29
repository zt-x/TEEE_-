package com.teee.domain.works.QuestionObject;

import com.teee.config.Code;
import lombok.Data;

@Data
public class TextQuestion extends QuestionObject{
    public TextQuestion() {
    }

    public TextQuestion(int questionType, float questionScore, String questionText) {
        super(questionType, questionScore, questionText);
        this.questionType = Code.BankType_text_question;
    }
}
