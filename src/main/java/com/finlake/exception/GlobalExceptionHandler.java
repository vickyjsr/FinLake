package com.finlake.exception;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class})
    public ModelAndView handleAccessDeniedException(HttpServletResponse response) throws IOException {
        if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            // Redirect to login page
            return new ModelAndView("redirect:/login");
        }

        // Handle other exceptions
        // ...

        return new ModelAndView("redirect:/users");
    }
}
