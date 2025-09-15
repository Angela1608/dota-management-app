package com.dota.hero.manager.exception.handler;

import com.dota.hero.manager.exception.NotFoundException;
import com.dota.hero.manager.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final ErrorResponseBuilder errorResponseBuilder;

    @Order(1)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErrorResponse.Fault.ErrorDetails errorDetails = ErrorResponse.Fault.ErrorDetails.builder()
                .description(ex.getMessage())
                .errorCode(ex.getClass().getSimpleName())
                .build();

        return errorResponseBuilder.buildErrorResponse(List.of(errorDetails), request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse.Fault.ErrorDetails errorDetails = ErrorResponse.Fault.ErrorDetails.builder()
                .description(ex.getMessage())
                .errorCode(ex.getClass().getSimpleName())
                .build();

        return errorResponseBuilder.buildErrorResponse(List.of(errorDetails), request);
    }

}
