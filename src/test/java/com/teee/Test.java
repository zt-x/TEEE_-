package com.teee;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.LoginDao;
import com.teee.dao.UserInfoDao;
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
        System.out.println(powerService.getUser(token));
    }

    @org.junit.Test
    public void getRouter(){
        String role = "student";
        System.out.println(new PowerServiceImpl().getRouter(role).toString());
    }

    //illegal Token
    @org.junit.Test
    public void CheckToken(){
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.dwadsadw.wafawds";
        System.out.println(new PowerServiceImpl().isTokenLegal(token));
    }




    @org.junit.Test
    public void Test(){
        System.out.println(courseService.addCourseToUser(2L, 1));
    }

    @org.junit.Test
    public void getUIDTest(){
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIsInJvbGUiOiJzdHVkZW50IiwiZXhwIjoxNjY2NDYwNDY4LCJqdGkiOiI4Y2M2NzM2Mi1mNWVmLTQ0NTUtOThhMC05N2Y3NmRjYTVhMWEifQ.qGHSYif2ZT8UjBpVoXhcGYapRYlnuD928-3ZbAWmpKo";
        System.out.println(JWT.getUid(token));
    }
}
