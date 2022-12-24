package com.austin.walletapp.controllers.auth;

import com.austin.walletapp.dtos.requestDtos.ChangePasswordDto;
import com.austin.walletapp.dtos.requestDtos.UserLoginDto;
import com.austin.walletapp.dtos.requestDtos.UserSignupDto;
import com.austin.walletapp.dtos.requestDtos.ActivateUserDto;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.UserResponseDto;
import com.austin.walletapp.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserSignupDto userSignupDto) {

        return ResponseEntity.ok().body(new ApiResponse<>("User signup successful",true, userServices.signup(userSignupDto)));
    }

    @PostMapping("/activate-user")
    public ResponseEntity<ApiResponse<UserResponseDto>> activateUser(@RequestBody ActivateUserDto activateUserDto) {
        return ResponseEntity.ok(new ApiResponse<>("User activated!",true, userServices.activateUser(activateUserDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> loginUser(@RequestBody UserLoginDto userLoginDto) {

        return ResponseEntity.ok(new ApiResponse<>("User activated!",true, userServices.login(userLoginDto)));
    }

    @PostMapping("/resend-token")
    public ResponseEntity<ApiResponse<String>> resendToken(@Valid @RequestParam String email, @Valid @RequestParam String reason) {
        return ResponseEntity.ok(new ApiResponse<>(userServices.sendToken(email, reason),true, email));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(new ApiResponse<>(userServices.resetPassword(changePasswordDto),true, null));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(new ApiResponse<>(userServices.logout(token), true, null));
    }
}
