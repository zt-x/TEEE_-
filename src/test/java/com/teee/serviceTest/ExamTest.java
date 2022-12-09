package com.teee.serviceTest;

import com.alibaba.fastjson2.JSONObject;
import com.teee.TEEEApplication;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.service.HomeWork.Exams.ExamService;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class ExamTest {

    @Test
    public void  checkTest(){
        ExamService examService = SpringBeanUtil.getBean(ExamService.class);
        JSONObject sub = new JSONObject();
        ArrayList<String> rules = new ArrayList<>();
        rules.add("FACKCHECK");
        sub.put("uid", "202031101006");
        sub.put("wid", "30");
        sub.put("subFace", "D:\\testFace\\lxd2.jpg");
        BooleanReturn booleanReturn = examService.checkRule(sub, rules);
        System.out.println(booleanReturn);
    }
}
