package com.liumapp.config;

import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    /**邮件服务器*/
    public final static String myEmailSMTPHost = "smtp.163.com";
    /**发件人邮箱账号*/
    public final static String myEmailAccount = "nzlsgg@163.com";
    /**收件人账号*/
    public final static String receiveMailAccount = "1132263176@qq.com";
    /**邮件服务器授权码*/
    public final static String myEmailPassword = "nzlsgg199595";
    /**邮件主题*/
    public final static String subject = "socket服务器挂掉了";
    /**发送人昵称*/
    public final static String from = "socket服务器";
    /**邮件内容*/
    public final static String mailContent = "大兄弟,救我,我挂了 快把我重启吧!!!";
    /**对接受人称呼*/
    public final static String personal = "XX大哥";
    /**心跳正常次数,把error清0*/
    public final static int success = 100;
    /**心跳出现异常达到发邮件的次数*/
    public final static int error = 3;
}
