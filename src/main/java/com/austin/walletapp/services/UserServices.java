package com.austin.walletapp.services;

import com.austin.walletapp.dtos.requestDtos.ActivateUserDto;
import com.austin.walletapp.dtos.requestDtos.ChangePasswordDto;
import com.austin.walletapp.dtos.requestDtos.UserLoginDto;
import com.austin.walletapp.dtos.requestDtos.UserSignupDto;
import com.austin.walletapp.dtos.responseDtos.UserResponseDto;

public interface UserServices {
    UserResponseDto signup(UserSignupDto userDto);

    String sendToken(String userEmail, String mailSubject);

    UserResponseDto activateUser(ActivateUserDto activateUserDto);

    UserResponseDto login(UserLoginDto userLoginDto);

//    String forgotPassword(String email);

    String resetPassword(ChangePasswordDto changePasswordDto);

    String logout(String token);
}
