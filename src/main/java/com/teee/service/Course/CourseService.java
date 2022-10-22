package com.teee.service.Course;

import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    Integer addStuToCourse(int cid, Long uid);
    Integer addCourseToUser(Long uid, int cid);
    boolean isCourseExist(int cid);
}
