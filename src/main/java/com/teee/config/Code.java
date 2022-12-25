package com.teee.config;

public class Code {
    // 身份
    public static final String Teacher = "teacher";
    public static final String Student = "student";
    public static final String Admin = "admin";

    // Token 失效
    public static final Integer Token_Illegal = 00000;
    //未定义异常
    public static final Integer ERR = 00001;
    //正常
    public static final Integer Suc = 00002;

    //注册
    public static final Integer Login_Right = 20001;
    public static final Integer Login_Fail_unknown_username = 20002;
    public static final Integer Login_Fail_pwdWrong = 20003;
    //登录
    public static final Integer Register_Right = 20011;
    public static final Integer Register_Fail_usernameExist = 20012;
    public static final Integer Register_Fail_UnknownError = 20013;
    //获取用户信息
    public static final Integer GetUser_Success = 20021;
    public static final Integer GetUser_Fail = 20022;
    //设置用户信息
    public static final Integer SetUserInfo_Success = 20031;
    public static final Integer SetUserInfo_Fail = 20032;

    //学生
    //**添加课程
    public static final Integer addCourse_Success = 20101;
    public static final Integer addCourse_Fail_cidErr = 20102;

    //教师
    public static final Integer Bank_noQue = 20200;
    public static final Integer Bank_getQueSucc = 20201;

    public static final Integer BankType_choice_question = 30000;
    public static final Integer BankType_fillin_question = 30001;
    public static final Integer BankType_text_question = 30002;

    public static final Integer QueType_choice_question = 30010;
    public static final Integer QueType_fillin_question = 30011;
    public static final Integer QueType_text_question = 30012;





}
