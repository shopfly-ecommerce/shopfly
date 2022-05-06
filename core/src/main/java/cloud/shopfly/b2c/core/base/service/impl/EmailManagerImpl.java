/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.service.impl;

import cloud.shopfly.b2c.core.base.model.dos.EmailDO;
import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.base.service.EmailManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;
import cloud.shopfly.b2c.core.system.service.SmtpManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送实现
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018年3月26日 下午3:23:04
 */
@Service
public class EmailManagerImpl implements EmailManager {
    @Autowired
    private SmtpManager smtpManager;
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private Debugger debugger;

    @Autowired
    
    private DaoSupport systemDaoSupport;

    /**
     * 通过java Transport发送邮件  支持ssl
     *
     * @param emailVO
     */
    @Override
    public void sendMailByTransport(SmtpDO smtp, EmailVO emailVO) {

        Properties props = new Properties();
        props.put("mail.smtp.host", smtp.getHost());
        props.put("mail.smtp.port", String.valueOf(smtp.getPort()));
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", String.valueOf(smtp.getPort()));
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtps.ssl.protocols", "TSLv1 TSLv1.1 TLSv1.2");
        props.put("mail.smtp.ssl.ciphersuites", "SSL_RSA_WITH_RC4_128_SHA SSL_RSA_WITH_RC4_128_MD5 TLS_RSA_WITH_AES_128_CBC_SHA TLS_DHE_RSA_WITH_AES_128_CBC_SHA TLS_DHE_DSS_WITH_AES_128_CBC_SHA SSL_RSA_WITH_3DES_EDE_CBC_SHA SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA SSL_RSA_WITH_DES_CBC_SHA SSL_DHE_RSA_WITH_DES_CBC_SHA SSL_DHE_DSS_WITH_DES_CBC_SHA SSL_RSA_EXPORT_WITH_RC4_40_MD5 SSL_RSA_EXPORT_WITH_DES40_CBC_SHA SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA TLS_EMPTY_RENEGOTIATION_INFO_SCSV");

        Session sendMailSession = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtp.getUsername(),
                                smtp.getPassword());
                    }
                });
        // 发送邮件
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(smtp.getUsername(), smtp.getMailFrom());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(emailVO.getEmail());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(emailVO.getTitle());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            mailMessage.setContent(emailVO.getContent(), "text/html;charset=utf-8");
            Transport.send(mailMessage);
            debugger.log("邮件发送成功");
        } catch (MessagingException e) {
            debugger.log("邮件发送失败:", StringUtil.getStackTrace(e));
            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "邮件发送失败！");
        } catch (UnsupportedEncodingException e) {
            debugger.log("邮件发送失败:", StringUtil.getStackTrace(e));

            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "邮件发送失败！");
        }
        emailVO.setErrorNum(0);
        emailVO.setSuccess(1);
        //向库中插入
        this.add(emailVO);

    }

    /**
     * 通过javamail 发送邮件 暂不支持ssl
     *
     * @param emailVO
     */
    @Override
    public void sendMailByMailSender(SmtpDO smtp, EmailVO emailVO) {
        //否则使用javaMailSender
        JavaMailSender javaMailSender = new JavaMailSenderImpl();

        ((JavaMailSenderImpl) javaMailSender).setHost(smtp.getHost());
        ((JavaMailSenderImpl) javaMailSender).setUsername(smtp.getUsername());
        ((JavaMailSenderImpl) javaMailSender).setPassword(smtp.getPassword());
        ((JavaMailSenderImpl) javaMailSender).setPort(smtp.getPort());
        //设置发送者
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            //设置邮件标题
            helper.setSubject(emailVO.getTitle());
            //设置邮件内容
            helper.setText(emailVO.getContent());

            //设置邮件 收件人
            helper.setTo(emailVO.getEmail());

            helper.setFrom(smtp.getMailFrom());
            //发送邮件
            javaMailSender.send(message);
            debugger.log("邮件发送成功");

        } catch (Exception e) {
            debugger.log("邮件发送失败:", StringUtil.getStackTrace(e));

            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "邮件发送失败！");
        }
        //向库中插入
        this.add(emailVO);
    }


    /**
     * 添加发邮件记录
     *
     * @param email 邮件信息
     * @return 邮件信息
     */
    private EmailDO add(EmailVO email) {
        EmailDO emailDO = new EmailDO();
        emailDO.setEmail(email.getEmail());
        emailDO.setTitle(email.getTitle());
        emailDO.setContent(email.getContent());
        emailDO.setType(email.getTitle());
        //默认假设成功
        emailDO.setSuccess(email.getSuccess());
        emailDO.setLastSend(DateUtil.getDateline());
        emailDO.setErrorNum(email.getErrorNum());
        this.systemDaoSupport.insert(emailDO);
        return emailDO;
    }

    @Override
    public void sendMQ(EmailVO emailVO) {
        this.messageSender.send(new MqMessage(AmqpExchange.EMAIL_SEND_MESSAGE, "emailSendMessageMsg", emailVO));
    }


    @Override
    public void sendEmail(EmailVO emailVO) {
        //获取当钱的smtp服务器
        SmtpDO smtp = smtpManager.getCurrentSmtp();

        debugger.log("找到smtp服务器：",smtp.toString());
        //根据对ssl的支付 分别走不同的发送方法
        if (smtp.getOpenSsl() == 1 || "smtp.qq.com".equals(smtp.getHost())) {
            debugger.log("使用ssl");
            this.sendMailByTransport(smtp, emailVO);
        } else {
            debugger.log("不使用ssl");

            this.sendMailByMailSender(smtp, emailVO);
        }
    }
}
