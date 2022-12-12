package com.teee.service.HomeWork.Exams.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teee.dao.UserFaceDao;
import com.teee.dao.WorkExamRuleDao;
import com.teee.domain.UserFace;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.works.WorkExamRule;
import com.teee.service.HomeWork.Exams.ExamService;
import com.teee.utils.Tencent;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    UserFaceDao userFaceDao;

    @Autowired
    Tencent tencent;

    @Autowired
    WorkExamRuleDao workExamRuleDao;

    @Value("${path.facePath}")
    private String facePath;

    @Override
    public BooleanReturn setRuleForExam(WorkExamRule rule) {
        LambdaQueryWrapper lqw = new LambdaQueryWrapper<WorkExamRule>().eq(WorkExamRule::getWid, rule.getWid());
        try{
            if(workExamRuleDao.selectCount(lqw) > 0){
                workExamRuleDao.updateById(rule);
            }else{
                workExamRuleDao.insert(rule);
            }
            return new BooleanReturn(true);
        }catch (Exception e){
            e.printStackTrace();
            return new BooleanReturn(false, e.getMessage());
        }

    }

    @Override
    public BooleanReturn getRuleForExam(int wid) {
        try{
            WorkExamRule workExamRule = workExamRuleDao.selectById(wid);
            if(workExamRule != null){
                return new BooleanReturn(true,"",workExamRule);
            }else{
                return new BooleanReturn(true, "",new WorkExamRule(wid,"","","无规则"));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BooleanReturn(false,e.getMessage());

        }
    }

    @Override
    public BooleanReturn faceCheck(Long uid, String imgUrl) {
        // 本地拉取用户Face
        UserFace u_face = userFaceDao.selectById(uid);
        if(u_face == null || u_face.getFaceSrc() == null){
            return new BooleanReturn(false,"您还没有登记人脸信息捏~请先 “头像->个人信息->添加人脸验证” 喵");
        }
        System.out.println("正在获取本地FACE");
        String faceSrc = u_face.getFaceSrc();


        // 获取当前照片
        System.out.println("正在获取当前上传的FACE");

        if(faceSrc == null || imgUrl == null){
            return new BooleanReturn(false,"存在空照片");
        }
        BooleanReturn faceCheckRes = tencent.faceCheck(faceSrc, imgUrl);
        if(faceCheckRes.isSuccess()){
            JSONObject data = (JSONObject) faceCheckRes.getData();
            BigDecimal score = TypeChange.Obj2BigDec(data.get("Score"));
            System.out.println("SCORE = " + score);
            if(score.compareTo(new BigDecimal(80)) >=0){
                return new BooleanReturn(true, "验证通过");

            }else{
                return new BooleanReturn(false, "验证不通过");

            }
        }else{
            return new BooleanReturn(false,faceCheckRes.getMsg());
        }
    }

    /**
     * submit:{
     *     uid:
     *     wid:
     *     subFace:
     * }
     *
     * **/

    @Override
    public BooleanReturn checkRule(JSONObject submit, ArrayList<String> rules) {
        boolean pass = true;
        String failMsg = "";
        for (String rule : rules) {
            System.out.println("=== 循环");
            if("FACKCHECK".equals(rule)){
                // 本地拉取用户Face
                UserFace u_face = userFaceDao.selectById(submit.getString("uid"));
                if(u_face == null){
                    return new BooleanReturn(false,"您还没有登记人脸信息捏~");
                }
                System.out.println("正在获取本地FACE");
                String faceSrc = null;
                try {
                    faceSrc = TypeChange.getImageBaseURL(u_face.getFaceSrc());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 获取当前照片
                System.out.println("正在获取当前上传的FACE");
                // TODO 测试
                String submitFace = TypeChange.getImgBaseFile(submit.getString("subFace"));
                if(faceSrc == null || submitFace == null){
                    return new BooleanReturn(false,"存在空照片");
                }
                BooleanReturn faceCheckRes = tencent.faceCheck(faceSrc, submitFace);
                if(faceCheckRes.isSuccess()){
                    JSONObject data = (JSONObject) faceCheckRes.getData();
                    BigDecimal score = TypeChange.Obj2BigDec(data.get("Score"));
                    System.out.println("SCORE = " + score);
                    if(score.compareTo(new BigDecimal(80)) >=0){
                        return new BooleanReturn(true, "验证通过");

                    }else{
                        return new BooleanReturn(false, "相似度较低");

                    }
                }else{
                    return new BooleanReturn(false,faceCheckRes.getMsg());
                }
            }
        }
        return new BooleanReturn(true);
    }
}
