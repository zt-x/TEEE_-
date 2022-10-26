package com.teee.controller.teacher;

import com.teee.domain.Course;
import com.teee.domain.Result;

public interface TeacherCourseController {
    Result createCourse(String token, Course courseInfo);
    Result getMyCourses(String token);
}
