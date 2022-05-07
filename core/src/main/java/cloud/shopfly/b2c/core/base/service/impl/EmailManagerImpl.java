/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
 * Mail sending implementation
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month26On the afternoon3:23:04
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
     * throughjava TransportSend Mail Supportssl
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
        // Send E-mail
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            // Create the sender address
            Address from = new InternetAddress(smtp.getUsername(), smtp.getMailFrom());
            // Sets the sender of the mail message
            mailMessage.setFrom(from);
            // Create the recipient address of the message and set it in the mail message
            Address to = new InternetAddress(emailVO.getEmail());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // Set the subject of the mail message
            mailMessage.setSubject(emailVO.getTitle());
            // Set the time when email messages are sent
            mailMessage.setSentDate(new Date());
            // Sets the main content of the mail message
            mailMessage.setContent(emailVO.getContent(), "text/html;charset=utf-8");
            Transport.send(mailMessage);
            debugger.log("Email sent successfully");
        } catch (MessagingException e) {
            debugger.log("Email sending failure:", StringUtil.getStackTrace(e));
            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "Email sending failure！");
        } catch (UnsupportedEncodingException e) {
            debugger.log("Email sending failure:", StringUtil.getStackTrace(e));

            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "Email sending failure！");
        }
        emailVO.setErrorNum(0);
        emailVO.setSuccess(1);
        // Insert into the library
        this.add(emailVO);

    }

    /**
     * throughjavamail Sending emails is not supportedssl
     *
     * @param emailVO
     */
    @Override
    public void sendMailByMailSender(SmtpDO smtp, EmailVO emailVO) {
        // Otherwise, use javaMailSender
        JavaMailSender javaMailSender = new JavaMailSenderImpl();

        ((JavaMailSenderImpl) javaMailSender).setHost(smtp.getHost());
        ((JavaMailSenderImpl) javaMailSender).setUsername(smtp.getUsername());
        ((JavaMailSenderImpl) javaMailSender).setPassword(smtp.getPassword());
        ((JavaMailSenderImpl) javaMailSender).setPort(smtp.getPort());
        // Setting the sender
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Set email Subject
            helper.setSubject(emailVO.getTitle());
            // Setting email Content
            helper.setText(emailVO.getContent());

            // Setting Email Recipients
            helper.setTo(emailVO.getEmail());

            helper.setFrom(smtp.getMailFrom());
            // Send E-mail
            javaMailSender.send(message);
            debugger.log("Email sent successfully");

        } catch (Exception e) {
            debugger.log("Email sending failure:", StringUtil.getStackTrace(e));

            e.printStackTrace();
            emailVO.setErrorNum(1);
            emailVO.setSuccess(0);
            throw new ServiceException(SystemErrorCode.E904.code(), "Email sending failure！");
        }
        // Insert into the library
        this.add(emailVO);
    }


    /**
     * Add an email sending record
     *
     * @param email Email messages
     * @return Email messages
     */
    private EmailDO add(EmailVO email) {
        EmailDO emailDO = new EmailDO();
        emailDO.setEmail(email.getEmail());
        emailDO.setTitle(email.getTitle());
        emailDO.setContent(email.getContent());
        emailDO.setType(email.getTitle());
        // Default assumption success
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
        // Get when money is sent to the SMTP server
        SmtpDO smtp = smtpManager.getCurrentSmtp();

        debugger.log("findsmtpThe server：",smtp.toString());
        // The sending method is different depending on the payment to SSL
        if (smtp.getOpenSsl() == 1 || "smtp.qq.com".equals(smtp.getHost())) {
            debugger.log("usessl");
            this.sendMailByTransport(smtp, emailVO);
        } else {
            debugger.log("Do not usessl");

            this.sendMailByMailSender(smtp, emailVO);
        }
    }
}
