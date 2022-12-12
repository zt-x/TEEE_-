package com.teee.serviceTest;

import com.alibaba.fastjson2.JSONObject;
import com.teee.TEEEApplication;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.service.HomeWork.Exams.ExamService;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
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
        BooleanReturn faceCheck = examService.faceCheck(202031101668L, "http://127.0.0.1:8080/face/1670556470201.png");
        System.out.println(faceCheck);
    }
}
