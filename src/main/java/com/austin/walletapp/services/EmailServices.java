package com.austin.walletapp.services;

import com.austin.walletapp.dtos.requestDtos.MailDto;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;

public interface EmailServices {

    ApiResponse<MailDto> sendEmail(MailDto mailDto);
}
