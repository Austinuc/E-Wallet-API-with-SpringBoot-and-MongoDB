package com.austin.walletapp.exceptions;
import com.austin.walletapp.dtos.responseDtos.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleNotFoundException(NotFoundException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>(ex.getMessage(), false, null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleValidationException(ValidationException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponse<String> handleAuthenticationException(AuthenticationException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(MailSendingException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponse<String> handleMailSendingException(MailSendingException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleInvalidTransactionException(InvalidTransactionException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiResponse<String> handleUnAuthorizedException(org.springframework.security.core.AuthenticationException ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ ex.getMessage(), false,null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<String> handleException(Exception ex){
        logger.error(ex.getMessage());
        return  new ApiResponse<>("Error: "+ ex.getMessage(), false,null);
    }

}
