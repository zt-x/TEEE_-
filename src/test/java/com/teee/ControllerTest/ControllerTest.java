package com.teee.ControllerTest;

import com.teee.TEEEApplication;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.controller.publicpart.Work.Impl.SubmitWorkControllerImpl;
import com.teee.controller.publicpart.Work.Impl.WorkControllerImpl;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.controller.student.CourseController;
import com.teee.controller.student.Impl.CourseControllerImpl;
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

    @Test
    public void getMyCouese(){
        CourseControllerImpl bean = SpringBeanUtil.getBean(CourseControllerImpl.class);
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIsInJvbGUiOiJzdHVkZW50IiwiZXhwIjoxNjY4MDAzMzY4LCJqdGkiOiJhMjIxN2QxNS03NGJiLTQ2ZTQtYjkwNS03MTE3ZDM0ZDlhNzcifQ.-Yue5okOP8esqvtOjhNMYhiW0cWKFfNu8ludmLjs5jE";
        Result myCourses = bean.getMyCourses(token);
        System.out.println(myCourses.getData());

    }

    @Test
    public void getAllSubmit(){
        SubmitWorkControllerImpl workController = SpringBeanUtil.getBean(SubmitWorkControllerImpl.class);
        Result allSubmitByWorkId = workController.getAllSubmitByWorkId(28);
        System.out.println(allSubmitByWorkId.getData());
    }

    @Test
    public void getSubmitSummary(){
        SubmitWorkControllerImpl workController = SpringBeanUtil.getBean(SubmitWorkControllerImpl.class);
        System.out.println(workController.getSubmitSummary(29).getData());
    }

    @Test
    public void deleteAWork(){
        WorkControllerImpl bean = SpringBeanUtil.getBean(WorkControllerImpl.class);
        System.out.println(bean.deleteAWork(33));

    }
}
