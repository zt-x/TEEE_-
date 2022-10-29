package com.teee.domain.works.QuestionObject;

import com.teee.config.Code;
import lombok.Data;


/**
 * @author Xu ZhengTao
 */
@Data
public class FillInQuestion extends QuestionObject{
    private String rightAnswer;

    public FillInQuestion(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public FillInQuestion(int questionType, float questionScore, String questionText, String rightAnswer) {
        super(questionType, questionScore, questionText);
        this.rightAnswer = rightAnswer;
        this.questionType = Code.BankType_fillin_question;
    }
}
