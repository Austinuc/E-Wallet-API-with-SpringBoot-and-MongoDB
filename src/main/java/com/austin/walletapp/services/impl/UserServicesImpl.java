package com.austin.walletapp.services.impl;

import com.austin.walletapp.dtos.requestDtos.*;
import com.austin.walletapp.dtos.responseDtos.UserResponseDto;
import com.austin.walletapp.enums.Roles;
import com.austin.walletapp.enums.Status;
import com.austin.walletapp.exceptions.AuthenticationException;
import com.austin.walletapp.exceptions.NotFoundException;
import com.austin.walletapp.exceptions.ValidationException;
import com.austin.walletapp.models.User;
import com.austin.walletapp.models.Wallet;
import com.austin.walletapp.repositories.UserRepository;
import com.austin.walletapp.repositories.WalletRepository;
import com.austin.walletapp.security.CustomUserDetailsServices;
import com.austin.walletapp.security.JwtUtil;
import com.austin.walletapp.services.UserServices;
import com.austin.walletapp.utils.AppUtil;
import com.austin.walletapp.utils.LocalMemStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final AuthenticationManager auth;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CustomUserDetailsServices userDetailsServices;
    private final EmailServicesImpl mailSender;
    private final WalletRepository walletRepository;
    private final AppUtil app;
    private final LocalMemStorage memStorage;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);


    @Override
    public UserResponseDto signup(UserSignupDto userDto) {

        if (!app.validEmail(userDto.getEmail())) {
            logger.debug("invalid signup email: {}", userDto.getEmail());
            throw new ValidationException("Invalid email address");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            logger.debug("User already exist. email: {}",userDto.getEmail());
            throw new ValidationException("User with email: " + userDto.getEmail() + " already exists");
        }

        User newUser = app.getMapper().convertValue(userDto, User.class);
        String userId = app.generateSerialNumber("usr");
        newUser.setUuid(userId);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRoles(Roles.USER.name());
        newUser.setStatus(Status.INACTIVE.name());


        logger.info("Setting up wallet for new user: {}",newUser.getEmail());
        Wallet newWallet = Wallet.builder()
                .walletUUID(app.generateSerialNumber("0"))
                .balance(BigDecimal.ZERO)
                .userUUID(newUser.getUuid())
                .status(Status.INACTIVE)
                .build();


        walletRepository.save(newWallet);

        newUser.setWalletUUId(newWallet.getWalletUUID());
        newUser = userRepository.save(newUser);
        logger.info("new user registered. email: {}",newUser.getEmail());

        sendToken(newUser.getEmail(), "Activate your account");

        return app.getMapper().convertValue(newUser, UserResponseDto.class);
    }

    @Override
    public String sendToken(String userEmail, String subject) {
        logger.info("Sending verification token to: {}",userEmail);

        String token = app.generateSerialNumber("verify");
        memStorage.save(userEmail, token, 900); //expires in 15mins

        MailDto mailDto = MailDto.builder()
                .to(userEmail)
                .subject(subject.toUpperCase())
                .message(String.format("Use this generated token to %s: %s (Expires in 15mins)", subject.toLowerCase(), token))
                .build();

        mailSender.sendEmail(mailDto);

        return "Verification Token sent";
    }

    @Override
    public UserResponseDto activateUser(ActivateUserDto activateUserDto) {

        validateToken(activateUserDto.getEmail(), activateUserDto.getVerificationToken());

        User userToActivate = userRepository.findByEmail(activateUserDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));


        userToActivate.setStatus(Status.ACTIVE.name());
        UserResponseDto userResponseDto = app.getMapper().convertValue(userRepository.save(userToActivate), UserResponseDto.class);

        MailDto mailDto = MailDto.builder()
                .to(userToActivate.getEmail())
                .subject("YOUR ACCOUNT IS ACTIVATED")
                .message(String.format("Hi %s, \n You have successfully activated your account. Kindly login to start making use of the app", userResponseDto.getFirstName()))
                .build();

        mailSender.sendEmail(mailDto);
        logger.info("Confirmation email sent to  {}", userResponseDto.getEmail());

        return userResponseDto;
    }

    @Override
    public UserResponseDto login(UserLoginDto userLoginDto) {

        if (!app.validEmail(userLoginDto.getEmail()))
            throw new ValidationException("Invalid email");
        try {
            Authentication authentication = auth.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
            );
            UserResponseDto userResponseDto = null;

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(userLoginDto.getEmail())
                        .orElseThrow(() -> new AuthenticationException("Invalid login credentials"));

                if (user.getStatus().equals(Status.INACTIVE.name()))
                    throw new ValidationException("User not active");

                logger.info("Generating access token for {}", user.getEmail());

                String accessToken = jwtUtil.generateToken(userDetailsServices.loadUserByUsername(user.getEmail()));

                user.setLastLoginDate(new Date());
                userResponseDto = app.getMapper().convertValue(userRepository.save(user), UserResponseDto.class);

                userResponseDto.setToken(accessToken);
            } else {
                throw new AuthenticationException("Invalid username or password");
            }
            return userResponseDto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public String resetPassword(ChangePasswordDto changePasswordDto) {
        validateToken(changePasswordDto.getEmail(), changePasswordDto.getVerificationToken());

        User userToResetPassword = userRepository.findByEmail(changePasswordDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        userToResetPassword.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(userToResetPassword);

        return "password reset successful";
    }

    @Override
    public String logout(String headerToken) {
        if (headerToken.startsWith("Bearer")) {
            headerToken = headerToken.replace("Bearer", "").replace("\\s", "");
        }
        blacklistToken(headerToken);
        return "Logout successful";
    }

    public void validateToken(String memCachedKey, String token) {

        if (!app.validEmail(memCachedKey))
            throw new ValidationException("Invalid email");

        String cachedToken = memStorage.getValueByKey(memCachedKey);
        if (cachedToken == null)
            throw new ValidationException("Token expired");
        if (!cachedToken.equals(token))
            throw new ValidationException("Invalid token");

        if (!userRepository.existsByEmail(memCachedKey))
            throw new NotFoundException("User not found");
    }

    public void blacklistToken(String token) {
        Date expiryDate = jwtUtil.extractExpiration(token);
        int expiryTimeInSeconds = (int) ((expiryDate.getTime() - new Date().getTime())/1000);
        memStorage.setBlacklist(token, expiryTimeInSeconds);
    }
}
