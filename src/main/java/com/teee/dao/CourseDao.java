package com.teee.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teee.domain.Course;
import com.teee.domain.TeacherCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseDao extends BaseMapper<Course> {
    @Insert("insert into teacher_course(cid, tid) values(#{cid}, #{tid})")
    int insertCourseToTeahcer(@Param("cid") int cid, @Param("tid") Long tid);

    @Select("select cid from teacher_course where tid=#{tid}")
    List<Integer> getCidByTid(@Param("tid") Long tid);
}
