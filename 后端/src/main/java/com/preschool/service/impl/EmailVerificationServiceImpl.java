package com.preschool.service.impl;

import com.preschool.pojo.EmailVerification;
import com.preschool.dao.EmailVerificationMapper;
import com.preschool.service.EmailVerificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.utils.UUID_DIY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.beans.Transient;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-12-14
 */
@Service
public class EmailVerificationServiceImpl extends ServiceImpl<EmailVerificationMapper, EmailVerification> implements EmailVerificationService {

    @Autowired
    JavaMailSenderImpl mailSender;


    @Override
    @Transient
    public String sendEmail(String email) {
        //一个简单的邮件发送
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("preschool-study找回密码");
        String code = UUID_DIY.getUUIDInOrderId();
        mailMessage.setText("您收到的验证码是"+code+"\n密码有效期会5分钟，请尽快前往验证");
        mailMessage.setTo(email);
        mailMessage.setFrom("993204079@qq.com");
        mailSender.send(mailMessage);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setCode(code);
        emailVerification.setEmail(email);
        save(emailVerification);
        return code;
    }
}
