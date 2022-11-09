package com.teee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.controller.publicpart.Work.Impl.WorkControllerImpl;
import com.teee.dao.AWorkDao;
import com.teee.dao.CourseDao;
import com.teee.dao.LoginDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.returnClass.Result;
import com.teee.domain.UserInfo;
import com.teee.domain.works.AWork;
import com.teee.domain.works.QuestionObject.FillInQuestion;
import com.teee.service.Course.Impl.CourseServiceImpl;
import com.teee.service.HomeWork.QuestionManager;
import com.teee.service.User.Impl.UserServiceImpl;
import com.teee.service.publicpart.Impl.PowerServiceImpl;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
    }


    @Autowired
    CourseDao courseDao;
    @org.junit.Test
    public void getCouse(){
    }

    @org.junit.Test
    public void getStuCourse(){
    }

    @org.junit.Test
    public void jsonTest(){
//        Result rs = new Result();
//        Result rs2 = new Result();
//        rs.setData(new UserInfo());
//        rs.setCode(1235);
//        rs.setMsg("啦啦啦啦");
//        rs2.setData(new UserInfo());
//        rs2.setCode(66666);
//        rs2.setMsg("日日日日日");
//        JSONObject js = (JSONObject)JSONObject.toJSON(rs);
//        JSONObject js2 = (JSONObject)JSONObject.toJSON(rs2);
//        JSONArray ja = new JSONArray();

        AWorkDao aWorkDao = SpringBeanUtil.getBean(AWorkDao.class);
        LambdaQueryWrapper<AWork> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AWork::getCid, 15l);
        List<AWork> aWorks = aWorkDao.selectList(lqw);
        JSONArray jsonArray = new JSONArray();
        // 装配
        for (AWork aWork : aWorks) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(aWork);
            jsonArray.add(jsonObject);
        }
//
//
//
//
//        ja.add(js);
//        ja.add(js2);
//
//        String str_ja = ja.toString();
//        System.out.println(str_ja);
//
//        // 转回来
//
//        JSONArray jb = JSONArray.parseArray(str_ja);
//        System.out.println(jb);
//        JSONObject jbb = new JSONObject();
//        for (Object o : jb) {
//            jbb = (JSONObject)o;
//            System.out.println(jbb);
//        }
    }
    @org.junit.Test
    public void QuestionManagerTest(){
        FillInQuestion fillInQuestion = new FillInQuestion(1, 1.0F, "你好", "QuestionText");
        FillInQuestion fillInQuestion2 = new FillInQuestion(1, 1.0F, "我是2", "QuestionText");
        String s = QuestionManager.addQuestion("", fillInQuestion);
        String s1 = QuestionManager.addQuestion(s, fillInQuestion2);


    }


    @org.junit.Test
    public void getAllWorksByCID(){
        WorkControllerImpl workController = SpringBeanUtil.getBean(WorkControllerImpl.class);
        workController.getAllWorksByCID(25);
    }

}
