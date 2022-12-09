package com.teee.service.Course;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    Integer addStuToCourse(int cid, Long uid);
    Integer addCourseToUser(Long uid, int cid);
    JSONArray getStuCourses(Long uid);
    JSONArray getTeaCourses(Long tid);
    boolean isCourseExist(int cid);
}
