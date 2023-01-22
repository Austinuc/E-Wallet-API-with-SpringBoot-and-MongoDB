package com.austin.walletapp.controllers.auth;

import com.austin.walletapp.dtos.requestDtos.ChangePasswordDto;
import com.austin.walletapp.dtos.requestDtos.UserLoginDto;
import com.austin.walletapp.dtos.requestDtos.UserSignupDto;
import com.austin.walletapp.dtos.requestDtos.ActivateUserDto;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import com.austin.walletapp.dtos.responseDtos.UserResponseDto;
import com.austin.walletapp.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserServices userServices;

    @Operation(summary = "Create a new user account",
            description = "After creating your account, a verification code will be sent to your provided email. \n" +
                    "Copy the code from you email and enter it in the 'activate-account end point'.\n" +
                    "If no code was sent to you, then use the 'resend-token' end point to resend a new activation code")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserSignupDto userSignupDto) {

        return ResponseEntity.ok().body(new ApiResponse<>("User signup successful",true, userServices.signup(userSignupDto)));
    }

    @Operation(summary = "Activates a newly created account or an inactive account",
        description = "Once activated, you can then login using the 'login' end point.\n" +
                "Ensure that you enter the complete verification code sent to your email, it starts with 'verify...'")
    @PostMapping("/activate-account")
    public ResponseEntity<ApiResponse<UserResponseDto>> activateUser(@RequestBody ActivateUserDto activateUserDto) {
        return ResponseEntity.ok(new ApiResponse<>("User activated!",true, userServices.activateUser(activateUserDto)));
    }

    @Operation(summary = "Generates a JWT token upon successful login that will be used for Authorizations",
        description = "You wiil need to append the string 'Bearer ' to the token before adding it to your Authorization header. NOTE: " +
                "there's a single space after the 'Bearer ' string. Don't forget to include it.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> loginUser(@RequestBody UserLoginDto userLoginDto) {

        return ResponseEntity.ok(new ApiResponse<>("User logged in successfully!",true, userServices.login(userLoginDto)));
    }

    @Operation(summary = "Resend account activation token or password reset token")
    @PostMapping("/resend-token")
    public ResponseEntity<ApiResponse<String>> resendToken(@Valid @RequestParam String email, @Valid @RequestParam String reason) {
        return ResponseEntity.ok(new ApiResponse<>(userServices.sendToken(email, reason),true, email));
    }

    @Operation(summary = "Resets a password after a reset token has been confirmed",
            description = "Before using this endpoint, ensure the forgot password token has been activated")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(new ApiResponse<>(userServices.resetPassword(changePasswordDto),true, null));
    }

}
