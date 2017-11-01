# Tcp心跳机制以及Javax.mail邮件
### Tcp心跳机制
引用的jar包: 

1. spring-boot-starter-log4j2.jar 1.5.8.RELEASE
2. DNSQueen.jar 1.2.0

###### 心跳程序入口:Application.java

###### 关键类:TcpHeart.java
 
  该类是继承了Thread类,是个线程,加入了log4j2记录日志,该线程首先判断socket的连接状态,没连接则用该类的connect()方法进行连接,
  线程里面有个死循环,调用发送报文的send()方法,发送成功返回true,反之false,然后休息指定时间,心跳再次进行,如果成功则success++,反之error++,当error =3时,则判定socket挂掉了,发送邮件给指定邮箱,心跳还一直在进行.连接不上就用日志打印出异常,直到连接成功,当成功达到指定次数时,将success=0;error = 0;总之,程序不停,心跳不止.

### 邮件模块

引用jar包:

1. javax.mail.jar 1.6.0
###### 关键类:MailUtil.java

该类就两个方法createMimeMessage() 生成一个普通邮件的方法,sendMail()这个是发送邮件的方法,其实这个邮件模块就是进行一些配置就行,调用sendMail()方法就可以发送邮件啦,主要就是需要明白这些方法要传的各个参数就行啦

    
		   /**
	     * 发送普通邮件的方法
	     * @param myEmailSMTPHost 发件人SMTP 服务器地址
	     * @param myEmailAccount  发件人邮箱账号
	     * @param receiveMailAccount  收件人账号
	     * @param myEmailPassword    发件人的服务器密码
	     * @param subject  邮件主题
	     * @param from   发件人昵称
	     * @param mailContent   邮件内容
	     * @param personal      收件人的称呼
	     */
	    public static void sendMail(String myEmailSMTPHost,String myEmailAccount,String receiveMailAccount,
	                                String myEmailPassword,String subject,
	                                String from,String mailContent,String personal)

		/**
		     * 创建一封只包含文本的简单邮件
		     *
		     * @param session 和服务器交互的会话
		     * @param sendMail 发件人邮箱
		     * @param receiveMail 收件人邮箱
		     * @Param from 发件人昵称
		     * @Param mailContent 邮件内容
		     * @Param subject  邮件主题
		     * @Param personal 对收件人的称呼
		     * @return
		     * @throws Exception
		     */
		    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String subject,
		            String from,String mailContent,String personal)