package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.requestDtos.MailDto;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.services.EmailServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicesImpl implements EmailServices {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailServicesImpl.class);
    @Override
    public ApiResponse<MailDto> sendEmail(MailDto mailDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("no-relpy-ewallet@gmail.com");
        simpleMailMessage.setTo(mailDto.getTo());
        simpleMailMessage.setSubject(mailDto.getSubject());
        simpleMailMessage.setText(mailDto.getMessage());

        mailSender.send(simpleMailMessage);

        logger.info("Email sent successfully to {}",mailDto.getTo());

        return new ApiResponse<>("Email sent successfully", true, mailDto);
    }

    public static void main(String[] args) {

    }
}
