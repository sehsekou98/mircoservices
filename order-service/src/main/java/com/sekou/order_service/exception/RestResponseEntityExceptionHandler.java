package com.sekou.order_service.exception;
import com.sekou.order_service.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException exception)  {
        new ErrorResponse();
        return new ResponseEntity<>(ErrorResponse.builder()
              .errorMessage(exception.getMessage())
              .errorCode(exception.getErrorCode())
              .build(),
                HttpStatus.valueOf(exception.getStatus()));


    }
}
