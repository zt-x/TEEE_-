package com.teee.service.Course;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teee.domain.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {
    Integer addStuToCourse(int cid, Long uid);
    Integer addCourseToUser(Long uid, int cid);
    JSONArray getStuCourses(Long uid);
    JSONArray getTeaCourses(Long tid);
    boolean isCourseExist(int cid);
}
