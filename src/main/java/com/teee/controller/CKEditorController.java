package com.teee.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.teee.config.Code;
import com.teee.domain.returnClass.Result;
import com.teee.domain.returnClass.UploadErr;
import com.teee.domain.returnClass.UploadResult;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Xu ZhengTao
 */
@RequestMapping("/upload")
@Controller
public class CKEditorController {

    @Value("${path.picPath}")
    private String picPath;

    @Value("${path.picPath}")
    private String filePath;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/img")
    @ResponseBody
    public UploadResult uploadImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request){
        System.out.println("进入upload");
        if(file == null){
            return new UploadResult(0, new UploadErr("未发现上传的文件"));
        }
        String originalFilename = file.getOriginalFilename();
        String appendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("origin: " + originalFilename);
        System.out.println("appendName: " + appendName);
        File newMkdir = new File(picPath);
        if(!newMkdir.exists()){
            newMkdir.mkdirs();
        }
        String uploadFile = System.currentTimeMillis() + appendName;
        try {
            file.transferTo(new File(picPath+File.separator+uploadFile));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + port + "/pic/" + uploadFile;
            System.out.println("url=" + url);
            return new UploadResult(1, "",url);
        } catch (IOException e) {
            e.printStackTrace();
            return new UploadResult(0, new UploadErr("上传失败"));
        }
    }
    @RequestMapping("/file")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile[] file, HttpServletRequest request){
        JSONObject ret = new JSONObject();
        ArrayList<String> arrayList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            if(multipartFile.isEmpty()){
                ret.put("err", "空文件");
                return new Result(Code.ERR, null, "空文件");
            }
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传: " + fileName + ", 后缀: " + suffixName);
            File fileTempObj = new File(filePath + File.separator + "TimeStamp_" +  System.currentTimeMillis() + "_" + fileName);
            if(!fileTempObj.getParentFile().exists()){
                fileTempObj.getParentFile().mkdirs();
            }
            try{
                multipartFile.transferTo(fileTempObj);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(Code.ERR, null, "写入文件失败: " + fileTempObj.getName());
            }
            arrayList.add(fileTempObj.getPath());
        }
        return new Result(Code.Suc, TypeChange.arrL2str(arrayList), "上传成功！");
//        return new Result(Code.Suc, fileName + "|" + ((double) fileTempObj.length() / 1024 / 1024));
    }

}
