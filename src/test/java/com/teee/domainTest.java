package com.teee;

import com.teee.domain.works.SubmitWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class domainTest {
    @Test
    public void getNumOfQue(){
        SubmitWork sw = new SubmitWork();
        sw.setWorkTableId(18);
        System.out.println(SubmitWork.getNumOfQue(sw));
    }
}
