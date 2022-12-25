package com.teee.service.publicpart.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.dao.AWorkDao;
import com.teee.dao.CourseDao;
import com.teee.dao.SubmitWorkDao;
import com.teee.dao.UserCourseDao;
import com.teee.domain.Course;
import com.teee.domain.UserCourse;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.AWork;
import com.teee.domain.works.SubmitWork;
import com.teee.service.publicpart.TodoListService;
import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu ZhengTao
 */
@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    CourseDao courseDao;
    @Autowired
    UserCourseDao userCourseDao;
    @Autowired
    SubmitWorkDao submitWorkDao;
    @Autowired
    AWorkDao aWorkDao;

    @Override
    public BooleanReturn getTodoList(String token) {
        // 获取 user 身份
        String role = JWT.getRole(token);
        Long id = JWT.getUid(token);
        if(Code.Teacher.equals(role)){
            // 获取未批改的作业
            /*
               {
                  CourseID:
                  CourseName:
                  unReadOverWork:
               },
              */
            JSONArray jsonArray = new JSONArray();
            // 拿到我的Course
            List<Course> courses = courseDao.selectList(new LambdaQueryWrapper<Course>().eq(Course::getTid, id));
            if(courses == null || courses.size() <= 0){
                return new BooleanReturn(true,"您还没有课程",null);
            }
            for (Course course : courses) {
                JSONObject jo = new JSONObject();
                Integer unReadOverWork = 0;
                List<AWork> aWorks = aWorkDao.selectList(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, course.getId()));
                if(aWorks == null || aWorks.size() <=0){
                    continue;
                }else{
                    for (AWork aWork : aWorks) {
                        unReadOverWork += submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, aWork.getId()).eq(SubmitWork::getFinishReadOver, 0));
                    }
                }
                if(unReadOverWork != 0){
                    jo.put("CourseID",course.getId());
                    jo.put("CourseName",course.getCourseName());
                    jo.put("unReadOverWork",unReadOverWork);
                    jsonArray.add(jo);
                }
            }
            return new BooleanReturn(true,"获取成功", TypeChange.arr2str(jsonArray));
        }else if(Code.Student.equals(role)){
            // 获取未做的作业
            /*
              {
                  WorkName:
                  WorkID:
                  Deadline:
              }
              */
            JSONArray jsonArray = new JSONArray();
            UserCourse userCourse = userCourseDao.selectById(id);
            if(userCourse == null || "".equals(userCourse.getCid()) || "[]".equals(userCourse.getCid())){
                return new BooleanReturn(true,"获取成功","[]");
            }
            ArrayList<String> arrayList = TypeChange.str2arrl(userCourse.getCid());
            for (String s : arrayList) {
                Integer cid = Integer.valueOf(s);
                List<AWork> aWorks = aWorkDao.selectList(new LambdaQueryWrapper<AWork>().eq(AWork::getCid, cid));
                for (AWork aWork : aWorks) {
                    Integer count = submitWorkDao.selectCount(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, aWork.getId()).eq(SubmitWork::getUid,id));
                    if(count<=0){
                        JSONObject jo = new JSONObject();
                        jo.put("WorkID",aWork.getId());
                        jo.put("WorkName",aWork.getWorkName());
                        jo.put("CourseName",courseDao.selectById(cid).getCourseName());
                        jo.put("Deadline",aWork.getDeadline());
                        jsonArray.add(jo);
                    }
                }
            }
            return new BooleanReturn(true,"获取成功", TypeChange.arr2str(jsonArray));
        }else if(Code.Admin.equals(role)){

        }else{
            return new BooleanReturn(false,"未知身份: " + role);
        }
        return new BooleanReturn(false,"未知异常");
    }
}
