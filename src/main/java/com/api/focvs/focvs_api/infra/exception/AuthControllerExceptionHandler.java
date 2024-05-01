package com.api.focvs.focvs_api.infra.exception;

import com.api.focvs.focvs_api.controllers.auth.IAuthController;
import com.api.focvs.focvs_api.dtos.error.ErrorMessageDTO;
import com.api.focvs.focvs_api.exceptions.account.AccountNotFoundException;
import com.api.focvs.focvs_api.exceptions.account.EmailAlreadyInUseException;
import com.api.focvs.focvs_api.exceptions.account.PasswordDoesNotMatchException;
import com.api.focvs.focvs_api.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = {IAuthController.class})
public class AuthControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    private ResponseEntity<ErrorMessageDTO> handleAccountNotFoundException(AccountNotFoundException exception) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.NOT_FOUND, true, exception.getMessage());
        return ResponseEntity.status(errorMessageDTO.status()).body(errorMessageDTO);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    private ResponseEntity<ErrorMessageDTO> handlePasswordDoesNotMatchException(PasswordDoesNotMatchException exception) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.UNAUTHORIZED, true, exception.getMessage());
        return ResponseEntity.status(errorMessageDTO.status()).body(errorMessageDTO);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    private ResponseEntity<ErrorMessageDTO> handleEmailAlreadyInUseException(EmailAlreadyInUseException exception) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.OK, true, exception.getMessage());
        return ResponseEntity.status(errorMessageDTO.status()).body(errorMessageDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.UNAUTHORIZED, true, exception.getMessage());
        return ResponseEntity.status(errorMessageDTO.status()).body(errorMessageDTO);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorMessageDTO> handleGenericException(Exception exception) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR, true, "An unexpected error occurred.");
        return ResponseEntity.status(errorMessageDTO.status()).body(errorMessageDTO);
    }
}
