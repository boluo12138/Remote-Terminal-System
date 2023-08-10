package com.wzu.sys.service;

public interface MailService {
    //发送邮件
    boolean sendMail(String email, String text);
}
