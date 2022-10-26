package com.teee.controller.student;

import com.teee.domain.Result;

public interface CourseController {
    Result addCourse(String token, int cid);
    Result getMyCourses(String token);
}
