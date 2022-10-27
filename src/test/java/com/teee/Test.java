package com.teee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.dao.CourseDao;
import com.teee.dao.LoginDao;
import com.teee.dao.UserInfoDao;
import com.teee.domain.Result;
import com.teee.domain.UserInfo;
import com.teee.domain.works.QuestionObject.FillInQuestion;
import com.teee.domain.works.QuestionObject.QuestionObject;
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

    @org.junit.Test
    public void jsonTest(){
        Result rs = new Result();
        Result rs2 = new Result();
        rs.setData(new UserInfo());
        rs.setCode(1235);
        rs.setMsg("啦啦啦啦");
        rs2.setData(new UserInfo());
        rs2.setCode(66666);
        rs2.setMsg("日日日日日");
        JSONObject js = (JSONObject)JSONObject.toJSON(rs);
        JSONObject js2 = (JSONObject)JSONObject.toJSON(rs2);
        JSONArray ja = new JSONArray();
        ja.add(js);
        ja.add(js2);

        String str_ja = ja.toString();
        System.out.println(str_ja);

        // 转回来

        JSONArray jb = JSONArray.parseArray(str_ja);
        System.out.println(jb);
        JSONObject jbb = new JSONObject();
        for (Object o : jb) {
            jbb = (JSONObject)o;
            System.out.println(jbb);
        }
    }
    @org.junit.Test
    public void QuestionManagerTest(){
        FillInQuestion fillInQuestion = new FillInQuestion(1, 1.0F, "你好", "QuestionText");
        FillInQuestion fillInQuestion2 = new FillInQuestion(1, 1.0F, "我是2", "QuestionText");
        String s = QuestionManager.addQuestion("", fillInQuestion);
        String s1 = QuestionManager.addQuestion(s, fillInQuestion2);
        System.out.println(s);
        System.out.println(s1);

    }

}
