package com.teee.serviceTest;

import com.teee.TEEEApplication;
import com.teee.dao.SubmitWorkDao;
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
        SubmitWorkDao bean = SpringBeanUtil.getBean(SubmitWorkDao.class);
        SubmitWork submitWork = bean.selectById(1);
        SubmitWork submitWork1 = SubmitServiceImpl.autoReadOver(submitWork, true, true);
        System.out.println(submitWork1);
    }
}
