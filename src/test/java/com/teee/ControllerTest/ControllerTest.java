package com.teee.ControllerTest;

import com.teee.TEEEApplication;
import com.teee.controller.publicpart.CourseCon;
import com.teee.controller.publicpart.Work.BankController;
import com.teee.controller.publicpart.Work.Impl.SubmitWorkControllerImpl;
import com.teee.controller.publicpart.Work.Impl.WorkControllerImpl;
import com.teee.controller.publicpart.Work.SubmitWorkController;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.controller.student.CourseController;
import com.teee.controller.student.Impl.CourseControllerImpl;
import com.teee.controller.teacher.TeacherCourseController;
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
    @Test
    public  void getSubmitBySid(){
        SubmitWorkController submitWorkController = SpringBeanUtil.getBean(SubmitWorkController.class);
        Result submitBySid = submitWorkController.getSubmitBySid(40);
        System.out.println(submitBySid);

    }

    @Test
    public void getWorkFinishStatus(){
        WorkController wcl = SpringBeanUtil.getBean(WorkController.class);
//        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIwMjAzMTEwMTAxNCwicm9sZSI6InN0dWRlbnQiLCJleHAiOjE2Njg0ODcyNzcsImp0aSI6ImQ4OWJlYzYyLWIwOGQtNGYzZS1hNWVjLWVlOGM1M2E5YWFiYSJ9.jpAd5Lky4ds03v6jZmsR7oX-Uy0M9Gxn0lqs_ITz4hI";
//        System.out.println(wcl.getWorkFinishStatus(token, 28));
    }

    @Test
    public void getAllUser(){
        TeacherCourseController teacherCourseController = SpringBeanUtil.getBean(TeacherCourseController.class);
        System.out.println(teacherCourseController.getAllUser(28));
    }

    @Test
    public void getCourseInfo(){
        CourseCon courseCon = SpringBeanUtil.getBean(CourseCon.class);
        System.out.println(courseCon.getCourseInfo(28));
    }
    @Test
    public void getCourseStatistic(){
        CourseCon courseCon = SpringBeanUtil.getBean(CourseCon.class);
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjEsInJvbGUiOiJ0ZWFjaGVyIiwiZXhwIjoxNjY4NjUyNTM0LCJqdGkiOiIzODk0NzZhOS1iMThiLTQ2ZTEtOGFkYy0zNWZhOTk3ZDBmMTQifQ.TJSuV01M_o1u-VAZGRnTzAW7RCI_dLimr5FgfqqxSmQ";
        System.out.println(courseCon.getCourseStatistic(token, 25));
    }

    @Test
    public void getAllWorkSummary(){
        CourseCon courseCon = SpringBeanUtil.getBean(CourseCon.class);
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjEsInJvbGUiOiJ0ZWFjaGVyIiwiZXhwIjoxNjY4NjUyNTM0LCJqdGkiOiIzODk0NzZhOS1iMThiLTQ2ZTEtOGFkYy0zNWZhOTk3ZDBmMTQifQ.TJSuV01M_o1u-VAZGRnTzAW7RCI_dLimr5FgfqqxSmQ";
        System.out.println(courseCon.getAllWorkSummary(25));
    }
}
