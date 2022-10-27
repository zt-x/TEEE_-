package com.teee.domain.works;

import com.teee.domain.works.QuestionObject.QuestionObject;
import lombok.Data;
import java.util.ArrayList;

/**
 * @author Xu ZhengTao
 * @data: questions: JSONArray<JSONObject>.toString();
 */
@Data
public class BankWork {
    private int workId;
    private String questions;
    private String workName;
}
