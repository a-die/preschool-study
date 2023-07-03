package com.preschool.service;

import com.preschool.pojo.EmailVerification;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-12-14
 */
public interface EmailVerificationService extends IService<EmailVerification> {
    public String sendEmail(String email);

}
