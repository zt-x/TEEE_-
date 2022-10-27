package com.teee.domain.works.QuestionObject;


import lombok.Data;

import java.util.ArrayList;


/**
 * @author Xu ZhengTao
 */
@Data
public class ChoiceQuestion extends QuestionObject{
    private ArrayList<String> choices;
    private int rightAnswer;
}
