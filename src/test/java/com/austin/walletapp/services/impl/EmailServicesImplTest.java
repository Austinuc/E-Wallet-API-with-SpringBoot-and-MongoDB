package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.requestDtos.MailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class EmailServicesImplTest {
    @Mock
    JavaMailSender mailSender;


    @Test
    public void send(){
        MailDto mailDto = MailDto.builder()
                .message("Testing message body: http://localhost:8083/api/v1/auth/signup")
                .subject("THIS IS THE HEADER")
                .to("austin5astro@gmail.com")
                .build();
        EmailServicesImpl emailServices = new EmailServicesImpl(mailSender);

        emailServices.sendEmail(mailDto);
    }

}