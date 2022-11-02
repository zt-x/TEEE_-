package com.teee.ControllerTest;

import com.teee.TEEEApplication;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.domain.returnClass.Result;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Test
    public void getQueBankByTid(){
        BankController bankController = SpringBeanUtil.getBean(BankController.class);
        Result res = bankController.getQueBankByTid("eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjEsInJvbGUiOiJ0ZWFjaGVyIiwiZXhwIjoxNjY3Mzg2NjEyLCJqdGkiOiJjOGFlNDUxZi02NGFmLTQ1YzItYjhmMy0zYTQ0YTMwOGY1ZWIifQ.5j3Ju99jJGX4I5zJ4v0PCow9GH_zS-ZiGhdt2TxGBBo");

    }
}
