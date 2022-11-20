package com.teee.ControllerTest;

import com.teee.TEEEApplication;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.domain.returnClass.Result;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class WorkControlTest {
    @Test
    public void getWorkTimer(){
        WorkController workController = SpringBeanUtil.getBean(WorkController.class);
        String token="eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIwMjAzMTEwMTAwMSwicm9sZSI6InN0dWRlbnQiLCJleHAiOjE2Njg5NDU2MzgsImp0aSI6IjU1MWM2YTkxLTBiY2EtNGIwYS1hZTcxLTc0M2RiZjE5MmU4ZCJ9.LGw_jmr4n57-ZuADCZKDXHxkkzjVICdP1SLpLP0FDTo";
        Result workTimer = workController.getWorkTimer(token, 4);
        System.out.println(workTimer);

    }
}
