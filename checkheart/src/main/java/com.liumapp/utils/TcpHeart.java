package com.liumapp.utils;

import com.liuapp.util.MailUtil;
import com.liumapp.DNSQueen.queen.Queen;
import com.liumapp.config.Config;
import com.liumapp.config.MailConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpHeart extends  Thread{
   @Autowired
   private Config config;
   @Autowired
   private MailConfig mailConfig;
   private Queen queen;
   private boolean isconnect = false;
   private static Logger logger = LogManager.getLogger(TcpHeart.class);
    @Override
    public void run() {
        System.out.println("开始检测Socket的此时状态");
        int success = 0;
        int error = 0;
        boolean isSend = true;//是否发送邮件
        if (!isconnect) {
            connect();
        }
        try {
            while (true) {
               boolean bool = send();
               if (bool) {
                   if(success++ == mailConfig.success) {
                       error = 0;
                       success = 0;
                   };
                   isSend = true;//把状态设置为可以发送邮件
                   System.out.println("The service is keeping alive");
               } else {
                   //如果错误次数达到了三次并且之前没有发送过邮件,就发送一次邮件,
                   //此时心跳还一直在继续,如果一直异常的话就不会再发邮件,如果中途服务器连接上,又出现错误,那就再次发送
                   if (error ++ == mailConfig.error&& isSend) {
                       isSend = false;
                      MailUtil.sendMail( mailConfig.myEmailSMTPHost,mailConfig.myEmailAccount,mailConfig.receiveMailAccount,
                              mailConfig.myEmailPassword,mailConfig.subject,
                              mailConfig.from,mailConfig.mailContent, mailConfig.personal);
                      logger.info("socket状态异常的邮件已发送到 ["+mailConfig.receiveMailAccount+"] 的邮箱中,发送时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                   }
                   connect();//尝试重新连接
                   Thread.sleep(3000);
                   logger.info("socket异常,正在尝试重新连接.....");
               }
               Thread.sleep(config.time);
            }
        } catch (InterruptedException e) {
             logger.error(e.getMessage());
        }
    }

    /**
     * 连接socket的方法
     */
    public Boolean connect() {
        queen = new Queen();
        queen.setAddress(config.IP);
        try {
            queen.connect();
        } catch (IOException e) {
           logger.info(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 查看发送报文后,service给的回应
     * @return
     */
    public boolean send(){
        //查看当前连接是否关闭
        if(!queen.isConnected()){
            return false;
        }
        //发送给服务端的空报文
        queen.say(""+"\r\n");
        try {
            //从服务端收到的报文
            String hear = queen.hear();
        } catch (IOException e) {
            logger.info(e.getMessage());
            return false;
        }
        return true;
    }

}
