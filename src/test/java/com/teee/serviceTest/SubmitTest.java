package com.teee.serviceTest;

import com.teee.TEEEApplication;
import com.teee.domain.works.SubmitWork;
import com.teee.service.HomeWork.SubmitService;
import com.teee.service.HomeWork.SubmitServiceImpl;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class SubmitTest {
    @Test
    public void autoReadOverTest(){
        SubmitWork submitWork = new SubmitWork();
//        submitWork.s
//        SubmitServiceImpl.autoReadOver()
    }
}
