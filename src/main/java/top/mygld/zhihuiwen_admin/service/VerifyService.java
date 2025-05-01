package top.mygld.zhihuiwen_admin.service;

import top.mygld.zhihuiwen_admin.common.Result;

public interface VerifyService {
    public Result<String> sendCode(String email, String captchaVerification);
    public Result<String> checkCode(String email,String code);
}
