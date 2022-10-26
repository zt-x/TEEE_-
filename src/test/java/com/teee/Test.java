package com.teee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.CourseDao;
import com.teee.dao.LoginDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.Course;
import com.teee.domain.TeacherCourse;
import com.teee.domain.UserInfo;
import com.teee.service.Course.CourseService;
import com.teee.service.Course.Impl.CourseServiceImpl;
import com.teee.service.User.Impl.UserServiceImpl;
import com.teee.service.publicpart.Impl.PowerServiceImpl;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    LoginDao loginDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    UserServiceImpl uService;

    @Autowired
    CourseServiceImpl courseService;

    @org.junit.Test
    public void getUserInfoByToken(){
        PowerServiceImpl powerService = SpringBeanUtil.getBean(PowerServiceImpl.class);

        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIsInJvbGUiOiJzdHVkZW50IiwiZXhwIjoxNjY2NDUxMDUwLCJqdGkiOiI5Y2MwYjYwNi04NjliLTQyNjYtODhjZC1lMTZlOTgwNDM1MDEifQ.fn0S0PKk8bIbCW-O4WcK1DHnu7BUuHcvdbN_adh4La0";
    }

    @org.junit.Test
    public void getRouter(){
        String role = "student";
    }

    //illegal Token
    @org.junit.Test
    public void CheckToken(){
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.dwadsadw.wafawds";
    }




    @org.junit.Test
    public void Test(){
    }

    @org.junit.Test
    public void getUIDTest(){
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjEsInJvbGUiOiJ0ZWFjaGVyIiwiZXhwIjoxNjY2ODAxOTc0LCJqdGkiOiI5NGU5OTE4OS0yNTI0LTRmNjgtODI0OC02MDg4NWY1NmU3MjgifQ.Og0jADw0iQUfhorHCd82myP4YcnOJBptSGH3HIoQqwA";
        System.out.println(JWT.getUid(token));
    }


    @Autowired
    CourseDao courseDao;
    @org.junit.Test
    public void getCouse(){
        System.out.println(courseService.getStuCourses(2l));
    }

    @org.junit.Test
    public void getStuCourse(){
        System.out.println(courseService.getStuCourses(2l));
    }

}
