package com.teee.controller;

import com.alibaba.fastjson.JSONObject;
import com.teee.config.Code;
import com.teee.domain.returnClass.Result;
import com.teee.domain.returnClass.UploadErr;
import com.teee.domain.returnClass.UploadResult;
import com.teee.service.User.UserService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.TypeChange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author Xu ZhengTao
 */
@RequestMapping("/upload")
@Controller
public class UploadController {

    @Value("${path.picPath}")
    private String picPath;

    @Value("${path.filePath}")
    private String filePath;

    @Value("${path.facePath}")
    private String facePath;

    @Value("${server.port}")
    private String port;

    @Value("${realMaxFileSizeMB}")
    private int maxSizeMB;

    @RequestMapping("/uploadFacePic")
    @ResponseBody
    public Result uploadFacePic(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        UploadResult uploadResult = uploadImg(file, request, facePath, "face");
        if(uploadResult.getUploaded() == 0){
            return new Result(Code.ERR, uploadResult.getError());
        }else{
            SpringBeanUtil.getBean(UserService.class).setFace(JWT.getUid(token), uploadResult.getUrl());
            return new Result(Code.Suc,null, "上传成功");
        }
    }



    @RequestMapping("/img")
    @ResponseBody
    public UploadResult uploadPicImg(@RequestParam("upload") MultipartFile file, HttpServletRequest request){
        UploadResult uploadResult = uploadImg(file, request, picPath, "pic");
        return uploadResult;
    }

    private UploadResult uploadImg(MultipartFile file, HttpServletRequest request, String path, String dirName){
        ArrayList<String> suffixWhiteList = new ArrayList<>();
        suffixWhiteList.add(".png");
        suffixWhiteList.add(".jpg");
        suffixWhiteList.add(".jpeg");
        suffixWhiteList.add(".gif");
        suffixWhiteList.add(".ico");
        suffixWhiteList.add(".cur");
        suffixWhiteList.add(".jfif");
        suffixWhiteList.add(".pjpeg");
        suffixWhiteList.add(".pjp");
        suffixWhiteList.add(".svg");
        suffixWhiteList.add(".tif");
        suffixWhiteList.add(".webp");
        suffixWhiteList.add(".tiff");
        System.out.println("进入upload");
        if(file == null){
            return new UploadResult(0, new UploadErr("未发现上传的文件"));
        }
        String originalFilename = file.getOriginalFilename();
        String appendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("append: " + appendName);
        if(!suffixWhiteList.contains(appendName)){
            return new UploadResult(0, new UploadErr("图片格式不支持"));

//            return new UploadResult(0, new UploadErr("不支持的图像格式"));
        }
        System.out.println("origin: " + originalFilename);
        System.out.println("appendName: " + appendName);
        File newMkdir = new File(path);
        if(!newMkdir.exists()){
            newMkdir.mkdirs();
        }
        String uploadFile = System.currentTimeMillis() + appendName;
        try {
            file.transferTo(new File(path+File.separator+uploadFile));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + port + "/" + dirName + "/" + uploadFile;
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
        try{
            System.out.println("进入上传队列 ...");
            // 文件校验
            for (MultipartFile multipartFile : file) {
                int check = checkFile(multipartFile);
                if(check!=0){
                    if(check==1){
                        return new Result(Code.ERR,null ,"文件:" + multipartFile.getOriginalFilename() + "超出大小限制(" + maxSizeMB + "MB)");
                    }
                }
            }

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
                arrayList.add("\"" + fileTempObj.getName() + "\"");
            }
            return new Result(Code.Suc, TypeChange.arrL2str(arrayList), "上传成功！");
//        return new Result(Code.Suc, fileName + "|" + ((double) fileTempObj.length() / 1024 / 1024));
        }catch (Exception e){
            return new Result(Code.ERR, e.getMessage(), "Err Cause by UploadController");
        }

    }

    @RequestMapping("/getFile")
    @ResponseBody
    public Result downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filePath + File.separator + fileName);
        String substring = fileName.substring(fileName.lastIndexOf("_")+1);
        String fileOriginName = substring.substring(substring.lastIndexOf("_")+1);
        if(!file.exists()){
            return new Result(Code.ERR, null, "文件不存在");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition",URLEncoder.encode(fileOriginName, "UTF-8"));

        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(file);
            OutputStream os = response.getOutputStream();
            os.write(bytes);
//            return new Result(Code.Suc, null, "下载启动成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(Code.ERR, null, "下载启动失败");
        }
        return null;
    }

    /**
     * 返回值：
     *  0.通过校验
     *  1.文件大小太大
     *  2.不通过安全检测 (TODO)
     * */
    public int checkFile(MultipartFile file){

        if ((file.getSize() / (1024*1024)) > maxSizeMB ){
            return 1;
        }
        /* TODO
        else if (){

        }
         */
        else{
            return 0;
        }
    }
}
