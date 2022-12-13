package com.teee.service.Course.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.config.Code;
import com.teee.dao.*;
import com.teee.domain.Course;
import com.teee.domain.CourseUser;
import com.teee.domain.UserCourse;
import com.teee.domain.works.SubmitWork;
import com.teee.domain.works.SubmitWorkContent;
import com.teee.service.Course.CourseService;
import com.teee.utils.TypeChange;
import com.teee.utils.fileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseUserDao courseUserDao;
    @Autowired
    private UserCourseDao userCourseDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private SubmitWorkDao submitWorkDao;
    @Autowired
    private SubmitWorkContentDao submitWorkContentDaoDao;

    @Value("${path.filePath}")
    private String filePath;

    @Value("${path.tempPath}")
    private String tempPath;


    @Override
    public boolean isCourseExist(int cid){
        try{
            Course cu = courseDao.selectById(cid);
            if(cu == null){
                return false;
            }
            return true;
        }catch(NullPointerException npe){
            return false;
        }
    }

    @Override
    public Integer addStuToCourse(int cid, Long uid) {
        int new_ = 0;
        try{

            CourseUser courseUser = courseUserDao.selectById(cid);
            if(courseUser == null){
                courseUser = new CourseUser(cid, "[]");
                new_ = 1;
            }
            ArrayList<Long> uids = new ArrayList<Long>();
            String[] split = courseUser.getUid().replace("[", "").replace("]", "").split(",");
            if(!split[0].equals("")){
                for (String s : split) {
                    uids.add(Long.valueOf(s.trim()));
                }
            }
            if(!uids.contains(uid)){
                uids.add(Long.valueOf(uid));
            }
            courseUser.setUid(uids.toString());
            if(new_ == 1){
                courseUserDao.insert(courseUser);
            }else{
                courseUserDao.updateById(courseUser);
            }
            return Code.Suc;
        }catch (Exception e){
            return Code.ERR;
        }
    }

    @Override
    public Integer addCourseToUser(Long uid, int cid) {
        int new_ = 0;
        try{
            UserCourse userCourse =  userCourseDao.selectById(uid);
            if(userCourse == null){
                userCourse = new UserCourse(uid, "[]");
                new_ = 1;
            }
            ArrayList<Integer> cids = new ArrayList<>();
            String[] split = userCourse.getCid().replace("[", "").replace("]", "").split(",");
            if(!split[0].equals("")){
                for (String s : split) {
                    cids.add(Integer.valueOf(s.trim()));
                }
            }
            if(!cids.contains(cid)){
                cids.add(cid);
            }
            userCourse.setCid(cids.toString());
            if(new_ == 1){
                userCourseDao.insert(userCourse);
            }else{
                userCourseDao.updateById(userCourse);
            }
            return Code.Suc;
        }catch (Exception e){
            return Code.ERR;
        }

    }


    @Override
    public JSONArray getStuCourses(Long uid) {
        JSONArray courses = new JSONArray();
        Course course;
        JSONObject course_json = new JSONObject();
        try{
            UserCourse userCourse = userCourseDao.selectById(uid);
            String[] cids = userCourse.getCid().replace("[", "").replace("]", "").split(",");
            for (String cid : cids) {
                cid = cid.replaceAll(" ", "");
                course = courseDao.selectById(Integer.valueOf(cid));
                course_json = (JSONObject) JSONObject.toJSON(course);
                course_json.put("Name", course.getCourseName());
                course_json.put("id", course.getId());
                course_json.put("TeacherName", userInfoDao.selectById(course.getTid()).getUsername());
                course_json.put("College", course.getCollege());
                course_json.put("Time", course.getStartTime() + " - " + course.getEndTime());
                course_json.put("IMG", course.getBanner());
                course_json.put("status", course.getStatus());
                courses.add(course_json);
            }
        }catch(NullPointerException npe){
        }
        return courses;
    }

    @Override
    public JSONArray getTeaCourses(Long tid) {
        JSONArray courses = new JSONArray();
        Course course;
        JSONObject course_json;
        for (Integer cid : courseDao.getCidByTid(tid)) {
            course = courseDao.selectById(cid);
            course_json = (JSONObject) JSONObject.toJSON(course);
            course_json.put("Name", course.getCourseName());
            course_json.put("id", course.getId());
            course_json.put("TeacherName", userInfoDao.selectById(course.getTid()).getUsername());
            course_json.put("College", course.getCollege());
            course_json.put("Time", course.getStartTime() + " - " + course.getEndTime());
            course_json.put("IMG", course.getBanner());
            course_json.put("status", course.getStatus());
            courses.add(course_json);
        }
        return courses;
    }

    @Override
    public File packageFile(int wid) {
        String tmpFilePath = tempPath+File.separator + "downloadZipTemp" + File.separator + wid;
        File tmpdir = new File(tmpFilePath);
        System.out.println("exi: " + tmpdir.exists() + "isD:" + tmpdir.isDirectory() + "isFile: " + tmpdir.isFile());
        if(tmpdir.isDirectory()){
            System.out.println("wid文件夹存在, 删除");
            fileUtil.delFile(tmpdir);
        }else{

        }
        List<SubmitWork> submitWorks = submitWorkDao.selectList(new LambdaQueryWrapper<SubmitWork>().eq(SubmitWork::getWorkTableId, wid));
        for (SubmitWork sw : submitWorks) {
            System.out.println("获取work");
            Integer submitId = sw.getSubmitId();
            SubmitWorkContent swc = submitWorkContentDaoDao.selectById(submitId);
            String files = swc.getFiles();
            ArrayList<String> file_list = TypeChange.str2arrl(files);
            for (int i = 1; i <= file_list.size(); i++) {
                // 第 i 题
                System.out.println("  获取第" + i + "题");
                ArrayList<String> ans_file = TypeChange.str2arrl(file_list.get(i-1), ",");
                // 第 i1 个附件
                for (int i1 = 0; i1 < ans_file.size(); i1++) {
                    System.out.println("    获取第" + i1+1 + "个文件");
                    String fileName = ans_file.get(i1);
                    File src = new File(filePath+File.separator + fileName);
                    String substring = fileName.substring(fileName.lastIndexOf("_")+1);
                    String fileOriginName = substring.substring(substring.lastIndexOf("_")+1);
                    File dst = new File( tmpFilePath +File.separator + sw.getUid()+"_" + sw.getUsername() +"_第"+i+"题_" + fileOriginName);
                    if(!dst.getParentFile().isDirectory()){
                        dst.getParentFile().mkdirs();
                    }
                    try {
                        FileCopyUtils.copy(src,dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            return fileUtil.fileToZip(tmpFilePath, tempPath+File.separator + "downloadZipTemp" + File.separator,wid+".zip");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
